package com.example.amber.dinnereatwhat;


import java.util.ArrayList;

public interface MainView {


    void setContentView();

    void switchToAddDinner();

    void switchToChooseDinner();

    void switchToFinalDinner(DinnerData dinnerData);

    void chooseAgain(DinnerData dinnerData);

    void switchToMainView();

    void initDinnerRecyeclerView(ArrayList<DinnerData> dinnerDatas);

    void showToast(String msg);

    void switchToEditDinner(DinnerData dinnerData);

    void switchToViewDinner(ArrayList<DinnerData> dinnerData);
}