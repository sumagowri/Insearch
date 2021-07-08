package com.example.insearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button studLogin,compLogin,studReg,compReg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        studLogin=findViewById(R.id.studLogin);
        studLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StudentLogin.class);
                startActivity(intent);
            }
        });
        studReg=findViewById(R.id.studReg);
        studReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Stud_reg.class);
                startActivity(intent);
            }
        });
        compLogin=findViewById(R.id.compLogin);
        compLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CompanyLogin.class);
                startActivity(intent);
            }
        });
        compReg=findViewById(R.id.compReg);
        compReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Comp_reg.class);
                startActivity(intent);
            }
        });
    }
}