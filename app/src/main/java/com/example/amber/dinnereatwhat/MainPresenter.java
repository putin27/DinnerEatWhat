package com.example.amber.dinnereatwhat;

public class MainPresenter {
    private final MainView view;
    private final MainModel model;

    //建構子，傳入view跟model，讓presenter可以指揮他們
    public MainPresenter(MainView view, MainModel model) {
        this.view = view;
        this.model = model;
    }

    //程式初始化
    //設置一開始的畫面
    public void onCreat() {
        view.setContentView();
    }
    public void onFastChooseDinnerClick() {
        view.switchToFinalDinner();
    }
    public void onChooseDinnerClick() {
        view.switchToChooseDinner();
    }
    public void onAddDinnerClick() {
        view.switchToAddDinner();
    }
    public String getFristDinnerData() {
        DinnerData dinnerData = model.getFirstDinnerData();
        return "shop:"+dinnerData.shop+",meal:"+dinnerData.meal+",price:"+dinnerData.price+",tag:"+dinnerData.tag;
    }


    public void onTagChooseDinnerClick() {
        view.switchToFinalDinner();
    }
}
