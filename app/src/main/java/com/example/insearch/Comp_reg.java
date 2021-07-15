package com.example.insearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Comp_reg extends AppCompatActivity {
Button register;
    EditText c_name, reg_id, phone, email, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comp_reg);

//        ListView
       register= findViewById(R.id.register);
        c_name=findViewById(R.id.name);
        reg_id= (EditText) findViewById(R.id.regid);
        phone= findViewById(R.id.phone);
        email=findViewById(R.id.email);
        password=findViewById(R.id.pass);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Company company;
                try {
                     company=new Company(-1,c_name.getText().toString(),reg_id.getText().toString(),Integer.parseInt(phone.getText().toString()),email.getText().toString(),password.getText().toString());
                    Toast.makeText(Comp_reg.this,company.toString(),Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(Comp_reg.this, CompanyLogin.class);
                    //startActivity(intent);
                }
                catch (Exception e){
                    Toast.makeText(Comp_reg.this,"input the contents coreectly",Toast.LENGTH_LONG).show();
                    company=new Company(-1,"error","null",0,"error","null");
                }
                Database database=new Database(Comp_reg.this);
                boolean success = database.addOne(company);
                Toast.makeText(Comp_reg.this,"Success"+success,Toast.LENGTH_LONG).show();
            }
        });
    }



}