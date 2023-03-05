package com.example.myapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Databasehelper extends SQLiteOpenHelper {

    public static final String databaseName ="Signup.db";

    public Databasehelper(@Nullable Context context) {
        super(context, databaseName, null, 1);
    } ;

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        MyDatabase.execSQL("create Table allusers(id int primary key ,email TEXT , password TEXT)");
        MyDatabase.execSQL("create Table billbook(id int primary key ,userId int ,purpose TEXT ,money int)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists allusers");
        db.execSQL("drop Table if exists billbook");
        onCreate(db);

    }

    public Boolean insertData(String email, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        contentValues.put("id", ts);
        contentValues.put("email", email);
        contentValues.put("password", password);
        long result = MyDatabase.insert("allusers", null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean checkEmail(String email) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from allusers where email = ?", new String[]{email});

        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }

    }

    public List<HashMap<String, String>> getAllExpenses(String user) {
        List<HashMap<String, String>> moneyList = new ArrayList<>();
        String TABLE_NAME="billbook";
        String COLUMN_NAME="userId";
        String userId=user;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + " = '" + userId + "'";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String purpose = cursor.getString(2);
                Integer Amount = cursor.getInt(3);

                HashMap<String, String> moneyMap = new HashMap<>();
                moneyMap.put("id", String.valueOf(id));
                moneyMap.put("purpose", purpose);
                moneyMap.put("amount", Amount.toString());
                moneyList.add(moneyMap);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return moneyList;
    }
    public String checkEmailPassword(String email, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();

        Cursor cursor = MyDatabase.rawQuery("Select * from allusers where email = ? and password = ?", new String[]{email, password});

        if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {


                String id= cursor.getString(0);
                System.out.println(id);
                cursor.close();
                return id;

        }

        return null;
    }
    public Integer getTotal(String UserId){

        SQLiteDatabase db=this.getReadableDatabase();

        String TABLE_NAME="billbook";
        String COLUMN_NAME1="userId";


        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME1 + " = '" + UserId + "'";
        Cursor cursor=db.rawQuery(query, null);
        Integer mon=0;
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(3);
                mon=mon+id;
            } while (cursor.moveToNext());
        }

        cursor.close();

        System.out.println("total"+mon.toString());
        return mon;
    };
    public boolean addMoney(String pur,Integer money,String user_id){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        contentValues.put("id", ts);
        contentValues.put("userId",user_id);
        contentValues.put("purpose",pur);
        contentValues.put("money",money);
        long result = db.insert("billbook", null, contentValues);
        if(result==-1){
            return false;
        }
        else {
            return true;
        }

    }

    public Nullable deleteExpenses(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("billbook", "id=?", new String[]{id});
        return null;
    }

    public Nullable editExpenses(String id,Integer newAmount,String newDescription){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("money ", newAmount);
        values.put("purpose", newDescription);
        db.update("billbook", values, "id = ?", new String[] { id });
        db.close();

        return null;
    }

}
