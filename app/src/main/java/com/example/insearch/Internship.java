package com.example.insearch;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Internship extends AppCompatActivity {
    Button add;
    ImageView back;
    EditText internship_nameW,intern_skillW,internship_dateW,internship_durationW,internship_detailW,internship_slotW;
    String  userid;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internship);
        queue = Volley.newRequestQueue(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle!= null) {
            userid = bundle.getString("compId");
        }

        add=findViewById(R.id.add);
        back=findViewById(R.id.back_intern);
        internship_nameW = findViewById(R.id.Internship_name);
        intern_skillW = findViewById(R.id.skills);
        internship_dateW = findViewById(R.id.start_date);
        internship_durationW = findViewById(R.id.duration);
        internship_detailW = findViewById(R.id.internship_details);
        internship_slotW = findViewById(R.id.slots);

        add.setOnClickListener(v -> {
            String intern_name =internship_nameW.getText().toString();
            String skill = intern_skillW.getText().toString();
            String date = internship_dateW.getText().toString();
            String details = internship_detailW.getText().toString();
//            Date last_date_date = null;
//            try {
//                last_date_date = new SimpleDateFormat("yyyy-MM-dd").parse("date");
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            String intern_date = new SimpleDateFormat("yyyy-MM-dd").format(last_date_date);
            int internship_duration = Integer.parseInt(internship_durationW.getText().toString());
            int internship_slots = Integer.parseInt(internship_slotW.getText().toString());
            validate(intern_name,skill,date,internship_duration,details,internship_slots);
            Add_Internship add_internship = new Add_Internship(intern_name,skill,date,internship_duration,details,internship_slots);
            addInternship(add_internship);
        });

        back.setOnClickListener(v -> {
            Bundle b = new Bundle();
            b.putString("compId",userid);
            Intent intent = new Intent(Internship.this, Company_Home.class);
            intent.putExtras(b);
            startActivity(intent);
        });
    }

    private void validate(String intern_name, String skill, String date, int internship_duration, String details, int internship_slots) {
        if(intern_name.isEmpty()){
            Toast.makeText(this, "Name field is empty", Toast.LENGTH_SHORT).show();
        }else if(skill.isEmpty()){
            Toast.makeText(this, "Skill field is empty", Toast.LENGTH_SHORT).show();
        }else if(date.isEmpty()){
            Toast.makeText(this, "Date cannot be empty", Toast.LENGTH_SHORT).show();
        }else if(internship_duration == 0){
            Toast.makeText(this, "Duration needs to be atleast more than 0", Toast.LENGTH_SHORT).show();
        }else if(details.isEmpty()){
            Toast.makeText(this, "Details is empty", Toast.LENGTH_SHORT).show();
        }else if(internship_slots == 0){
            Toast.makeText(this, "Slots should be more that 0", Toast.LENGTH_SHORT).show();
        }
    }

    private void  goToHome(String id ,int intern_id){
        userid=id;
        Bundle b = new Bundle();
        b.putString("compId",userid);
        b.putInt("intern_id",intern_id);
        Toast.makeText(Internship.this,"Internship added successfully",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Internship.this, Company_Home.class);
        intent.putExtras(b);
        startActivity(intent);
    }

    private void addInternship(Add_Internship add_internship){
        String url = "https://insearchsystem.000webhostapp.com/php/internship_details.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject resObj = new JSONObject(response);
                            Boolean res = resObj.getBoolean("status");
                            if(res){
                                int intern_id = resObj.getInt("intern_id");
                                Toast.makeText(Internship.this, "Added successfully", Toast.LENGTH_SHORT).show();
                                goToHome(userid,intern_id);
                            }else{
                                Toast.makeText(Internship.this, "Try again", Toast.LENGTH_SHORT).show();
                            }
                        }catch (JSONException e){
                            Toast.makeText(Internship.this, "Exception", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Internship.this, "Try again!!", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("intern_name",add_internship.getIntern_name());
                params.put("startdate",add_internship.getDate());
                params.put("duration",String.valueOf(add_internship.getInternship_duration()));
                params.put("details",add_internship.getDetails());
                params.put("slots",String.valueOf(add_internship.getInternship_slots()));
                params.put("comp_id",userid);
                params.put("skill",add_internship.getSkill());
                return params;
            }
        };
        queue.add(stringRequest);
    }
}

class Add_Internship{
    String intern_name;
    String skill;
    String date;
    int internship_duration;
    String details;
    int internship_slots;

    public Add_Internship(String intern_name, String skill, String date, int internship_duration, String details, int internship_slots) {
        this.intern_name = intern_name;
        this.skill = skill;
        this.date = date;
        this.internship_duration = internship_duration;
        this.details = details;
        this.internship_slots = internship_slots;
    }

    public String getIntern_name() {
        return intern_name;
    }

    public void setIntern_name(String intern_name) {
        this.intern_name = intern_name;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getInternship_duration() {
        return internship_duration;
    }

    public void setInternship_duration(int internship_duration) {
        this.internship_duration = internship_duration;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getInternship_slots() {
        return internship_slots;
    }

    public void setInternship_slots(int internship_slots) {
        this.internship_slots = internship_slots;
    }
}