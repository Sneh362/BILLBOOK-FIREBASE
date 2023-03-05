package com.example.myapp;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;

public class MoneyHolder extends RecyclerView.ViewHolder {

    TextView purposeView;
    TextView AmountView;

    public MoneyHolder(View itemView) {
        super(itemView);
        purposeView = itemView.findViewById(R.id.card_purpose);
        AmountView = itemView.findViewById(R.id.card_amount);

    }

    public void bindData(final HashMap<String, String> todoMap) {
        purposeView.setText(todoMap.get("purpose"));
        Integer abc= Integer.valueOf(todoMap.get("amount"));
        if(abc>=0){
            AmountView.setTextColor(Color.parseColor("#10B104"));
            AmountView.setText(todoMap.get("amount"));
        }
        else{
            AmountView.setTextColor(Color.parseColor("#B10404"));
            AmountView.setText(todoMap.get("amount"));
        }


    }
}
