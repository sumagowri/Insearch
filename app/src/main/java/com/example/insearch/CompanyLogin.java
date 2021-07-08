package com.example.insearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CompanyLogin extends AppCompatActivity {
Button Login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_login);
        Login=findViewById(R.id.Login);
        Login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(CompanyLogin.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}