package com.example.myapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
public class MyAdapter extends RecyclerView.Adapter<MoneyHolder> {

    private List<HashMap<String, String>> moneyList;


    public MyAdapter(List<HashMap<String, String>> moneyList, HomePage homePage) {
        this.moneyList = moneyList;

    }

    @Override
    public MoneyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new MoneyHolder(view);
    }



    @Override
    public void onBindViewHolder(MoneyHolder holder, int position) {
        holder.bindData(moneyList.get(position));
    }

    @Override
    public int getItemCount() {
        return moneyList.size();
    }

}
