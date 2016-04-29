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
    private String cmdOrSearch, cmdAndSearch, cmdSubquery, cmdGetTag;

    private SQLiteDatabase db;
    private Cursor cursor;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = this.getWritableDatabase();
        //重設cmd
        resetCmds();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //創建dinnerTable及tagTable
        db.execSQL(createDinnerTable);
        db.execSQL(createTagTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
    //OR搜尋
    public ArrayList<Integer> orSearch(ArrayList<String> tags) {

        //組成OR搜尋的指令
        cmdSubquery = cmdSubquery + "'" + tags.get(0) + "' ";
        for (int i = 1; i < tags.size(); i++) {
            cmdSubquery = cmdSubquery + "or '" + tags.get(i) + "'";
        }
        cmdSubquery = cmdSubquery + ") ";

        cmdOrSearch = cmdOrSearch + cmdSubquery;
        //下指令
        cursor = db.rawQuery(cmdOrSearch, null);
        //移動到第一筆資料
        cursor.moveToFirst();
        //宣告一個ArrayList<Integer> 用來存放符合搜尋的ID
        ArrayList<Integer> ids = new ArrayList<>();
        for (int i = 0; i < cursor.getCount(); i++) {
            ids.add(cursor.getInt(i));
            cursor.moveToNext();
        }
        //回傳ID
        return ids;
    }

    //重設搜尋命令
    public void resetCmds() {
        cmdSubquery = "(select t_id,tag from " + tagTable + " where tag = ";
        cmdOrSearch = "select distinct t_id from ";
        cmdAndSearch = "select t_id from ";
        cmdGetTag = "select tag from " + tagTable + " where t_id = ";
    }

    //判斷是否資料庫為空
    public boolean isEmpty() {
        cursor = db.query(dinnerTable, null, null, null, null, null, null);
        // 如果沒有第一筆資料 回傳false
        return !cursor.moveToFirst();
    }
    //拿取dinnerTable中最後一筆資料(最新加進去的)的ID
    public int getLastDinnerId() {
        cursor = db.query(dinnerTable, null, null, null, null, null, null);
        cursor.moveToLast();
        return cursor.getInt(0);
    }
    //加入tagTable
    public void insertTag(int id, ArrayList<String> tags) {
        ContentValues contentValues;
        for (String tag : tags) {
            //每個TAG分別為一筆 加入資料庫
            contentValues = new ContentValues();
            contentValues.put("t_id", id);
            contentValues.put("tag", tag);
            db.insert(tagTable, null, contentValues);
        }
    }
    //同時加入dinnerTable&tagTable
    public void insertDinnerAndTag(DinnerData dinnerData) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("shop", dinnerData.shop);
        contentValues.put("meal", dinnerData.meal);
        contentValues.put("price", dinnerData.price);
        contentValues.put("tag", dinnerData.tag);
        db.insert(dinnerTable, null, contentValues);
        //拿取剛剛加進去的那筆的ID
        insertTag(getLastDinnerId(), dinnerData.getTags());
    }

    public String getTag(int id) {
        Cursor tagCursor;
        String s = "";
        cmdGetTag = cmdGetTag + id;
        tagCursor = db.rawQuery(cmdGetTag, null);

        if (tagCursor.moveToFirst()) {
            tagCursor.moveToFirst();
            s = tagCursor.getString(0);
            for (int i = 1; i < tagCursor.getCount(); i++) {
                s = s + "," + tagCursor.getString(0);
                tagCursor.moveToNext();
            }
        }
        resetCmds();
        return s;
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
