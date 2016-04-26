package com.example.amber.dinnereatwhat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

//這邊是主要的VIEW
//主要接收使用者的動作和顯示螢幕
//接收到使用者動作後，通知presenter去處理
public class MainActivity extends AppCompatActivity implements MainView, DinnerAdapter.EditButtonListener {

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
    public void switchToMainView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initDinnerRecyeclerView(ArrayList<DinnerData> dinnerDatas) {

        setContentView(R.layout.edit_dinner);

        DinnerAdapter dinnerAdapter = new DinnerAdapter(dinnerDatas, this);

        RecyclerView recyclerView = getRecyclerView();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(dinnerAdapter);
    }

    @Override
    public void switchToFinalDinner(DinnerData dinnerData) {
        setContentView(R.layout.final_dinner);
        TextView textView1 = (TextView) findViewById(R.id.tv_finaldinner);
        assert textView1 != null;
        textView1.setText(dinnerData.toString());
        TextView textView2 = (TextView) findViewById(R.id.tv_price);
        assert textView2 != null;
        textView2.setText(dinnerData.getPrice()+"元");
    }

    @Override
    public void chooseAgain(DinnerData dinnerData) {
        TextView textView = (TextView) findViewById(R.id.tv_finaldinner);
        assert textView != null;
        textView.setText(dinnerData.toString());
        TextView textView2 = (TextView) findViewById(R.id.tv_price);
        assert textView2 != null;
        textView2.setText(dinnerData.getPrice() + "元");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            presenter.onKeyDown();
        }

        return false;
    }

    @Override
    public void onEditButtonClick(int position) {
        presenter.onEditButtonClick(position);
    }

    private RecyclerView getRecyclerView() {
        return (RecyclerView) findViewById(R.id.dinner_recycler_view);
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


    public void onEditDinnerClick(View view) {
        presenter.onEditDinnerClick();
    }
}