package com.example.insearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Comp_reg extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 99;
    private  static final  int PERMISSION_REQUEST_CODE = 1;
    Button register;
    EditText c_nameW, reg_idW, phoneW, emailW,addressW, passwordW,confirmW;
    RequestQueue queue;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comp_reg);

        queue = Volley.newRequestQueue(this);
//        imageView = findViewById(R.id.comp_icon);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
////                showFileChoose();
//            }
//        });

//        requestStoragePermission();
        register= findViewById(R.id.register);
        c_nameW=findViewById(R.id.name);
        reg_idW= (EditText) findViewById(R.id.regid);
        phoneW= findViewById(R.id.c_phone);
        emailW=findViewById(R.id.email);
        addressW = findViewById(R.id.address);
        passwordW=findViewById(R.id.pass);
        confirmW = findViewById(R.id.confirm);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String regid = reg_idW.getText().toString();
                String c_name = c_nameW.getText().toString();
                String email = emailW.getText().toString();
                long phone = Long.parseLong(phoneW.getText().toString());
                String address = addressW.getText().toString();
                String password = passwordW.getText().toString();
                String confirm = confirmW.getText().toString();

                validate(regid,c_name,email,phone,address,password,confirm);

            }
        });
    }

//    private  void requestStoragePermission(){
//        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
//            return;
//        }
//        if()
//    }

//    private void  showFileChoose(){
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startAcivityForResult(Intent.createChooser(intent,"select Picture"),PICK_IMAGE_REQUEST);
//    }
//
//    @Override
//    protected void onActivityResult(int RC, int RQC, Intent I) {
//        super.onActivityResult(RC, RQC, I);
//        if (RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null) {
//            Uri uri = I.getData();
//            InputStream imagestream = null;
//            try {
//                imagestream = getContentResolver().openInputStream(uri);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//            bitmap = BitmapFactory.decodeStream(imagestream);
//
//            imageView.setImageURI(uri);
//            image = BitMapToString(bitmap);
//        }
//    }
//
//    public  String BitMapToString(Bitmap b){
//
//    }

    private void validate(String regid, String c_name, String email, long phone, String address, String password, String confirm){
        if(regid.isEmpty()){
            Toast.makeText(this, "id is empty", Toast.LENGTH_SHORT).show();
        }else if(c_name.isEmpty()){
            Toast.makeText(this, "name is empty", Toast.LENGTH_SHORT).show();
        }else if(email.isEmpty()){
            Toast.makeText(this, "email is empty", Toast.LENGTH_SHORT).show();
        }else if(phone==0){
            Toast.makeText(this, "phone is empty", Toast.LENGTH_SHORT).show();
        }else if(address.isEmpty()){
            Toast.makeText(this, "address is empty", Toast.LENGTH_SHORT).show();
        }else if(password.isEmpty()){
            Toast.makeText(this, "password is empty", Toast.LENGTH_SHORT).show();
        }else if(confirm.isEmpty()){
            Toast.makeText(this, "password is empty", Toast.LENGTH_SHORT).show();
        }else if(!password.equals(confirm)){
            Toast.makeText(this, "password should be same", Toast.LENGTH_SHORT).show();
        }
        else{
            Company company = new Company(c_name,regid,phone,address,email,password) ;
            uploadRegisterData(company);
        }
    }
    private void goToHome(String uid){
        userid = uid;
        Bundle bundle = new Bundle();
        bundle.putString("compId",userid);
        Intent intent = new Intent(Comp_reg.this, Company_Home.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void uploadRegisterData(Company company){
        String url = "https://insearchsystem.000webhostapp.com/php/company_register.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject respObj = new JSONObject(response);
                            boolean res = respObj.getBoolean("status");
                            if(res){
                                String comp_id = respObj.getString("comp_id");
                                goToHome(comp_id);
                            }else{
                                Toast.makeText(Comp_reg.this, "Already registered", Toast.LENGTH_SHORT).show();
                            }
                        }catch (JSONException e){
                            Toast.makeText(Comp_reg.this, "Try again!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Comp_reg.this, "Try again!!!", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("comp_id",company.getReg_id());
                params.put("comp_name",company.getC_name());
                params.put("comp_email",company.getEmail());
                params.put("comp_phone", String.valueOf(company.getPhone()));
                params.put("comp_address",company.getAddress());
                params.put("comp_password",company.getPassword());
                return params;
            }
        };
        queue.add(stringRequest);
    }
}