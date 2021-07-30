package com.example.insearch;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Student_Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;
    NavigationView navigationView;
    ListView listView;
    ImageView search;
    EditText textSearch;
    int userid;
    RequestQueue queue;
    String[] name,skill,duration,slots,intern_id;
    //Toolbar toolbar;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
        queue = Volley.newRequestQueue(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle!= null) {
            userid = bundle.getInt("userid");
        }

        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.navigation_view);
        listView = findViewById(R.id.view);
        setNavigationViewListener();
        getInternship(userid);

        androidx.appcompat.widget.Toolbar toolbar;
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navopen,R.string.navclose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        search = findViewById(R.id.search);
        textSearch = findViewById(R.id.textSeacrh);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String skillSearch = textSearch.getText().toString();
                searchInternship(skillSearch,userid);
            }
        });
    }
    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
    private void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.m1) {
            Bundle bundle = new Bundle();
            bundle.putInt("userid",userid);
            Intent intent = new Intent(Student_Home.this, Stud_profile.class);
            intent.putExtras(bundle);
            startActivity(intent);
            return true;
        }
        if(id==R.id.m2) {
            Bundle bundle = new Bundle();
            bundle.putInt("userid",userid);
            Intent intent = new Intent(Student_Home.this, Feedback.class);
            intent.putExtras(bundle);
            startActivity(intent);
            return true;
        }
        else if(id==R.id.m3) {

//            Toast.makeText(Company_Home.this,"DprofileS",Toast.LENGTH_LONG).show();
//            return true;
            Bundle bundle = new Bundle();
            bundle.putInt("userid",userid);
            Intent intent = new Intent(Student_Home.this, StudentLogin.class);
            intent.putExtras(bundle);
            startActivity(intent);
            return true;
        }drawerLayout.closeDrawer(GravityCompat.START);return false;
    }
    private void getInternship(int stud_id){
        String url = "https://insearchsystem.000webhostapp.com/php/Student_skill_recommended.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray resArr = new JSONArray(response);
                            name = new String[resArr.length()];
                            skill = new String[resArr.length()];
                            duration = new String[resArr.length()];
                            slots = new String[resArr.length()];
                            intern_id = new String[resArr.length()];
                            for(int i = 0 ;i< resArr.length();i++){
                                JSONObject resObj = resArr.getJSONObject(i);
                                try{
                                    name[i] = resObj.getString("internship_name");
                                    skill[i] = resObj.getString("skill_name");
                                    duration[i] = resObj.getString("duration");
                                    slots[i] = resObj.getString("slots");
                                    intern_id[i] = resObj.getString("internship_id");
                                }catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }
                            List_internship_student adapter=new List_internship_student(Student_Home.this,R.layout.view_internship_skill,name,skill,duration,slots,intern_id,stud_id);
                            listView.setAdapter(adapter);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Student_Home.this, "Try again!!", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("stud_id",String.valueOf(stud_id));
                return params;
            }
        };
        queue.add(stringRequest);
    }

    private void searchInternship(String skillSearch,int stud_id){
        String url = "https://insearchsystem.000webhostapp.com/php/search_internship.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray resArr = new JSONArray(response);
                            name = new String[resArr.length()];
                            skill = new String[resArr.length()];
                            duration = new String[resArr.length()];
                            slots = new String[resArr.length()];
                            intern_id = new String[resArr.length()];
                            for(int i = 0 ;i< resArr.length();i++){
                                JSONObject resObj = resArr.getJSONObject(i);
                                try{
                                    name[i] = resObj.getString("internship_name");
                                    skill[i] = resObj.getString("skill_name");
                                    duration[i] = resObj.getString("duration");
                                    slots[i] = resObj.getString("slots");
                                    intern_id[i] = resObj.getString("internship_id");
                                }catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }
                            List_internship_student adapter=new List_internship_student(Student_Home.this,R.layout.view_internship_skill,name,skill,duration,slots,intern_id,stud_id);
                            listView.setAdapter(adapter);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Student_Home.this, "Try again!!", Toast.LENGTH_SHORT).show();
            }
        }){
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("skill_name",skillSearch);
                return  params;
            }
        };
        queue.add(stringRequest);
    }
}