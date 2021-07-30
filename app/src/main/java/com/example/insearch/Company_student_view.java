package com.example.insearch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Company_student_view extends AppCompatActivity {
    String intern_id,comp_id;
    String[] name,email,phone,cgpa,mark10,mark12,skill;
    RequestQueue queue;
    ListView listViewW;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_student_view);
        queue = Volley.newRequestQueue(this);
        Bundle bundl = getIntent().getExtras();
        if(bundl!=null){
            intern_id = bundl.getString("intern_id");
            comp_id = bundl.getString("comp_id");
        }

        getInterns(intern_id);

        listViewW= findViewById(R.id.listView_stud);
        back = findViewById(R.id.back_comp_stud);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToHome(comp_id);
            }
        });
        // calling the action bar
//        ActionBar actionBar = getSupportActionBar();
//
//        // showing the back button in action bar
//        actionBar.setDisplayHomeAsUpEnabled(true);
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                this.finish();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    private  void goToHome(String id){
        comp_id=id;
        Bundle bundle = new Bundle();
        bundle.putString("compId",comp_id);
        Intent intent = new Intent(Company_student_view.this,Company_Home.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void getInterns(String intern_id){
        String url = "https://insearchsystem.000webhostapp.com/php/view_student_applied_internship.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray resArr = new JSONArray(response);
                            name = new String[resArr.length()];
                            email = new String[resArr.length()];
                            phone = new String[resArr.length()];
                            cgpa = new String[resArr.length()];
                            mark10 = new String[resArr.length()];
                            mark12 = new String[resArr.length()];
                            skill = new String[resArr.length()];
                            for(int i = 0 ;i<resArr.length();i++){
                                JSONObject resObj = resArr.getJSONObject(i);
                                try{
                                    name[i] = resObj.getString("stud_name");
                                    email[i] = resObj.getString("stud_email");
                                    phone[i] = resObj.getString("stud_phone");
                                    cgpa[i] = resObj.getString("stud_cgpa");
                                    mark10[i] = resObj.getString("stud_10");
                                    mark12[i] = resObj.getString("stud_12");
                                    skill[i] = resObj.getString("skill_name");
                                }catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }
                            List_company_student adapter=new List_company_student(Company_student_view.this,R.layout.view_company_student_intern,name,email,phone,cgpa,mark10,mark12,skill);
                            listViewW.setAdapter(adapter);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Company_student_view.this, "Try again!!", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String,String>();
                params.put("intern_id",intern_id);
                return params;
            }
        };
        queue.add(stringRequest);
    }
}