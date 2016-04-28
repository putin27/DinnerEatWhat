package com.example.amber.dinnereatwhat;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

//table的第一筆資料 cursor的position從0開始 裡面的_id是第0個欄位 所以資料從1開始取

public class DBHelper extends SQLiteOpenHelper {
    //資料庫名稱
    private final static String DATABASE_NAME = "dinnerDatabase";
    //資料庫版本
    private final static int DATABASE_VERSION = 1;
    //dinner table的名稱
    private String dinnerTable = "dinner";
    //tag table的名稱
    private String tagTable = "tag";
    //創建dinner table的指令
    //_id int,shop char,meal char,price int
    private String createDinnerTable = "CREATE TABLE IF NOT EXISTS " + dinnerTable
            + " ("
            + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "shop CHAR, meal CHAR, price INT,tag CHAR"
            + ")";
    //創建tag table的指令
    //t_id int foreign key,tag
    //foreign key 要放在最後面宣告
    private String createTagTable = "CREATE TABLE IF NOT EXISTS " + tagTable
            + " ("
            + "t_id INTEGER ,"
            + "tag CHAR,"
            + "FOREIGN KEY(t_id) REFERENCES " + dinnerTable + "(_id)"
            + ")";

    //rawquery指令
    //OR搜尋 AND搜尋 子搜尋
    //寫入新的字串時 保持前面不留 後面有一個空白 防止指令黏在一起造成指令錯誤
    private String cmdsOrSearch, cmdsAndSearch, cmdsSubquery;

    private SQLiteDatabase db;
    private Cursor cursor;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = this.getWritableDatabase();

        resetCmds();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createDinnerTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public ArrayList<Integer> orSearch(ArrayList<String> tags) {

        cmdsSubquery = cmdsSubquery + "'" + tags.get(0) + "' ";
        for (int i = 1; i < tags.size(); i++) {
            cmdsSubquery = cmdsSubquery + "or '" + tags.get(i) + "'";
        }
        cmdsSubquery = cmdsSubquery + ") ";

        cmdsOrSearch = cmdsOrSearch + cmdsSubquery;

        cursor = db.rawQuery(cmdsOrSearch, null);
        cursor.moveToFirst();

        ArrayList<Integer> ids = new ArrayList<>();
        for (int i = 0; i < cursor.getCount(); i++) {
            ids.add(cursor.getInt(i));
            cursor.moveToNext();
        }
        return ids;
    }


    public void resetCmds() {
        cmdsSubquery = "(select t_id,tag from " + tagTable + " where tag = ";
        cmdsOrSearch = "select distinct t_id from ";
        cmdsAndSearch = "select t_id from ";
    }

    //判斷是否資料庫為空
    public boolean isEmpty() {
        cursor = db.query(dinnerTable, null, null, null, null, null, null);
        // 如果沒有第一筆資料 回傳false
        return !cursor.moveToFirst();

    }

    public void insertDinner(DinnerData dinnerData) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("shop", dinnerData.shop);
        contentValues.put("meal", dinnerData.meal);
        contentValues.put("price", dinnerData.price);
        contentValues.put("tag", dinnerData.tag);
        db.insert(dinnerTable, null, contentValues);
    }

    public ArrayList<DinnerData> getAllDinnerData() {
        ArrayList<DinnerData> dinnerDatas = new ArrayList<>();
        cursor = db.query(dinnerTable, null, null, null, null, null, null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            dinnerDatas.add(new DinnerData(cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4)));
            cursor.moveToNext();
        }
        return dinnerDatas;
    }

    public DinnerData getDinnerData(int position) {
        cursor = db.query(dinnerTable, null, null, null, null, null, null);
        cursor.moveToPosition(position);
        return new DinnerData(cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4));
    }

    public int getSize() {
        cursor = db.query(dinnerTable, null, null, null, null, null, null);

        return cursor.getCount();
    }
}
