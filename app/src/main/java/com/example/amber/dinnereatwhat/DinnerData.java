package com.example.amber.dinnereatwhat;

import java.util.ArrayList;
import java.util.Collections;

//餐點資料格式
public class DinnerData {

    private int id;
    private String shop;
    private String meal;
    private int price;
    private String tag;
    private int recommend = 0;

    public DinnerData() {

    }

    public DinnerData(int id, String shop, String meal, int price, String tag, int recommend) {
        this.id = id;
        this.shop = shop;
        this.meal = meal;
        this.price = price;
        this.tag = tag;
        this.recommend = recommend;
    }

    public DinnerData(int id, String shop, String meal, int price, String tag) {
        this.id = id;
        this.shop = shop;
        this.meal = meal;
        this.price = price;
        this.tag = tag;
    }

    public DinnerData(String shop, String meal, int price, String tag) {
        this.shop = shop;
        this.meal = meal;
        this.price = price;
        this.tag = tag;
    }

    public DinnerData(String shop, String meal, int price, String tag, int recommend) {
        this.shop = shop;
        this.meal = meal;
        this.price = price;
        this.tag = tag;
        this.recommend = recommend;
    }

    public String toString() {
        return shop + "的" + meal;
    }

    public int getId() {
        return id;
    }

    public String getShop() {
        return shop;
    }

    public String getMeal() {
        return meal;
    }

    public int getPrice() {
        return price;
    }

    public boolean isTagEmpty() {
        return tag.trim().isEmpty();
    }

    public String getTag() {
        if (tag.isEmpty()) {
            return "目前沒有TAG";
        }
        return tag;
    }

    public static ArrayList<String> getTags(String tag) {
        ArrayList<String> tags = new ArrayList<>();
        String[] tagArray;
        tagArray = tag.trim().split(",");
        Collections.addAll(tags, tagArray);
        return tags;
    }

    //回傳分割好的tag
    public ArrayList<String> getTags() {
        ArrayList<String> tags = new ArrayList<>();
        String[] tagArray;
        tagArray = tag.trim().split(",");
        Collections.addAll(tags, tagArray);
        return tags;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRecommend() {
        return recommend;
    }
}
