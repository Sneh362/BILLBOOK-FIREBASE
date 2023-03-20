package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MYAPP2 extends AppCompatActivity {
    EditText username;
    EditText password;
    EditText conPass;
    Button sign;
    TextView toLogin;
    private FirebaseAuth mAuth;
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    @Override
    public void onBackPressed() {
        // Do nothing
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myapp2);
        username=(EditText) findViewById(R.id.signup_email);
        password=(EditText) findViewById(R.id.signup_password);
        conPass=(EditText) findViewById(R.id.signup_conpass);
        sign=(Button) findViewById(R.id.signup_button);
        toLogin= (TextView) findViewById(R.id.toLogin);


        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Databasehelper db = new Databasehelper(MYAPP2.this);
                String Email=username.getText().toString();

                String Password=password.getText().toString();
                String ConPassword=conPass.getText().toString();
                if(Email.equals("") && Password.equals("")&& ConPassword.equals("")){
                    Toast.makeText(MYAPP2.this, "All Field is Mandatory", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(isValidEmail(Email)){
                        if (Password.equals(ConPassword)){
                            mAuth=FirebaseAuth.getInstance();
                            mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        username.setText("");
                                        password.setText("");
                                        conPass.setText("");
                                        Toast.makeText(MYAPP2.this, "User Created in FIreAuth Successfully", Toast.LENGTH_SHORT).show();

                                    }
                                    else{
                                        Toast.makeText(MYAPP2.this, "Something Went Wrong5dtx", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
//                            boolean res1=db.checkEmail(Email);
//                            if(res1){
//                                Toast.makeText(MYAPP2.this, "User Already Exists", Toast.LENGTH_SHORT).show();
//
//                            }
//                            else {
//                                boolean res = db.insertData(Email, Password);
//                                if (res) {
//                                    username.setText("");
//                                    password.setText("");
//                                    conPass.setText("");
//                                    mAuth=FirebaseAuth.getInstance();
//                                    mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<AuthResult> task) {
//                                            if(task.isSuccessful()){
//                                                Toast.makeText(MYAPP2.this, "User Created in FIreAuth Successfully", Toast.LENGTH_SHORT).show();
//
//                                            }
//                                            else{
//                                                Toast.makeText(MYAPP2.this, "Something Went Wrong5dtx", Toast.LENGTH_SHORT).show();
//
//                                            }
//                                        }
//                                    });
//                                    Toast.makeText(MYAPP2.this, "User Created Successfully", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    Toast.makeText(MYAPP2.this, "Unable to Create User", Toast.LENGTH_SHORT).show();
//                                }
//                            }
                        }
                        else{
                            Toast.makeText(MYAPP2.this, "Confirm Passowrd not match", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(MYAPP2.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



    }
}