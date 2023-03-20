package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    EditText email;
    EditText password;
    Button login;

    TextView toSign;
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
        setContentView(R.layout.activity_login);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MySharedPref",MODE_PRIVATE);

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
                mAuth=FirebaseAuth.getInstance();
                if(Email.equals("") && Password.equals("")){
                    Toast.makeText(Login.this, "All Field is Mandatory", Toast.LENGTH_SHORT).show();

                }
                else{
                    if(isValidEmail(Email)){

                        mAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    FirebaseUser currentUser = mAuth.getCurrentUser();
                                    String userId = currentUser.getUid();
                                    System.out.println(userId);
                                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                    myEdit.putString("userId",userId);
                                    myEdit.commit();
                                    Intent intent = new Intent(getApplicationContext(), HomePage.class);
                                    startActivity(intent);
                                    Toast.makeText(Login.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(Login.this, "Not Successfully", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
//                            Boolean res1=db.checkEmail(Email);
//                            if(res1){
//                                String res2=db.checkEmailPassword(Email,Password);
//                                if(res2!=null){
//                                    email.setText("");
//                                    password.setText("");
//                                    System.out.println("responed: "+res2);
//                                    SharedPreferences.Editor myEdit=sharedPreferences.edit();
//                                    myEdit.putString("userId",res2);
//                                    myEdit.commit();
//
//                                    Toast.makeText(Login.this, "Login Successfully", Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(getApplicationContext(), HomePage.class);
//                                    startActivity(intent);
//                                }
//                                else{
//                                    Toast.makeText(Login.this, "Invalid Password", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                            else{
//                                Toast.makeText(Login.this, "No User Found", Toast.LENGTH_SHORT).show();
//                            }

                        }

                    else{
                        Toast.makeText(Login.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}