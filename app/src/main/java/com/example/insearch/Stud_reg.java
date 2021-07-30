package com.example.insearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Stud_reg extends AppCompatActivity {
    Button register;
    EditText nameW,collegeW,emailW,mark10W,mark12W,skillW,cgpaW,phoneW,passW,confirmW;
    int user_id;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stud_reg);

        queue = Volley.newRequestQueue(this);

        register=findViewById(R.id.register);
        nameW = findViewById(R.id.name);
        collegeW = findViewById(R.id.s_college);
        emailW = findViewById(R.id.email);
        phoneW = findViewById(R.id.phone);
        passW = findViewById(R.id.pass);
        confirmW = findViewById(R.id.confirm_pass);
        mark10W = findViewById(R.id.ten_mark);
        mark12W = findViewById(R.id.twelve_mark);
        skillW = findViewById(R.id.stud_skill);
        cgpaW = findViewById(R.id.cgpa1);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameW.getText().toString();
                String college = collegeW.getText().toString();
                String email = emailW.getText().toString();
                long phone = Long.parseLong(phoneW.getText().toString());
                String pass = passW.getText().toString();
                String confirm = confirmW.getText().toString();
                float mark10 = Float.parseFloat(mark10W.getText().toString());
                float mark12 = Float.parseFloat(mark12W.getText().toString());
                String skill = skillW.getText().toString();
                float cgpa = Float.parseFloat(cgpaW.getText().toString());


                boolean res = validate(name,college,email,phone,pass,confirm,mark10,mark12,skill,cgpa);
                if(res){
                    Student s = new Student(name,college,email,phone,pass,mark10,mark12,skill,cgpa);
                    uploadRegisterData(s);
                }
            }
        });
    }
    
    private boolean validate(String name, String college, String email, long phone, String pass, String confirm, float mark10, float mark12, String skill,float cgpa){
        if(name.isEmpty()){
            Toast.makeText(this, "Name is empty", Toast.LENGTH_SHORT).show();
            return false;
        }else if (college.isEmpty()){
            Toast.makeText(this, "College is empty", Toast.LENGTH_SHORT).show();
            return false;
        }else if (email.isEmpty()){
            Toast.makeText(this, "email is empty", Toast.LENGTH_SHORT).show();
            return false;
        }else if (phone == 0){
            Toast.makeText(this, "enter 10 digit phone number", Toast.LENGTH_SHORT).show();
            return false;
        }else if (mark10 > 100 || mark10 < 0 ){
            Toast.makeText(this, "Enter correct value", Toast.LENGTH_SHORT).show();
            return false;
        }else if (mark12 > 100 || mark12 < 0){
            Toast.makeText(this, "Enter correct value", Toast.LENGTH_SHORT).show();
            return false;
        }else if (skill.isEmpty()){
            Toast.makeText(this, "Skill is empty", Toast.LENGTH_SHORT).show();
            return false;
        }else if (cgpa > 10 || cgpa < 0){
            Toast.makeText(this, "Enter correct value", Toast.LENGTH_SHORT).show();
            return false;
        }else if (pass.isEmpty()){
            Toast.makeText(this, "password is empty", Toast.LENGTH_SHORT).show();
            return false;
        }else if (confirm.isEmpty()){
            Toast.makeText(this, "confirm password is empty", Toast.LENGTH_SHORT).show();
            return false;
        }else if(!pass.equals(confirm)){
            Toast.makeText(this, "password needs to be same", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    
    private void goToHome(int stud_id){
        user_id=stud_id;
        Bundle bundle = new Bundle();
        bundle.putInt("userid",user_id);
        Intent intent = new Intent(Stud_reg.this, Student_Home.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void uploadRegisterData(Student student){
        String url = "https://insearchsystem.000webhostapp.com/php/student_register.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject respObj = new JSONObject(response);
                            boolean res = respObj.getBoolean("status");
                            if(res){
                                int stud_id = respObj.getInt("stud_id");
                                goToHome(stud_id);
                            }else{
                                Toast.makeText(Stud_reg.this, "Already registered", Toast.LENGTH_SHORT).show();
                            }
                        }catch (JSONException e){
                            Toast.makeText(Stud_reg.this, "Try again!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Stud_reg.this, "Try again!!!", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("stud_name",student.getName());
                params.put("stud_college",student.getColg_name());
                params.put("stud_email",student.getEmail());
                params.put("stud_phone", String.valueOf(student.getPhone()));
                params.put("stud_password",student.getPassword());
                params.put("stud_10",String.valueOf(student.getMark10()));
                params.put("stud_12",String.valueOf(student.getMark12()));
                params.put("stud_cgpa",String.valueOf(student.getCgpa()));
                params.put("stud_resume","abc.pdf");
                params.put("stud_skill",student.getSkill());
                return params;
            }
        };
        queue.add(stringRequest);
    }
}

class Student{
    String name;
    String colg_name;
    String email;
    long phone;
    String password;
    float mark10;
    float mark12;
    String skill;
    float cgpa;

    public Student(String name, String colg_name, String email, long phone, String password,float mark10, float mark12, String skill,float cgpa) {
        this.name = name;
        this.colg_name = colg_name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.mark10 = mark10;
        this.mark12 = mark12;
        this.skill = skill;
        this.cgpa = cgpa;
    }

    public String getName() {
        return name;
    }

    public String getColg_name() {
        return colg_name;
    }

    public String getEmail() {
        return email;
    }

    public long getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColg_name(String colg_name) {
        this.colg_name = colg_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
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