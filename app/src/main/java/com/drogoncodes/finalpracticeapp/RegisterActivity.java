package com.drogoncodes.finalpracticeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Register");

        DBHelper helper = new DBHelper(this, null, null, 1);
        EditText name, contact, email, password;
        Button btnRegister;
        name = findViewById(R.id.etName);
        contact = findViewById(R.id.etContact);
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etNewPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helper.Register(name.getText().toString(), email.getText().toString(), contact.getText().toString(), password.getText().toString());
                Toast.makeText(getApplicationContext(), "User Registered Successfully!!!", Toast.LENGTH_LONG).show();
                Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}