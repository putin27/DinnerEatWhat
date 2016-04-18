package com.example.amber.dinnereatwhat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

//這邊是主要的VIEW
//主要接收使用者的動作和顯示螢幕
//接收到使用者動作後，通知presenter去處理
public class MainActivity extends AppCompatActivity implements MainView {

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MainPresenter(this, new MainModel(this));
        //讓presenter啟動開始程序
        presenter.onCreat();
    }

    //test
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void switchToAddDinner() {
        setContentView(R.layout.newdinner);
    }

    @Override
    public void switchToChooseDinner() {
        setContentView(R.layout.choose);
    }


    @Override
    public void switchToFinalDinner(DinnerData dinnerData) {
        setContentView(R.layout.final_dinner);
        TextView textView = (TextView) findViewById(R.id.tv_finaldinner);
        assert textView != null;
        textView.setText(dinnerData.toString());
    }

    @Override
    public void chooseAgain(DinnerData dinnerData) {
        TextView textView = (TextView) findViewById(R.id.tv_finaldinner);
        assert textView != null;
        textView.setText(dinnerData.toString());
    }


    public void onFastChooseDinnerClick(View view) {
        presenter.onFastChooseDinnerClick();
    }

    public void onChooseDinnerClick(View view) {
        presenter.onChooseDinnerClick();
    }

    public void onAddDinnerClick(View view) {
        presenter.onAddDinnerClick();
    }

    public void onTagChooseDinnerClick(View view) {
        presenter.onTagChooseDinnerClick();
    }

    public void onChooseAgainClick(View view) {
        presenter.onChooseAgainClick();
    }
}
