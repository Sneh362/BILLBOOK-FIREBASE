package com.example.myapp;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Expenses extends AppCompatActivity {
    EditText inSource;
    EditText money;
    Button addButtofn;
    BottomNavigationView bottomNavigationView;
    @Override
    public void onBackPressed() {
        // Do nothing
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);
        Databasehelper db = new Databasehelper(this);
        inSource=findViewById(R.id.expenseSource);
        money=findViewById(R.id.expenseAmount);
       addButtofn=findViewById(R.id.absButton);
         bottomNavigationView =findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.giveMoney);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MySharedPref",MODE_PRIVATE);
        addButtofn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId=sharedPreferences.getString("userId","");
                if(inSource.getText().toString().equals("")||money.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"All Field Mandotory",Toast.LENGTH_SHORT).show();
                }
                else{

                    Integer mon=Integer.parseInt(money.getText().toString());
                    mon=mon-2*mon;
                    System.out.println("djivsnu dskv  "+userId);
                    Boolean res=db.addMoney(inSource.getText().toString(),mon,userId);
                    if(res){
                        inSource.setText("");
                        money.setText("");
                        Toast.makeText(getApplicationContext(),"Added Successfully",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Something went Wrong",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {


            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),HomePage.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.addMoney:
                        startActivity(new Intent(getApplicationContext(),AddMoney.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.giveMoney:
                        return true;
                }
                return false;
            }


            }

         );
}

}
