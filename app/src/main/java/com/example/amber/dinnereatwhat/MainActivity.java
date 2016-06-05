package com.example.amber.dinnereatwhat;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
    private int searchType = SearchType.OR;

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
        searchType = SearchType.OR;
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
        setContentView(R.layout.view_dinner);
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
    public void switchToEditDinner(DinnerData dinnerData) {
        setContentView(R.layout.edit_dinner);
        EditText etShop, etMeal, etPrice, etTag;
        etShop = (EditText) findViewById(R.id.edit_dinner_et_shop);
        etMeal = (EditText) findViewById(R.id.edit_dinner_et_meal);
        etPrice = (EditText) findViewById(R.id.edit_dinner_et_price);
        etTag = (EditText) findViewById(R.id.edit_dinner_et_tag);

        assert etShop != null;
        etShop.setText(dinnerData.getShop());
        assert etMeal != null;
        etMeal.setText(dinnerData.getMeal());
        assert etPrice != null;
        etPrice.setText("" + dinnerData.getPrice());
        assert etTag != null;
        etTag.setText(dinnerData.getTag());
    }

    @Override
    public void switchToFinalDinner(DinnerData dinnerData) {
        //依照傳入的DinerData設定顯示的文字
        setContentView(R.layout.final_dinner);
        setFinalDinner(dinnerData);
    }

    @Override
    public void chooseAgain(DinnerData dinnerData) {
        setFinalDinner(dinnerData);
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
    public void onGoEditButtonClick(int position) {
        presenter.onGoEditButtonClick(position);
    }

    private void setFinalDinner(DinnerData dinnerData) {
        TextView tvShop, tvMeal, tvPrice;

        tvShop = (TextView) findViewById(R.id.final_dinner_tv_shop);
        assert tvShop != null;
        tvMeal = (TextView) findViewById(R.id.final_dinner_tv_meal);
        assert tvMeal != null;
        tvPrice = (TextView) findViewById(R.id.final_dinner_tv_price);
        assert tvPrice != null;

        if (dinnerData.getShop().equals("null")) {
            TextView tvTilte = (TextView) findViewById(R.id.final_dinner_tv_title);
            TextView tvDer = (TextView) findViewById(R.id.final_dinner_tv_der);
            assert tvTilte != null;
            tvTilte.setText("無搜尋結果");
            assert tvDer != null;
            tvDer.setText("");
            tvShop.setText("");
            tvMeal.setText("");
            tvPrice.setText("");
            return;
        }

        tvShop.setText(dinnerData.getShop());
        tvMeal.setText(dinnerData.getMeal());
        tvPrice.setText(dinnerData.getPrice() + "元");

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
        EditText etStore = (EditText) findViewById(R.id.et_shop);
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
                return;
            }
        }
        //如果資料還沒準備好就不寫入資料庫
        //如果資料準備好才寫入資料庫
        assert etStore != null;
        assert etPrice != null;
        assert etMeal != null;
        assert etTag != null;
        dinnerData = new DinnerData(etStore.getText().toString(), etMeal.getText().toString(),
                Integer.parseInt(etPrice.getText().toString(), 10), etTag.getText().toString());
        presenter.onAddDinnerClick(dinnerData);
        //新增成功後清空
        for (EditText aCheck : check) {
            aCheck.setText("");
        }
    }

    public void onTagChooseDinnerClick(View view) {
        //如果沒有輸入價錢 預設值為-1
        int price1 = -1, price2 = -1;
        EditText etNeedTag, etExceptTag, etPrice1, etPrice2;

        etNeedTag = (EditText) findViewById(R.id.choose_et_need_tag);
        etExceptTag = (EditText) findViewById(R.id.choose_et_except_tag);
        etPrice1 = (EditText) findViewById(R.id.choose_et_price1);
        etPrice2 = (EditText) findViewById(R.id.choose_et_price2);

        assert etNeedTag != null;
        assert etExceptTag != null;
        assert etPrice1 != null;
        assert etPrice2 != null;
        //如果輸入不為空 讀取輸入的數值並儲存
        if (!etPrice1.getText().toString().trim().isEmpty()) {
            price1 = Integer.parseInt(etPrice1.getText().toString().trim());
            if(price1<0){
                showToast("價格不可為負數!");
                return;
            }
        }
        if (!etPrice2.getText().toString().trim().isEmpty()) {
            price2 = Integer.parseInt(etPrice2.getText().toString().trim());
            if(price2<0){
                showToast("價格不可為負數!");
                return;
            }
        }

        presenter.onTagChooseDinnerClick(searchType, etNeedTag.getText().toString().trim(), etExceptTag.getText().toString().trim(),
                price1, price2);
    }

    public void onChooseAgainClick(View view) {
        presenter.onChooseAgainClick();
    }


    public void onGoAddDinnerClick(View view) {
        presenter.onGoAddDinnerClick();
    }

    public void onGoViewDinnerClick(View view) {
        presenter.onGoViewDinnerClick();
    }

    public void onRadioButtonClick(View view) {
        switch (view.getId()) {
            case R.id.rb_and:
                searchType = SearchType.AND;
                break;
            case R.id.rb_or:
                searchType = SearchType.OR;
                break;
        }
    }

    public void onEditButtonClick(View view) {
        EditText etShop, etMeal, etPrice, etTag;
        etShop = (EditText) findViewById(R.id.edit_dinner_et_shop);
        etMeal = (EditText) findViewById(R.id.edit_dinner_et_meal);
        etPrice = (EditText) findViewById(R.id.edit_dinner_et_price);
        etTag = (EditText) findViewById(R.id.edit_dinner_et_tag);

        assert etShop != null;
        assert etMeal != null;
        assert etPrice != null;
        assert etTag != null;
        DinnerData dinnerData = new DinnerData(etShop.getText().toString(), etMeal.getText().toString(),
                Integer.parseInt(etPrice.getText().toString(), 10), etTag.getText().toString());
        presenter.onEditButtonClick(dinnerData);
    }

    public void onDelButtonClick(View view) {
        //跳出警告訊息
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("警告");
        builder.setMessage("你確定要刪除此晚餐嗎?");
        builder.setNegativeButton("刪除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //刪除
                MainActivity.this.presenter.onDelButtonClick();
            }
        });
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //顯示取消刪除
                showToast("取消刪除!");
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}