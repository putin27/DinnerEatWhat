package com.example.amber.dinnereatwhat;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DinnerAdapter extends RecyclerView.Adapter<DinnerAdapter.ViewHolder> {
    //宣告所需要的物件
    private final ArrayList<DinnerData> dinnerDatas;
    private final EditButtonListener mListener;

    //將傳入的物件設定好
    public DinnerAdapter(ArrayList<DinnerData> dinnerDatas, EditButtonListener listener) {
        this.dinnerDatas = dinnerDatas;
        this.mListener = listener;
    }

    //設定啟動時的動作
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //設定為自訂好的xml
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dinenr, parent, false);
        //傳回ViewHolder
        return new ViewHolder(v);
    }

    //設定每一條項目該做的動作
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //設定晚餐資料
        holder.dinnerTextView.setText(dinnerDatas.get(position).toString() + dinnerDatas.get(position).getPrice() + "元");
        //設定TAG資料
        holder.tagTextView.setText("tags:" + dinnerDatas.get(position).getTag());
        if (position % 2 == 1) {
            holder.dinnerTextView.setTextColor(Color.DKGRAY);
            holder.tagTextView.setTextColor(Color.DKGRAY);
        } else {
            holder.dinnerTextView.setTextColor(Color.BLUE);
            holder.tagTextView.setTextColor(Color.BLUE);
        }

        if (dinnerDatas.get(position).getRecommend() == 1) {
            holder.imageView.setImageResource(R.mipmap.eat);
            holder.imageView.setVisibility(View.VISIBLE);
        }
        else {
            holder.imageView.setVisibility(View.GONE);
        }

        //設定editButton的監聽事件
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onGoEditButtonClick(holder.getLayoutPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dinnerDatas.size();
    }

    //設定更新RecyclerView的方法
    public void updateNotes(ArrayList<DinnerData> dinnerDatas) {
        this.dinnerDatas.clear();
        this.dinnerDatas.addAll(dinnerDatas);
        notifyDataSetChanged();
    }

    //取得每一條裡的元件
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView dinnerTextView, tagTextView;
        public Button editButton;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            dinnerTextView = (TextView) itemView.findViewById(R.id.item_dinner_tv);
            tagTextView = (TextView) itemView.findViewById(R.id.item_dinner_tag);
            editButton = (Button) itemView.findViewById(R.id.item_edit_button);
            imageView = (ImageView) itemView.findViewById(R.id.item_dinner_image);
        }
    }

    //宣告監聽按鈕的interface
    public interface EditButtonListener {
        void onGoEditButtonClick(int position);
    }
}
