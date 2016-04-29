package com.example.amber.dinnereatwhat;

import java.util.ArrayList;
import java.util.Random;

//資料處理&存放處
public class MainModel {
    private MainActivity activity;
    private DBHelper dbHelper;

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
        if(!dinnerData.isTagEmpty()) {
            ArrayList<String> tags = new ArrayList<>();
            dbHelper.insertTag(dbHelper.getLastDinnerId(),tags);
        }
    }
    //取得所有的晚餐資料
    public ArrayList<DinnerData> getAllDinnerData() {
        return dbHelper.getAllDinnerData();
    }

    //快速隨機取得晚餐
    public DinnerData getFinalDinnerFast() {
        Random random = new Random();
        return dbHelper.getDinnerData(random.nextInt(dbHelper.getSize()));
    }
}
