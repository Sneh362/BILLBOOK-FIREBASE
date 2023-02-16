package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    EditText email;
    EditText password;
    Button login;
    Button toSign;
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
        setContentView(R.layout.activity_login);


        email=findViewById(R.id.login_email);
        password=findViewById(R.id.login_password);
        login=findViewById(R.id.login_button);
        toSign=findViewById(R.id.toSignup);

        toSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MYAPP2.class);
                startActivity(intent);

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Databasehelper db = new Databasehelper(Login.this);
                String Email=email.getText().toString();
                String Password=password.getText().toString();

                if(Email.equals("") && Password.equals("")){
                    Toast.makeText(Login.this, "All Field is Mandatory", Toast.LENGTH_SHORT).show();

                }
                else{
                    if(isValidEmail(Email)){

                            Boolean res1=db.checkEmail(Email);
                            if(res1){
                                Boolean res2=db.checkEmailPassword(Email,Password);
                                if(res2){
                                    email.setText("");
                                    password.setText("");

                                    Toast.makeText(Login.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), HomePage.class);
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(Login.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(Login.this, "No User Found", Toast.LENGTH_SHORT).show();
                            }

                        }

                    else{
                        Toast.makeText(Login.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}