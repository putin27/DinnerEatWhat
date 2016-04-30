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
        view.switchToFinalDinner(model.getFinalDinnerFast());
    }

    public void onChooseDinnerClick() {
        view.switchToChooseDinner();
    }

    public void onGoAddDinnerClick() {
        view.switchToAddDinner();
    }

    public void onTagChooseDinnerClick(int searchType,String tags) {
            view.switchToFinalDinner(model.getFinalDinnerByTag(searchType,tags));
    }

    public void onChooseAgainClick() {
        view.chooseAgain(model.getAgain());
    }

    public void onKeyDown() {
        view.switchToMainView();
    }

    public void onEditButtonClick(int position) {

    }

    public void onGoEditDinnerClick() {
        view.initDinnerRecyeclerView(model.getAllDinnerData());
    }

    //按下加入晚餐頁面的新增按鈕
    public void onAddDinnerClick(DinnerData dinnerData) {
        //叫model去加入晚餐
        model.addDinnerData(dinnerData);
        //叫view顯示成功加入訊息給使用者
        view.showToast("新增成功!!!");
    }
}
