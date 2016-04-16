package com.example.amber.dinnereatwhat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements MainView{

    private  MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new MainPresenter(this,new MainModel());
        presenter.onCreat();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_main);
    }

    public void onChoseDinnerClick(View view) {
    }

    public void onAddDinnerClick(View view) {
    }
}
