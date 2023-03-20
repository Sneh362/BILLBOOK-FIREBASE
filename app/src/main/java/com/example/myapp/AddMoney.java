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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddMoney extends AppCompatActivity {
    EditText inSource;
    EditText money;
    Button addButton;
    BottomNavigationView bottomNavigationView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    @Override
    public void onBackPressed() {
        // Do nothing
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);
        Databasehelper db = new Databasehelper(this);
        inSource=findViewById(R.id.incomeSource);
        money=findViewById(R.id.incomeAmount);
        addButton=findViewById(R.id.addIncomeButton);
        bottomNavigationView =findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.addMoney);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MySharedPref",MODE_PRIVATE);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Income");

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId=sharedPreferences.getString("userId","");
            if(inSource.getText().toString().equals("")||money.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(),"All Field Mandotory",Toast.LENGTH_SHORT).show();
            }
            else{

                Integer mon=Integer.parseInt(money.getText().toString());
                System.out.println("djivsnu dskv  "+userId);

                MoneyFire moneyFire=new MoneyFire(mon,inSource.getText().toString(),userId);
                Long tsLong = System.currentTimeMillis() / 1000;
                String ts = tsLong.toString();
                databaseReference.child(ts).setValue(moneyFire);
                inSource.setText("");
                money.setText("");
                Toast.makeText(AddMoney.this, "Task Added to firebase", Toast.LENGTH_SHORT).show();
//                Boolean res=db.addMoney(inSource.getText().toString(),mon,userId);
//                if(res){
//                    inSource.setText("");
//                    money.setText("");
//                    Toast.makeText(getApplicationContext(),"Added Successfully",Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    Toast.makeText(getApplicationContext(),"Something went Wrong",Toast.LENGTH_SHORT).show();
//                }

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

                         case R.id.giveMoney:
                             startActivity(new Intent(getApplicationContext(),Expenses.class));
                             overridePendingTransition(0,0);
                             return true;

                         case R.id.addMoney:
                             return true;
                     }
                     return false;
                 }


             }

        );
    }
}
