package com.example.amber.dinnereatwhat;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//table的第一筆資料 cursor的position從0開始 裡面的_id是第0個欄位 所以資料從1開始取

public class DBHelper extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "item_database";
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
        Log.i("dbhelper", "db open succeed");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public void insertDinner(DinnerData dinnerData) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("shop", dinnerData.shop);
        contentValues.put("meal", dinnerData.meal);
        contentValues.put("price", dinnerData.price);
        contentValues.put("tag", dinnerData.tag);
        db.insert(tableName, null, contentValues);
    }

    public DinnerData getDinnerData() {
        DinnerData dinnerData = new DinnerData();
        cursor = db.query(tableName, null, null, null, null, null, null);
        cursor.moveToFirst();
        dinnerData.shop = cursor.getString(1);
        dinnerData.meal = cursor.getString(2);
        dinnerData.price = cursor.getInt(3);
        dinnerData.tag = cursor.getString(4);
        return dinnerData;
    }
}
