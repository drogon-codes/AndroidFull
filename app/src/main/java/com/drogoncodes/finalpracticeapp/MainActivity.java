package com.drogoncodes.finalpracticeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Login");
        DBHelper db = new DBHelper(this, null,null, 1);
        TextView tv;
        EditText userName, password;
        Button btnLogin;
        tv = findViewById(R.id.tvRegister);
        userName = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

        SharedPreferences pref = getSharedPreferences("Login", MODE_PRIVATE);
        if(pref.getBoolean("isLogin", true)){
            Toast.makeText(getApplicationContext(), "User Already Logged In", Toast.LENGTH_LONG).show();
            Intent i = new Intent(MainActivity.this, ViewPatient.class);
//            finish();
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean checkLogin = db.Login(userName.getText().toString(), password.getText().toString());

                if(checkLogin){
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("isLogin", true);
                    editor.apply();
                    Toast.makeText(getApplicationContext(),"Login Successful!!!", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(MainActivity.this, ViewPatient.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Login Failed!!!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}