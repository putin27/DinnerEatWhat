package com.example.amber.dinnereatwhat;


public class MainModel {
    private MainActivity activity;
    private DBHelper dbHelper;

    public MainModel(MainActivity activity) {
        this.activity = activity;
        initial();
    }

    public void initial() {
        dbHelper = new DBHelper(activity);
    }

    public void addDinnerData(DinnerData dinnerData) {
        dbHelper.insertDinner(dinnerData);
    }

    public DinnerData getFirstDinnerData() {
        return dbHelper.getDinnerData();
    }
}
