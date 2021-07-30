package com.example.insearch;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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

public class Company_Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    NavigationView navigationView;
    String userid;
    //    Toolbar tool;
    ActionBarDrawerToggle toggle;
    ListView listViewW;
    RequestQueue queue;
    String[] name,skill,duration,slots,intern_id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company__home);
        queue = Volley.newRequestQueue(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle!= null) {
            userid = bundle.getString("compId");
        }

        getInternship(userid);

        listViewW = findViewById(R.id.listView_comp);
        androidx.appcompat.widget.Toolbar tool = findViewById(R.id.tool);
        setSupportActionBar(tool);

        drawer = findViewById(R.id.drawer);

        setNavigationViewListener();
        navigationView = findViewById(R.id.navigation_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, tool, R.string.navigation_open, R.string.navigation_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
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
        if(id==R.id.profile) {
            Bundle bundle = new Bundle();
            bundle.putString("compId",userid);
            Intent intent = new Intent(Company_Home.this,Comp_profile.class);
            intent.putExtras(bundle);
            startActivity(intent);
            return true;
        }

        else if(id==R.id.intern) {
            Bundle bundle = new Bundle();
            bundle.putString("compId",userid);
            Intent intent = new Intent(Company_Home.this,Internship.class);
            intent.putExtras(bundle);
            startActivity(intent);
            return true;
        }
        else if(id==R.id.application) {
            Bundle bundle = new Bundle();
            bundle.putString("compId",userid);
            Intent intent = new Intent(Company_Home.this,View_feedback.class);
            intent.putExtras(bundle);
            startActivity(intent);
            return true;
        }
        else if(id==R.id.logout) {
            Intent intent = new Intent(Company_Home.this, CompanyLogin.class);
            startActivity(intent);
            return true;
        }
        drawer.closeDrawer(GravityCompat.START);return false;
    }

    private void getInternship(String compid){
        String url = "https://insearchsystem.000webhostapp.com/php/comp_view_internship.php";
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
                            List_internship_company adapter=new List_internship_company(Company_Home.this,R.layout.view_internship_company,name,skill,duration,slots,intern_id,userid);
                            listViewW.setAdapter(adapter);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Company_Home.this, "Try again!!", Toast.LENGTH_SHORT).show();
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
}