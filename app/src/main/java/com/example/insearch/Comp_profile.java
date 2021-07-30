package com.example.insearch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class Comp_profile extends AppCompatActivity {
    ImageView back;
    Button save;
    EditText nameW,emailW,phnoW,addressW;
    String compid;
    String name,email,phno,address;
    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comp_profile);
        queue = Volley.newRequestQueue(this);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            compid = bundle.getString("compId");
        }
        fetchProfile(compid);

        back = findViewById(R.id.back1);
        save=findViewById(R.id.comp_save);
        nameW = findViewById(R.id.name_comp);
        emailW = findViewById(R.id.email_comp);
        phnoW = findViewById(R.id.phno_comp);
        addressW = findViewById(R.id.address_comp);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHome(compid);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check(name,email,phno,address);
            }

        });
    }

    boolean validate(String name, String email, Long phno,String address){
        if(name.isEmpty()){
            Toast.makeText(this, "Name is empty", Toast.LENGTH_SHORT).show();
            return false;
        }else if(email.isEmpty()){
            Toast.makeText(this, "Email is empty", Toast.LENGTH_SHORT).show();
            return false;
        }else if(!(phno.toString().length() == 10)){
            Toast.makeText(this, "Phone no. should be 10 digits", Toast.LENGTH_SHORT).show();
            return false;
        }else if(address.isEmpty()){
            Toast.makeText(this, "Address is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    void check(String name,String email,String phno,String address){
        String c_name = nameW.getText().toString();
        String c_email = emailW.getText().toString();
        Long c_phno = Long.parseLong(phnoW.getText().toString());
        String c_address = addressW.getText().toString();
        if(c_name.equals(name) && c_email.equals(email) && Long.parseLong(phno) == c_phno && c_address.equals(address)){
            Toast.makeText(this, "No changes were made.", Toast.LENGTH_SHORT).show();
        }else{
            boolean res = validate(c_name,c_email,c_phno,c_address);
            if(res){
                EditCompany editCompany = new EditCompany(c_name,c_email,c_phno,c_address);
                edit_company(editCompany);
            }
        }
    }

    private void goToHome(String id){
        compid = id;
        Bundle b = new Bundle();
        b.putString("compId",compid);
        Intent intent = new Intent(Comp_profile.this, Company_Home.class);
        intent.putExtras(b);
        startActivity(intent);
    }

    void setValues(String name,String email,String phno,String address){
        nameW.setText(name);
        emailW.setText(email);
        phnoW.setText(phno);
        addressW.setText(address);
    }

    private void fetchProfile(String compid) {
        String url = "https://insearchsystem.000webhostapp.com/php/Comp_profile.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject resObj = new JSONObject(response);
                            boolean res = resObj.getBoolean("status");
                            if(res){
                                name = resObj.getString("comp_name");
                                email = resObj.getString("comp_email");
                                phno = resObj.getString("comp_phone");
                                address = resObj.getString("comp_address");
                                setValues(name,email,phno,address);
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Comp_profile.this, "Try again!!", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("comp_id",compid);
                    return params;
                }
        };
        queue.add(stringRequest);
    }

    private void edit_company(EditCompany comp){
        String url = "https://insearchsystem.000webhostapp.com/php/editCompProfile.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject resObj = new JSONObject(response);
                            Boolean res = resObj.getBoolean("status");
                            if(res){
                                Toast.makeText(Comp_profile.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(Comp_profile.this, "try again", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Comp_profile.this, "Try again!!", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String,String>();
                params.put("comp_id",compid);
                params.put("comp_name",comp.getName());
                params.put("comp_email",comp.getEmail());
                params.put("comp_phone",String.valueOf(comp.getPhone()));
                params.put("comp_address",comp.getAddress());
                return params;
            }
        };
        queue.add(stringRequest);
    }
}

class EditCompany{
    String name;
    String email;
    long phone;
    String address;

    public EditCompany(String name, String email, long phone, String address) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}