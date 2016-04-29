package com.example.amber.dinnereatwhat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

//這邊是主要的VIEW
//主要接收使用者的動作和顯示螢幕
//接收到使用者動作後，通知presenter去處理
public class MainActivity extends AppCompatActivity implements MainView, DinnerAdapter.EditButtonListener {

    private MainPresenter presenter;
    private int searchType = SearchType.or;

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
        setContentView(R.layout.add_dinner);
    }

    @Override
    public void switchToChooseDinner() {
        setContentView(R.layout.choose);
        RadioButton rbOr = (RadioButton) findViewById(R.id.rb_or);
        assert rbOr != null;
        rbOr.setChecked(true);
    }

    @Override
    public void switchToMainView() {
        setContentView(R.layout.activity_main);
    }

    //初始化RecyeclerView
    @Override
    public void initDinnerRecyeclerView(ArrayList<DinnerData> dinnerDatas) {
        //切換至edit_dinner
        setContentView(R.layout.edit_dinner);
        //宣告&new出自訂的Adapter
        DinnerAdapter dinnerAdapter = new DinnerAdapter(dinnerDatas, this);
        //取得RecyeclerView
        RecyclerView recyclerView = getRecyclerView();
        //設定RecyeclerView的屬性
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //給RecyeclerView自訂的Adapter
        recyclerView.setAdapter(dinnerAdapter);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void switchToFinalDinner(DinnerData dinnerData) {
        //依照傳入的DinerData設定顯示的文字
        setContentView(R.layout.final_dinner);
        TextView textView1 = (TextView) findViewById(R.id.tv_finaldinner);
        assert textView1 != null;
        textView1.setText(dinnerData.toString());
        TextView textView2 = (TextView) findViewById(R.id.tv_price);
        assert textView2 != null;
        textView2.setText(dinnerData.getPrice() + "元");
    }

    @Override
    public void chooseAgain(DinnerData dinnerData) {
        //在final_dinner頁面下
        //依照傳入的DinerData設定顯示的文字
        TextView textView = (TextView) findViewById(R.id.tv_finaldinner);
        assert textView != null;
        textView.setText(dinnerData.toString());
        TextView textView2 = (TextView) findViewById(R.id.tv_price);
        assert textView2 != null;
        textView2.setText(dinnerData.getPrice() + "元");
    }

    //手機實體鍵
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //按下返回會回到主頁面
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            presenter.onKeyDown();
        }

        return false;
    }

    //按下編輯頁面的編輯按鈕
    @Override
    public void onEditButtonClick(int position) {
        presenter.onEditButtonClick(position);
    }

    //取得RecyclerView
    private RecyclerView getRecyclerView() {
        return (RecyclerView) findViewById(R.id.dinner_recycler_view);
    }

    public void onFastChooseDinnerClick(View view) {
        presenter.onFastChooseDinnerClick();
    }

    public void onChooseDinnerClick(View view) {
        presenter.onChooseDinnerClick();
    }

    //按下加入晚餐頁面的新增按鈕
    public void onAddDinnerClick(View view) {
        DinnerData dinnerData;
        //資料是否正確
        boolean isDataCorrect = true;
        EditText etStore = (EditText) findViewById(R.id.et_store);
        EditText etMeal = (EditText) findViewById(R.id.et_meal);
        EditText etPrice = (EditText) findViewById(R.id.et_price);
        EditText etTag = (EditText) findViewById(R.id.et_tag);
        //檢查用陣列
        EditText[] check = {etStore, etMeal, etPrice};
        //檢查店名、餐點名稱、價格是否有填入
        for (EditText aCheck : check) {
            //如果為空
            if (aCheck.getText().toString().trim().isEmpty()) {
                //聚焦在該EditText上
                aCheck.requestFocus();
                //顯示錯誤訊息給使用者
                showToast("該項為必填項目!!!");
                //設定資料還沒準備好
                isDataCorrect = false;
                break;
            }
        }
        //如果資料還沒準備好就不寫入資料庫
        //如果資料準備好才寫入資料庫
        if (isDataCorrect) {
            assert etStore != null;
            assert etPrice != null;
            assert etMeal != null;
            assert etTag != null;
            dinnerData = new DinnerData(etStore.getText().toString(), etMeal.getText().toString(),
                    Integer.parseInt(etPrice.getText().toString(), 10), etTag.getText().toString());
            presenter.onAddDinnerClick(dinnerData);
        }
    }

    public void onTagChooseDinnerClick(View view) {
        presenter.onTagChooseDinnerClick();
    }

    public void onChooseAgainClick(View view) {
        presenter.onChooseAgainClick();
    }


    public void onGoAddDinnerClick(View view) {
        presenter.onGoAddDinnerClick();
    }

    public void onGoEditDinnerClick(View view) {
        presenter.onGoEditDinnerClick();
    }

    public void onRadioButtonClick(View view) {

        switch (view.getId()) {
            case R.id.rb_and:
                searchType = SearchType.and;
                break;
            case R.id.rb_or:
                searchType = SearchType.or;
                break;
        }
    }
}