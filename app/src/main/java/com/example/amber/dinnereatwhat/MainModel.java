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
            dbHelper.insertDinnerAndTag(new DinnerData("麥當勞", "雞塊餐", 99, "雞肉,炸物"));
            dbHelper.insertDinnerAndTag(new DinnerData("麥當勞", "大麥克餐", 109, "牛肉,炸物"));
            dbHelper.insertDinnerAndTag(new DinnerData("麥當勞", "豬肉滿福堡餐", 59, "豬肉,早餐"));
            dbHelper.insertDinnerAndTag(new DinnerData("7-11", "義大利麵", 75, "麵"));
            dbHelper.insertDinnerAndTag(new DinnerData("7-11", "咖哩飯", 58, "飯,咖哩"));
            dbHelper.insertDinnerAndTag(new DinnerData("阿姨早餐店", "起司蛋餅", 30, "草餐"));
            dbHelper.insertDinnerAndTag(new DinnerData("珍膳坊", "牛肉麵", 80, "麵,牛肉"));
            dbHelper.insertDinnerAndTag(new DinnerData("珍膳坊", "炒飯",55, "飯"));
            dbHelper.insertDinnerAndTag(new DinnerData("珍膳坊", "豬肉水餃", 40, "豬肉,水餃"));
            dbHelper.insertDinnerAndTag(new DinnerData("江山海", "雞排麵", 75, "雞肉,麵"));
            dbHelper.insertDinnerAndTag(new DinnerData("江山海", "黯然銷魂飯", 65, "飯"));
            dbHelper.insertDinnerAndTag(new DinnerData("上匠", "炸雞腿飯", 70, "飯,炸物,雞肉"));
            dbHelper.insertDinnerAndTag(new DinnerData("上匠", "魚排飯", 65, "飯,魚肉"));
            dbHelper.insertDinnerAndTag(new DinnerData("擄胃專家", "排骨飯", 70, "飯"));
            dbHelper.insertDinnerAndTag(new DinnerData("天狗", "香脆雞腿排", 140, "飯,麵,雞肉"));
            dbHelper.insertDinnerAndTag(new DinnerData("天狗", "天狗牛排", 140, "飯,麵,牛肉"));
            dbHelper.insertDinnerAndTag(new DinnerData("王品", "法式鵝肝佐松露菲力", 1350, "牛肉,西餐"));
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

    public DinnerData getFinalDinnerByTag(int searchType, String needTtags, String exceptTags, int price1, int price2) {

        this.ids = dbHelper.search(searchType, needTtags, exceptTags, price1, price2);

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
