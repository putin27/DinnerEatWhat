package com.example.amber.dinnereatwhat;

//餐點資料格式
public class DinnerData {

    protected String shop;
    protected String meal;
    protected int price;
    protected String tag;

    public DinnerData() {

    }

    public DinnerData(String shop, String meal, int price, String tag) {
        this.shop = shop;
        this.meal = meal;
        this.price = price;
        this.tag = tag;
    }

    public String toString() {
        return shop + "的" + meal;
    }
    public String getPrice() {
        return price + "元";
    }
}
