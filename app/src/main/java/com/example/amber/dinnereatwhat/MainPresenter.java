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

    public void onFastChooseDinnerClick(int recommend) {
        view.switchToFinalDinner(model.getFinalDinnerByTag(SearchType.OR, "", "", -1, -1, recommend));
    }

    public void onChooseDinnerClick() {
        view.switchToChooseDinner();
    }

    public void onGoAddDinnerClick() {
        view.switchToAddDinner();
    }

    public void onTagChooseDinnerClick(int searchType, String needTtags, String exceptTags, int price1, int price2, int recommend) {
        view.switchToFinalDinner(model.getFinalDinnerByTag(searchType, needTtags, exceptTags, price1, price2, recommend));
    }

    public void onChooseAgainClick() {
        view.chooseAgain(model.getAgain());
    }

    public void onKeyDown() {
        view.switchToMainView();
    }

    public void onGoEditButtonClick(int position) {
        view.switchToEditDinner(model.getDinnerDataByPosition(position));
    }

    public void onGoViewDinnerClick() {
        view.switchToViewDinner(model.getDinnnerDataWithoutRecommend());
    }

    public void onShowRecommendClick(boolean isChecked) {
        if(isChecked) {
            view.initDinnerRecyeclerView(model.getAllDinnerData());
        }
        else {
            view.initDinnerRecyeclerView(model.getDinnnerDataWithoutRecommend());
        }
    }

    //按下加入晚餐頁面的新增按鈕
    public void onAddDinnerClick(DinnerData dinnerData) {
        //叫model去加入晚餐
        model.addDinnerData(dinnerData);
        //叫view顯示成功加入訊息給使用者
        view.showToast("新增成功!!!");
    }

    public void onEditButtonClick(DinnerData dinnerData) {
        model.updateEditingDinnerData(dinnerData);
        view.showToast("編輯成功!!!");
        view.switchToMainView();
    }

    public void onDelButtonClick() {
        model.delDinnerAndTag();
        view.showToast("刪除成功!!!");
        view.switchToMainView();
    }

}
