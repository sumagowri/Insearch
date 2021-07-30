package com.example.insearch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
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
import java.util.List;
import java.util.Map;

public class Feedback extends AppCompatActivity {
    RatingBar rating;
    Button Submit;
    ImageView back;
    Spinner dropdown;
    EditText reviewW;
    int userid;
    RequestQueue queue;
    String[] ids;
    String[] names;
    String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        queue = Volley.newRequestQueue(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle!= null) {
            userid = bundle.getInt("userid");
        }
        fetchCompName(userid);

        rating=findViewById(R.id.ratingBar);
        Submit=findViewById(R.id.submit);
        back=findViewById(R.id.back_feed);
        dropdown = findViewById(R.id.spinner);
        reviewW = findViewById(R.id.review);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                name = dropdown.getItemAtPosition(dropdown.getSelectedItemPosition()).toString();
                //Toast.makeText(getApplicationContext(),country,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rate=Float.parseFloat(String.valueOf(rating.getRating()));
                String review = reviewW.getText().toString();
                int i;
                boolean res = validate(review,rate,name);
                for(i=0 ; i<names.length ; i++){
                    if(names[i].equals(name)){
                        break;
                    }
                }
                if(res){
                    Review r = new Review(name,review,rate);
                    uploadReview(r,ids[i]);
                }
            }
        });

        back.setOnClickListener(v -> {
            gotoHome();
        });
    }

    private void gotoHome(){
        Bundle b = new Bundle();
        b.putInt("userid",userid);
        Intent intent = new Intent(Feedback.this, Student_Home.class);
        intent.putExtras(b);
        startActivity(intent);
    }

    boolean validate(String review,float rate,String comp_name){
        if(review.isEmpty()){
            Toast.makeText(this, "Review field is empty", Toast.LENGTH_SHORT).show();
            return false;
        }else if(rate>5 && rate<0){
            Toast.makeText(this, "Rating should be between 0 to 5", Toast.LENGTH_SHORT).show();
            return false;
        }else if(comp_name.isEmpty()){
            Toast.makeText(this, "Either you didn't apply for any internship or select a Company name", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private  void fetchCompName(int stud_id){
        String url = "https://insearchsystem.000webhostapp.com/php/student_internship_compname.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray resArr = new JSONArray(response);
                            ids = new String[resArr.length()];
                            names = new String[resArr.length()];
                            for(int i = 0 ;i< resArr.length();i++){
                                JSONObject resObj = resArr.getJSONObject(i);
                                try{
                                    ids[i] = resObj.getString("comp_id");
                                    names[i] = resObj.getString("comp_name");
                                }catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }
                            dropdown.setAdapter(new ArrayAdapter<String>(Feedback.this, android.R.layout.simple_spinner_dropdown_item, names));
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Feedback.this, "Try again!!", Toast.LENGTH_SHORT).show();
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

    private void uploadReview(Review r,String comp_id){
        String url = "https://insearchsystem.000webhostapp.com/php/student_feedback.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject resObj = new JSONObject(response);
                            boolean res = resObj.getBoolean("status");
                            if(res){
                                Toast.makeText(Feedback.this, "Inserted successfully", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(Feedback.this, "You have already given a feedback", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Feedback.this, "Try again!!", Toast.LENGTH_SHORT).show();
            }
        }){
          @Override
          protected Map<String,String> getParams(){
              Map<String,String> params = new HashMap<String,String>();
              params.put("stud_id",String.valueOf(userid));
              params.put("comp_id",comp_id);
              params.put("review",r.getReview());
              params.put("review_point",String.valueOf(r.getRate()));
              return params;
          }
        };
        queue.add(stringRequest);
    }
}


class Review{
    String comp_name;
    String review;
    float rate;


    public Review(String comp_name, String review, float rate) {
        this.comp_name = comp_name;
        this.review = review;
        this.rate = rate;
    }

    public String getComp_name() {
        return comp_name;
    }

    public void setComp_name(String comp_name) {
        this.comp_name = comp_name;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
