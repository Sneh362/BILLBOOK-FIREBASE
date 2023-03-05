package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomePage extends AppCompatActivity {
    RecyclerView moneyRecyclerView;
    List<HashMap<String, String>> moneyList = new ArrayList<>();
    MyAdapter moneyAdapter;
    TextView total;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        total=findViewById(R.id.totalBalance);
        bottomNavigationView =findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.home);
        Databasehelper db = new Databasehelper(this);
        moneyRecyclerView = findViewById(R.id.recyclerview);
        moneyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        moneyRecyclerView.setHasFixedSize(true);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MySharedPref",MODE_PRIVATE);
        String userId=sharedPreferences.getString("userId","");
        Integer tota=db.getTotal(userId);
        moneyList=db.getAllExpenses(userId);
        moneyAdapter = new MyAdapter(moneyList, HomePage.this);
        moneyRecyclerView.setAdapter(moneyAdapter);
        if(tota>=0) {
            total.setTextColor(Color.parseColor("#10B104"));
            total.setText(tota.toString());
        }
        else{
            total.setTextColor(Color.parseColor("#B10404"));
            total.setText(tota.toString());
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
         public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                total.setText(tota.toString());

                switch (item.getItemId())
             {
                 case R.id.addMoney:

                     startActivity(new Intent(getApplicationContext(),AddMoney.class));
                     overridePendingTransition(0,0);
                     return true;

                 case R.id.giveMoney:

                     startActivity(new Intent(getApplicationContext(),Expenses.class));
                     overridePendingTransition(0,0);
                     return true;

                 case R.id.home:

                     return true;
             }
             return false;
         }


                                                                 }

        );
    }
}