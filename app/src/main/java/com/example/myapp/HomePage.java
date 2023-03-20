package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomePage extends AppCompatActivity {
    RecyclerView moneyRecyclerView;
    List<HashMap<String, String>> moneyList = new ArrayList<>();
    MyAdapter moneyAdapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    TextView total;
    BottomNavigationView bottomNavigationView;
    @Override
    public void onBackPressed() {
        // Do nothing
    }
    public void updateTotal(Integer tota){

        if(tota>=0) {
            total.setTextColor(Color.parseColor("#10B104"));
            total.setText(tota.toString());
        }
        else{
            total.setTextColor(Color.parseColor("#B10404"));
            total.setText(tota.toString());
        }
    }
    public void update(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");
        progressDialog.show();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MySharedPref",MODE_PRIVATE);

        String userId=sharedPreferences.getString("userId","");
//        updateTotal();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Income");
        System.out.println("************************************************");
        System.out.println(userId);
        databaseReference.orderByChild("userId").equalTo(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                moneyList.clear();
                Integer tot;
                tot=0;

                for (DataSnapshot taskSnapshot : snapshot.getChildren()) {
                    String moneyId = taskSnapshot.getKey();
                    Integer money = taskSnapshot.child("money").getValue(Integer.class);
                    String desc = taskSnapshot.child("source").getValue(String.class);
                    String userId = taskSnapshot.child("userId").getValue(String.class);
                    HashMap<String, String> taskMap = new HashMap<>();
                    tot=tot+money;
                    taskMap.put("task_id", moneyId);
                    taskMap.put("amount", money.toString());
                    taskMap.put("purpose", desc);
                    taskMap.put("user_id", userId);
                    System.out.println(money);
                    System.out.println(desc);
                    moneyList.add(taskMap);
                }
                updateTotal(tot);
                moneyAdapter.updateData(moneyList);
                progressDialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
            }
        });
    }
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
        update();
//        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MySharedPref",MODE_PRIVATE);
//        String userId=sharedPreferences.getString("userId","");
////        updateTotal();
//        firebaseDatabase=FirebaseDatabase.getInstance();
//        databaseReference=firebaseDatabase.getReference("Income");
//        System.out.println("************************************************");
//        System.out.println(userId);
//        databaseReference.orderByChild("userId").equalTo(userId).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                moneyList.clear();
//                Integer tot;
//                tot=0;
//
//                for (DataSnapshot taskSnapshot : snapshot.getChildren()) {
//                    String moneyId = taskSnapshot.getKey();
//                    Integer money = taskSnapshot.child("money").getValue(Integer.class);
//                    String desc = taskSnapshot.child("source").getValue(String.class);
//                    String userId = taskSnapshot.child("userId").getValue(String.class);
//                    HashMap<String, String> taskMap = new HashMap<>();
//                    tot=tot+money;
//                    taskMap.put("task_id", moneyId);
//                    taskMap.put("amount", money.toString());
//                    taskMap.put("purpose", desc);
//                    taskMap.put("user_id", userId);
//                    System.out.println(money);
//                    System.out.println(desc);
//                    moneyList.add(taskMap);
//                }
//                updateTotal(tot);
//                moneyAdapter.updateData(moneyList);
//
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
////                progressDialog.dismiss();
//            }
//        });

//        moneyList=db.getAllExpenses(userId);
        moneyAdapter = new MyAdapter(this,moneyList, HomePage.this);
        moneyRecyclerView.setAdapter(moneyAdapter);
        SwipeToDeleteAndEditCallback swipeCallback = new SwipeToDeleteAndEditCallback(moneyAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeCallback);
        itemTouchHelper.attachToRecyclerView(moneyRecyclerView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
         public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                total.setText(tota.toString());
//            updateTotal();
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
                     update();
                     return true;
             }
             return false;
         }


                                                                 }

        );
    }
}