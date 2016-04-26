package com.example.amber.dinnereatwhat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class DinnerAdapter extends RecyclerView.Adapter<DinnerAdapter.ViewHolder> {
    private final ArrayList<DinnerData> dinnerDatas;
    private final EditButtonListener mListener;

    public DinnerAdapter(ArrayList<DinnerData> dinnerDatas, EditButtonListener listener) {
        this.dinnerDatas = dinnerDatas;
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dinenr, parent, false);

        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.dinnerTextView.setText(dinnerDatas.get(position).toString() + dinnerDatas.get(position).getPrice() + "元");

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onEditButtonClick(holder.getLayoutPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dinnerDatas.size();
    }

    public void updateNotes(ArrayList<DinnerData> dinnerDatas) {
        this.dinnerDatas.clear();
        this.dinnerDatas.addAll(dinnerDatas);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView dinnerTextView;
        public Button editButton;

        public ViewHolder(View itemView) {
            super(itemView);
            dinnerTextView = (TextView) itemView.findViewById(R.id.item_dinner_tv);
            editButton = (Button) itemView.findViewById(R.id.item_edit_button);
        }
    }

    public interface EditButtonListener {
        void onEditButtonClick(int position);
    }
}
