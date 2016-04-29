package com.example.amber.dinnereatwhat;

import java.util.ArrayList;
import java.util.Collections;

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
    //回傳分割好的tag
    public ArrayList<String> getTags() {
        ArrayList<String> tags = new ArrayList<>();
        String[] tagArray;
        tagArray = tag.trim().split(",");
        Collections.addAll(tags, tagArray);
        return tags;
    }
}
