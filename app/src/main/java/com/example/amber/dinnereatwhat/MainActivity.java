package com.example.amber.dinnereatwhat;
//test
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements MainView{

    private  MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new MainPresenter(this,new MainModel(this));
        presenter.onCreat();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_main);
    }

    public void onChoseDinnerClick(View view) {
        showToast(presenter.getFristDinnerData());
    }

    public void onAddDinnerClick(View view) {
        presenter.addDinner(new DinnerData());
    }

    public void showToast(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }
}
