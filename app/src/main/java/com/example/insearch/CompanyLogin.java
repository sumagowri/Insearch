package com.example.insearch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CompanyLogin extends AppCompatActivity {
    Button Login;
    EditText userW,passW;
    String userid;
    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_login);

        queue= Volley.newRequestQueue(this);
        Login=findViewById(R.id.Login);
        userW = findViewById(R.id.username);
        passW = findViewById(R.id.password);


        Login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                String user = userW.getText().toString();
                String pass = passW.getText().toString();

                validate(user,pass);
                Company_Login cl = new Company_Login(user,pass);
                LoginFolder(cl);
            }
        });
    }
    private void validate(String user,String pass){
        if(user.isEmpty()){
            Toast.makeText(this, "email is empty", Toast.LENGTH_SHORT).show();
        }else if(pass.isEmpty()){
            Toast.makeText(this, "password is empty", Toast.LENGTH_SHORT).show();
        }
    }

    private  void goToHome(String id){
        userid=id;
        Bundle bundle = new Bundle();
        bundle.putString("compId",userid);
        Intent intent = new Intent(CompanyLogin.this,Company_Home.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void LoginFolder(Company_Login cl){
        String url = "https://insearchsystem.000webhostapp.com/php/login_company.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(StudentLogin.this, "response", Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject respObj = new JSONObject(response);
                            boolean res = respObj.getBoolean("status");

                            if (res) {
                                String comp_id = respObj.getString("comp_id");
                                goToHome(comp_id);
                            } else {
                                Toast.makeText(CompanyLogin.this, "No Login Details", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            //Toast.makeText(StudentLogin.this, , Toast.LENGTH_SHORT).show();
                            //e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CompanyLogin.this, "Try again!!!", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("user",cl.getUser());
                params.put("pass",cl.getPass());
                return params;
            }
        };
        queue.add(stringRequest);
    }
}

class Company_Login{
    String user;
    String pass;

    public Company_Login(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}