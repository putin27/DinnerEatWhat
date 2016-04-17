package com.example.amber.dinnereatwhat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

//這邊是主要的VIEW
//主要接收使用者的動作和顯示螢幕
//接收到使用者動作後，通知presenter去處理
public class MainActivity extends AppCompatActivity implements MainView{

    private  MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new MainPresenter(this,new MainModel(this));
        //讓presenter啟動開始程序
        presenter.onCreat();
    }
    //test
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_main);
    }

    public void onChoseDinnerClick(View view) {
        showToast(presenter.getFristDinnerData());
    }

    public void onAddDinnerClick(View view) {
        presenter.onAddDinnerClick(new DinnerData());
    }

    public void showToast(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }
}
