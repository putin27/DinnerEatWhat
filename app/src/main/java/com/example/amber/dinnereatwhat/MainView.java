package com.example.amber.dinnereatwhat;


public interface MainView {


    void setContentView();

    void switchToAddDinner();

    void switchToChooseDinner();

    void switchToFinalDinner(DinnerData dinnerData);

    void chooseAgain(DinnerData dinnerData);
}