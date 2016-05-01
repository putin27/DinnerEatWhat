package com.example.amber.dinnereatwhat;

import java.util.ArrayList;
import java.util.Random;

//資料處理&存放處
public class MainModel {
    private MainActivity activity;
    private DBHelper dbHelper;
    private ArrayList<Integer> ids;
    private int editingId;

    public MainModel(MainActivity activity) {
        this.activity = activity;
        initial();
    }

    public void initial() {
        dbHelper = new DBHelper(activity);
        //如果沒有資料，就載入7筆預設資料
        if (dbHelper.isEmpty()) {
            dbHelper.insertDinnerAndTag(new DinnerData("麥當勞", "雞塊餐", 99, "A"));
            dbHelper.insertDinnerAndTag(new DinnerData("KLG", "C餐", 109, "B"));
            dbHelper.insertDinnerAndTag(new DinnerData("7-11", "義大利麵", 75, "C"));
            dbHelper.insertDinnerAndTag(new DinnerData("阿姨早餐店", "起司蛋餅", 30, "D"));
            dbHelper.insertDinnerAndTag(new DinnerData("珍膳坊", "牛肉麵", 80, "E"));
            dbHelper.insertDinnerAndTag(new DinnerData("江山海", "雞排麵", 75, "F"));
            dbHelper.insertDinnerAndTag(new DinnerData("天狗", "雞排鐵板麵", 75, "G"));
        }
    }

    //加入晚餐
    public void addDinnerData(DinnerData dinnerData) {
        dbHelper.insertDinnerAndTag(dinnerData);
        if (!dinnerData.isTagEmpty()) {
            ArrayList<String> tags = new ArrayList<>();
            dbHelper.insertTag(dbHelper.getLastDinnerId(), tags);
        }
    }

    public DinnerData getDinnerDataByPosition(int position) {
        editingId = dbHelper.getDinnerDataByPosition(position).getId();
        return dbHelper.getDinnerDataByPosition(position);
    }

    //取得所有的晚餐資料
    public ArrayList<DinnerData> getAllDinnerData() {
        return dbHelper.getAllDinnerData();
    }

    //快速隨機取得晚餐
    public DinnerData getFinalDinnerFast() {
        this.ids = dbHelper.fastSearch();
        //如果ids裡沒東西 回傳無搜尋結果的預設值
        if (ids.size() == 0) {
            return new DinnerData("null", "", 0, "");
        }
        Random random = new Random();
        return dbHelper.getDinnerDataById(ids.get(random.nextInt(ids.size())));
    }

    public DinnerData getFinalDinnerByTag(int searchType, String tags) {
        //判斷是哪種搜尋模式
        //取得符合搜尋結果的ids
        if (searchType == SearchType.OR) {
            this.ids = dbHelper.orSearch(DinnerData.getTags(tags));
        } else if (searchType == SearchType.AND) {
            this.ids = dbHelper.andSearch(DinnerData.getTags(tags));
        }
        //如果ids裡沒東西 回傳無搜尋結果的預設值
        if (ids.size() == 0) {
            return new DinnerData("null", "", 0, "");
        }
        Random random = new Random();
        //隨機取得其中一個ID
        //並從資料庫中取出
        return dbHelper.getDinnerDataById(ids.get(random.nextInt(ids.size())));
    }

    public DinnerData getAgain() {
        //如果ids裡沒東西 回傳無搜尋結果的預設值
        if (ids.size() == 0) {
            return new DinnerData("null", "", 0, "");
        }
        Random random = new Random();
        return dbHelper.getDinnerDataById(ids.get(random.nextInt(ids.size())));
    }

    public void updateEditingDinnerData(DinnerData dinnerData) {
        dinnerData.setId(editingId);
        dbHelper.updateDinnerData(dinnerData);
    }

    public void delDinnerAndTag() {
        dbHelper.delDinnerAndTag(editingId);
    }
}
