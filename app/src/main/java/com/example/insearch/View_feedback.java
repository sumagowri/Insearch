package com.example.insearch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class View_feedback extends AppCompatActivity {

    ListView listView;
    ImageView back;
    String compid;
    String[] name,review,rate;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_feedback);
        queue = Volley.newRequestQueue(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle!= null) {
            compid = bundle.getString("compId");
        }
        getFeedback(compid);
        listView=(ListView) findViewById(R.id.listview);

        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToHome(compid);
            }
        });
    }

    private void goToHome(String id){
        compid = id;
        Bundle bundl = new Bundle();
        bundl.putString("compId",compid);
        Intent intent = new Intent(View_feedback.this,Company_Home.class);
        intent.putExtras(bundl);
        startActivity(intent);
    }

    private void getFeedback(String compid){
        String url = "https://insearchsystem.000webhostapp.com/php/View_feedback.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray resArr = new JSONArray(response);
                            name = new String[resArr.length()];
                            review = new String[resArr.length()];
                            rate = new String[resArr.length()];
                            for(int i = 0 ;i< resArr.length();i++){
                                JSONObject resObj = resArr.getJSONObject(i);
                                try{
                                    name[i] = resObj.getString("stud_name");
                                    review[i] = resObj.getString("review");
                                    rate[i] = resObj.getString("review_point");
                                }catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }
                            List_feed adapter=new List_feed(View_feedback.this,R.layout.view,name,review,rate);
                            listView.setAdapter(adapter);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(View_feedback.this, "Try again!!", Toast.LENGTH_SHORT).show();
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

//class View_feed{
//    private String name;
//    private String feedback;
//    private String rating;
//
//    public View_feed(String name, String feedback, String rating) {
//        this.name = name;
//        this.feedback = feedback;
//        this.rating = rating;
//
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getFeedback() {
//        return feedback;
//    }
//
//    public void setFeedback(String feedback) {
//        this.feedback = feedback;
//    }
//
//    public String getRating() {
//        return rating;
//    }
//
//    public void setRating(String rating) {
//        this.rating = rating;
//    }
//
//}