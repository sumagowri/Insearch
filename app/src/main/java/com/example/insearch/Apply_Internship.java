package com.example.insearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class Apply_Internship extends AppCompatActivity {
    TextView compNameW,internNameW,emailW,phoneW,locationW,skillW,dateW,durationW,detailW,slotsW;
    String compName,internName,email,phone,location,skill,date,duration,detail,slots;
    Button apply;
    ImageView back;
    String intern_id;
    int stud_id;
    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_internship);
        queue = Volley.newRequestQueue(this);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            intern_id = bundle.getString("intern_id");
            stud_id = bundle.getInt("userid");
        }

        compNameW = findViewById(R.id.s_comp_name);
        internNameW = findViewById(R.id.s_intern_name);
        emailW = findViewById(R.id.s_comp_email);
        phoneW = findViewById(R.id.s_phone_comp);
        skillW = findViewById(R.id.s_intern_skill);
        locationW = findViewById(R.id.s_location);
        dateW = findViewById(R.id.s_date);
        durationW = findViewById(R.id.s_duration);
        detailW = findViewById(R.id.s_description);
        slotsW = findViewById(R.id.s_slots);
        getInternshipInfo(intern_id);

        apply = findViewById(R.id.apply_btn);
        back = findViewById(R.id.back_stud_home);

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apply(stud_id,intern_id);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToHome(stud_id);
            }
        });
    }

    private void goToHome(int id){
        stud_id = id;
        Bundle bundl = new Bundle();
        bundl.putInt("userid",stud_id);
//        Toast.makeText(this, stud_id, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Apply_Internship.this,Student_Home.class);
        intent.putExtras(bundl);
        startActivity(intent);
    }

    private void setData(String compName, String internName, String email, String phone, String location, String skill, String date, String duration, String detail, String slots) {
        compNameW.setText(compName);
        internNameW.setText(internName);
        emailW.setText(email);
        phoneW.setText(phone);
        locationW.setText(location);
        skillW.setText(skill);
        dateW.setText(date);
        durationW.setText(duration);
        detailW.setText(detail);
        slotsW.setText(slots);
    }

    private void apply(int stud_id,String intern_id){
        String url = "https://insearchsystem.000webhostapp.com/php/apply_internship.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject resObj = new JSONObject(response);
                            boolean res = resObj.getBoolean("status");
                            if(res){
                                Toast.makeText(Apply_Internship.this, "Applied successfully", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(Apply_Internship.this, "Already applied", Toast.LENGTH_SHORT).show();
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Apply_Internship.this, "Try again!!", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("stud_id",String.valueOf(stud_id));
                params.put("intern_id",intern_id);
                return  params;
            }
        };
        queue.add(stringRequest);
    }

    private void getInternshipInfo(String id){
        String url = "https://insearchsystem.000webhostapp.com/php/internship_view_apply.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject resObj = new JSONObject(response);
                            boolean status = resObj.getBoolean("status");
                            if(status){
                                compName = resObj.getString("comp_name");
                                internName = resObj.getString("internship_name");
                                email = resObj.getString("comp_email");
                                phone = resObj.getString("comp_phone");
                                location = resObj.getString("comp_address");
                                skill = resObj.getString("skill_name");
                                date = resObj.getString("start_date");
                                duration = resObj.getString("duration");
                                detail = resObj.getString("details");
                                slots = resObj.getString("slots");
                                setData(compName,internName,email,phone,location,skill,date,duration,detail,slots);
                            }else{
                                Toast.makeText(Apply_Internship.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Apply_Internship.this, "Try again!!", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String,String>();
                params.put("intern_id",id);
                return params;
            }
        };
        queue.add(stringRequest);
    }
}