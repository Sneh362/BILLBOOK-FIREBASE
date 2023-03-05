package com.example.myapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
public class MyAdapter extends RecyclerView.Adapter<MoneyHolder> {
    private List<HashMap<String, String>> moneyList;
    private Databasehelper dbHelper;
    public Context context;


    TextView total;
    private HomePage homePage;
    public MyAdapter(Context context,List<HashMap<String, String>> moneyList, HomePage homePage) {
        this.moneyList = moneyList;
        this.homePage=homePage;
        dbHelper = new Databasehelper(homePage.getApplicationContext());
        this.context=context;
    }


    public void deleteItem(int position) {

        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setMessage("Are you Sure to delete? ")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // get the id of the data at the position
                        String id = moneyList.get(position).get("id");

                        // delete the data from the database
                        dbHelper.deleteExpenses(id);
                        homePage.updateTotal();
                        // remove the data from the list
                        moneyList.remove(position);
                        notifyItemRemoved(position);

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        notifyDataSetChanged();

                    }
                });
        AlertDialog alert =builder.create();
        alert.show();



        }

    @Override
    public MoneyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new MoneyHolder(view);
    }


    private void openEditDialog(final HashMap<String, String> money) {
        // create the dialog
        final Dialog dialog = new Dialog(homePage);
        dialog.setContentView(R.layout.dialog_edit);

        // get the EditText views from the dialog
        final EditText amountEditText = dialog.findViewById(R.id.edit_amount);
        final EditText descriptionEditText = dialog.findViewById(R.id.edit_description);

        // set the default values for amount and description
        amountEditText.setText(money.get("amount"));
        descriptionEditText.setText(money.get("purpose"));

        // set the click listener for the "Save" button
        Button saveButton = dialog.findViewById(R.id.button_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the new values for amount and description
                String newAmount = amountEditText.getText().toString();
                String newDescription = descriptionEditText.getText().toString();

                dbHelper.editExpenses(money.get("id"),Integer.parseInt(newAmount),newDescription);
                // update the item in the database
//                SQLiteDatabase db = dbHelper.getWritableDatabase();
//                ContentValues values = new ContentValues();
//                values.put("amount", newAmount);
//                values.put("purpose", newDescription);
//                db.update("billbook", values, "_id = ?", new String[] { money.get("id") });
//                db.close();

                // update the data in the adapter and notify the RecyclerView
                money.put("amount", newAmount);
                money.put("purpose", newDescription);
                notifyDataSetChanged();
                homePage.updateTotal();
                dialog.dismiss();
            }
        });

        // set the click listener for the "Cancel" button
        Button cancelButton = dialog.findViewById(R.id.button_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyDataSetChanged();

                dialog.dismiss();
            }
        });
        notifyDataSetChanged();
        // show the dialog
        dialog.show();
    }

    @Override
    public void onBindViewHolder(MoneyHolder holder, int position) {
        holder.bindData(moneyList.get(position));
        final HashMap<String, String> money = moneyList.get(position);

        // bind the data to the ViewHolder
        holder.bindData(money);

        // set the swipe listener for the item
        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            private float startX;
            private boolean isSwiping;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        isSwiping = false;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (event.getX() < startX - 50) {
                            // swiped left
                            isSwiping = true;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (isSwiping) {
                            // swiped left, open edit dialog
                            openEditDialog(money);
                            isSwiping = false;
                        }
                        break;
                }
                return false;
            }

    });}

    @Override
    public int getItemCount() {
        return moneyList.size();
    }


    public void editItem(int position) {
        // get the data from the position
        HashMap<String, String> data = moneyList.get(position);
        System.out.println(data);
        String id = data.get("id");
        String amount = data.get("amount");
        String description = data.get("purpose");
        openEditDialog(data);



    }

//    public void deleteItem(int position) {
//        // get the id of the data at the position
//        String id = moneyList.get(position).get("id");
//
//        // delete the data from the database
//        SQLiteDatabase db = Databasehelper.getWritableDatabase();
//
//        db.delete("billbook", "id=?", new String[]{id});
//
//        // remove the data from the list
//        moneyList.remove(position);
//        notifyItemRemoved(position);
//    }

}
