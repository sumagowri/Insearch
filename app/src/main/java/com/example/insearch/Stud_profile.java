package com.example.insearch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

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

public class Stud_profile extends AppCompatActivity {
    ImageView back;

androidx.appcompat.widget.Toolbar toolbar;
    Button save;
    EditText nameW,collegeW,emailW,phoneW,mark10W,mark12W,skillW,cgpaW;
    int studid;
    RequestQueue queue,q;
    String name,college,email,phone,mark10,mark12,cgpa,skill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stud_profile);
        queue= Volley.newRequestQueue(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle!= null) {
            studid = bundle.getInt("userid");
        }
        fetchProfile(studid);

        save=findViewById(R.id.stud_save);
//        toolbar = findViewById(R.id.toolbar4);
        back=findViewById(R.id.back2);
        nameW = findViewById(R.id.name_comp);
        collegeW = findViewById(R.id.stud_college);
        emailW = findViewById(R.id.email_comp);
        phoneW = findViewById(R.id.phno_comp);
        mark10W = findViewById(R.id.ten);
        mark12W = findViewById(R.id.twelve);
        skillW = findViewById(R.id.stud_skills);
        cgpaW = findViewById(R.id.cgpa);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHome(studid);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check(name,college,email,phone,mark10,mark12,skill,cgpa);
            }
        });
    }

    private void validate(String name, String college, String email, long phone, float mark10, float mark12, String skill,float cgpa){
        if(name.isEmpty()){
            Toast.makeText(this, "Name is empty", Toast.LENGTH_SHORT).show();
        }else if (college.isEmpty()){
            Toast.makeText(this, "College is empty", Toast.LENGTH_SHORT).show();
        }else if (email.isEmpty()){
            Toast.makeText(this, "email is empty", Toast.LENGTH_SHORT).show();
        }else if (phone==0){
            Toast.makeText(this, "enter 10 digit phone number", Toast.LENGTH_SHORT).show();
        }else if (!(mark10 <= 100 && mark10 > 0 )){
            Toast.makeText(this, "Enter correct marks of 10th", Toast.LENGTH_SHORT).show();
        }else if (!(mark12 <= 100 && mark12 > 0)){
            Toast.makeText(this, "Enter correct marks of 12th", Toast.LENGTH_SHORT).show();
        }else if (skill.isEmpty()){
            Toast.makeText(this, "Skill is empty", Toast.LENGTH_SHORT).show();
        }else if (!(cgpa <= 10 && cgpa > 0)){
            Toast.makeText(this, "Enter correct cgpa value", Toast.LENGTH_SHORT).show();
        }
    }

    void setValues(String names, String college,String email, String phone, String mark10,String mark12,String cgpa,String skill){
        nameW.setText(names);
        collegeW.setText(college);
        emailW.setText(email);
        phoneW.setText(phone);
        mark10W.setText(mark10);
        mark12W.setText(mark12);
        skillW.setText(skill);
        cgpaW.setText(cgpa);
    }

    private void check(String name, String college, String email, String phone, String mark10, String mark12, String skill, String cgpa) {
        String s_name = nameW.getText().toString();
        String s_college = collegeW.getText().toString();
        String s_email = emailW.getText().toString();
        String s_skill = skillW.getText().toString();
        long s_phonee = Long.parseLong(phoneW.getText().toString());
        float mark_10 = Float.parseFloat(mark10W.getText().toString());
        float mark_12 = Float.parseFloat(mark12W.getText().toString());
        float s_cgpa =  Float.parseFloat(cgpaW.getText().toString());
        if(s_name.equals(name) && s_college.equals(college) && s_email.equals(email) && s_phonee == Long.parseLong(phone) && mark_10 == Float.parseFloat(mark10) && mark_12 == Float.parseFloat(mark12) && s_skill.equals(skill) && s_cgpa == Float.parseFloat(cgpa)){
            Toast.makeText(Stud_profile.this, "No changes were made!!", Toast.LENGTH_SHORT).show();
        }else{
            validate(s_name,s_college,s_email,s_phonee,mark_10,mark_12,s_skill,s_cgpa);
            StudentEdit se = new StudentEdit(s_name,s_college,s_email,s_phonee,mark_10,mark_12,s_skill,s_cgpa);
            editStudent(se);
        }
    }

    void goToHome(int id){
        studid=id;
        Bundle bundle = new Bundle();
        bundle.putInt("userid",studid);
        Intent intent = new Intent(Stud_profile.this, Student_Home.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
//    To fetch data
    private void fetchProfile(int userid) {
        String url = "https://insearchsystem.000webhostapp.com/php/Stud_profile.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject respObj = new JSONObject(response);
                            boolean res = respObj.getBoolean("status");
                            if(res){
                                name = respObj.getString("stud_name");
                                college = respObj.getString("stud_college");
                                email = respObj.getString("stud_email");
                                 phone = respObj.getString("stud_phone");
                                 mark10 = (respObj.getString("stud_10"));
                                 mark12 = (respObj.getString("stud_12"));
                                 cgpa = (respObj.getString("stud_cgpa"));
                                //String resume = respObj.getString("stud_resume");
                                 skill = respObj.getString("stud_skill");
                                setValues(name,college,email,phone,mark10,mark12,cgpa,skill);
                            }else{
                                Toast.makeText(Stud_profile.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Stud_profile.this, "Try again!!!", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("stud_id", String.valueOf(userid));
                return params;
            }
        };
        queue.add(stringRequest);
    }

//to update value
    private void editStudent(StudentEdit se){
        String url = "https://insearchsystem.000webhostapp.com/php/editStudentProfile.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject respObj = new JSONObject(response);
                            boolean res = respObj.getBoolean("status");
                            if(res){
                                Toast.makeText(Stud_profile.this, "Data updated", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(Stud_profile.this, "try again", Toast.LENGTH_SHORT).show();
                            }
                        }catch (JSONException e){
                            Toast.makeText(Stud_profile.this, "Try again!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Stud_profile.this, "try again!!", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("stud_id",String.valueOf(studid));
                params.put("stud_name",se.getName());
                params.put("stud_college",se.getColg_name());
                params.put("stud_email",se.getEmail());
                params.put("stud_phone", String.valueOf(se.getPhone()));
                params.put("stud_10",String.valueOf(se.getMark10()));
                params.put("stud_12",String.valueOf(se.getMark12()));
                params.put("stud_cgpa",String.valueOf(se.getCgpa()));
//                params.put("stud_resume","abc.pdf");
                params.put("stud_skill",se.getSkill());
                return params;
            }
        };
        queue.add(stringRequest);
    }
}

class StudentEdit{
    String name;
    String colg_name;
    String email;
    long phone;
    float mark10;
    float mark12;
    String skill;
    float cgpa;

    public StudentEdit(String name, String colg_name, String email, long phone, float mark10, float mark12, String skill, float cgpa) {
        this.name = name;
        this.colg_name = colg_name;
        this.email = email;
        this.phone = phone;
        this.mark10 = mark10;
        this.mark12 = mark12;
        this.skill = skill;
        this.cgpa = cgpa;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColg_name() {
        return colg_name;
    }

    public void setColg_name(String colg_name) {
        this.colg_name = colg_name;
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

    public float getMark10() {
        return mark10;
    }

    public void setMark10(float mark10) {
        this.mark10 = mark10;
    }

    public float getMark12() {
        return mark12;
    }

    public void setMark12(float mark12) {
        this.mark12 = mark12;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public float getCgpa() {
        return cgpa;
    }

    public void setCgpa(float cgpa) {
        this.cgpa = cgpa;
    }
}