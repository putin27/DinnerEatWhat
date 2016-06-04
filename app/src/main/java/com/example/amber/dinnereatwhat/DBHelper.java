package com.example.amber.dinnereatwhat;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

//table的第一筆資料 cursor的position從0開始 裡面的_id是第0個欄位 所以資料從1開始取
//_id會從1開始建立

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
    private String cmdOrSearch, cmdAndSearch, cmdSubquery, cmdExcept, cmdExceptSub, cmdGetTag;

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

    public ArrayList<Integer> search(int searchType, String needTag, String exceptTag, int price1, int price2) {

        String cmdSearch;
        ArrayList<String> needTags = null, exceptTags = null;

        //如果需求tag不是空的
        //建立搜尋tag
        if (!needTag.isEmpty()) {
            needTags = DinnerData.getTags(needTag);
            cmdSubquery = buildCmdSub(cmdSubquery, needTags);
        }
        //如果除外tag不是空的
        //建立搜尋tag
        if (!exceptTag.isEmpty()) {
            exceptTags = DinnerData.getTags(exceptTag);
            cmdExceptSub = buildCmdSub(cmdExceptSub, exceptTags);
        }

        //判斷需求tag是否為空
        if (needTags != null) {
            //判斷是OR還是AND搜尋
            if (searchType == SearchType.OR) {
                if (exceptTags != null) {
                    cmdSearch = "select _id from ("
                            + cmdOrSearch + cmdSubquery
                            + ") inner join (" + cmdExcept + cmdExceptSub + ")"
                            + "on _id = t_id";
                } else {
                    cmdSearch = cmdOrSearch + cmdSubquery;
                }
            } else {
                if (exceptTags != null) {
                    cmdSearch = "select _id from ("
                            + cmdAndSearch + cmdSubquery + "group by t_id having count(t_id)>=" + needTags.size()
                            + ") inner join (" + cmdExcept + cmdExceptSub + ")"
                            + "on _id = t_id";
                } else {
                    cmdSearch = cmdAndSearch + cmdSubquery + "group by t_id having count(t_id)>=" + needTags.size();
                }
            }
        } else {
            if (exceptTags != null) {
                cmdSearch = cmdExcept + cmdExceptSub;
            } else {
                cmdSearch = "select _id from " + dinnerTable;
            }
        }

        cursor = db.rawQuery(cmdSearch,null);
        //宣告一個ArrayList<Integer> 用來存放符合搜尋的ID
        ArrayList<Integer> ids = new ArrayList<>();

        //如果搜尋結果不是NULL
        if (cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                ids.add(cursor.getInt(0));
                cursor.moveToNext();
            }
        }
        //回傳ID
        resetCmds();
        return ids;
    }

    public String buildCmdSub(String cmdSub, ArrayList<String> tags) {
        String newcmdSub;

        newcmdSub = cmdSub;
        newcmdSub = newcmdSub + "'" + tags.get(0) + "' ";
        for (int i = 1; i < tags.size(); i++) {
            newcmdSub = newcmdSub + "or tag = '" + tags.get(i) + "' ";
        }
        newcmdSub = newcmdSub + ")";

        return newcmdSub;
    }

    //重設搜尋命令
    public void resetCmds() {
        cmdSubquery = "(select t_id from " + tagTable + " where tag = ";
        cmdOrSearch = "select distinct t_id from ";
        cmdAndSearch = "select t_id from ";
        cmdExcept = "select _id from " + dinnerTable + " where _id not in ";
        cmdExceptSub = "(select t_id from " + tagTable + " where tag = ";
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
        contentValues.put("shop", dinnerData.getShop());
        contentValues.put("meal", dinnerData.getMeal());
        contentValues.put("price", dinnerData.getPrice());
        contentValues.put("tag", dinnerData.getTag());
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
            dinnerDatas.add(new DinnerData(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4)));
            cursor.moveToNext();
        }
        return dinnerDatas;
    }

    public ArrayList<Integer> fastSearch() {
        ArrayList<Integer> ids = new ArrayList<>();
        cursor = db.query(dinnerTable, null, null, null, null, null, null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            ids.add(cursor.getInt(0));
            cursor.moveToNext();
        }
        return ids;
    }

    public DinnerData getDinnerDataById(int id) {
        String cmdId = "select * from " + dinnerTable + " where _id = " + id;
        cursor = db.rawQuery(cmdId, null);
        cursor.moveToFirst();
        return new DinnerData(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4));
    }

    public DinnerData getDinnerDataByPosition(int position) {
        cursor = db.query(dinnerTable, null, null, null, null, null, null);
        cursor.moveToPosition(position);
        return new DinnerData(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4));
    }

    public int getSize() {
        cursor = db.query(dinnerTable, null, null, null, null, null, null);

        return cursor.getCount();
    }

    public void delTagById(int id) {
        db.delete(tagTable, "t_id = " + id, null);
    }

    public void delDinnerAndTag(int id) {
        db.delete(tagTable, "t_id = " + id, null);
        db.delete(dinnerTable, "_id = " + id, null);
    }

    public void updateDinnerData(DinnerData dinnerData) {
        //更新dinnerTable
        ContentValues values = new ContentValues();
        values.put("shop", dinnerData.getShop());
        values.put("meal", dinnerData.getMeal());
        values.put("price", dinnerData.getPrice());
        values.put("tag", dinnerData.getTag());
        db.update(dinnerTable, values, "_id = " + dinnerData.getId(), null);

        //刪除舊的tag
        delTagById(dinnerData.getId());
        //寫入新的tag
        insertTag(dinnerData.getId(), dinnerData.getTags());
    }
}
