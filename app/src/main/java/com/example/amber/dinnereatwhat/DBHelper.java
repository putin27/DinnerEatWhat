package com.example.amber.dinnereatwhat;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

//table的第一筆資料 cursor的position從0開始 裡面的_id是第0個欄位 所以資料從1開始取

public class DBHelper extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "dinnerDatabase";
    private final static int DATABASE_VERSION = 1;
    private String tableName = "dinner";
    private String createDinnerTable = "CREATE TABLE IF NOT EXISTS " + tableName
            + " ("
            + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "shop CHAR, meal CHAR, price INT,tag CHAR"
            + ")";

    private SQLiteDatabase db;
    private Cursor cursor;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createDinnerTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    //判斷是否資料庫為空
    public boolean isEmpty() {
        cursor = db.query(tableName, null, null, null, null, null, null);
        // 如果沒有第一筆資料 回傳false
        return !cursor.moveToFirst();

    }

    public void insertDinner(DinnerData dinnerData) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("shop", dinnerData.shop);
        contentValues.put("meal", dinnerData.meal);
        contentValues.put("price", dinnerData.price);
        contentValues.put("tag", dinnerData.tag);
        db.insert(tableName, null, contentValues);
    }

    public ArrayList<DinnerData> getAllDinnerData() {
        ArrayList<DinnerData> dinnerDatas = new ArrayList<>();
        cursor = db.query(tableName, null, null, null, null, null, null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            dinnerDatas.add(new DinnerData(cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4)));
            cursor.moveToNext();
        }
        return dinnerDatas;
    }

    public DinnerData getDinnerData(int position) {
        cursor = db.query(tableName, null, null, null, null, null, null);
        cursor.moveToPosition(position);
        return new DinnerData(cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4));
    }

    public int getSize() {
        cursor = db.query(tableName, null, null, null, null, null, null);

        return cursor.getCount();
    }
}
