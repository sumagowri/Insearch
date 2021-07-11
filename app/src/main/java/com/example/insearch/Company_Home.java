package com.example.insearch;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

public class Company_Home extends AppCompatActivity {
    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar tool;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company__home);

        drawer=findViewById(R.id.drawer);
        navigationView=findViewById(R.id.navigation_view);




        androidx.appcompat.widget.Toolbar tool = findViewById(R.id.tool);
        setSupportActionBar(tool);



        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,tool,R.string.navigation_open,R.string.navigation_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        }
    }
