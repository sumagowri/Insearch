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

public class StudentLogin extends AppCompatActivity {
Button login;
EditText usernameW,passwordW;
int studid;
RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        queue= Volley.newRequestQueue(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        login=findViewById(R.id.Login);
        usernameW=findViewById(R.id.username);
        passwordW = findViewById(R.id.password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameW.getText().toString();
                String password = passwordW.getText().toString();

                validate(username,password);
                Student_Login s = new Student_Login(username,password);
                LoginFolder(s);
            }
        });
    }

    private void validate(String username,String password){
        if(username.isEmpty()){
            Toast.makeText(this, "email can't be empty", Toast.LENGTH_SHORT).show();
        }else if(password.isEmpty()){
            Toast.makeText(this, "password can't be empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void goToHome(int stud_id) {
        studid=stud_id;
        Bundle bundle = new Bundle();
        bundle.putInt("userid",studid);
        Intent i = new Intent(StudentLogin.this,Student_Home.class);
        i.putExtras(bundle);
        startActivity(i);
    }

    private void LoginFolder(Student_Login s){
        String url = "https://insearchsystem.000webhostapp.com/php/login_student.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(StudentLogin.this, "response", Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject respObj = new JSONObject(response);
                            boolean res = respObj.getBoolean("status");

                            if (res) {
                                int stud_id = respObj.getInt("stud_id");
                                goToHome(stud_id);
                            } else {
                                Toast.makeText(StudentLogin.this, "No Login Details", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(StudentLogin.this, "Try again!!!", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("user",s.getUser());
                params.put("pass",s.getPass());
                return params;
            }
        };
        queue.add(stringRequest);
    }
}

class  Student_Login{
    String user;
    String pass;

    public Student_Login(String user, String pass) {
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