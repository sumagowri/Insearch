package com.example.insearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class List_company_student extends ArrayAdapter<String> {
    String[] name;
    String[] email;
    String[] phone;
    String[] cgpa;
    String[] mark10;
    String[] mark12;
    String[] skill;
    private int mViewResourceId;
    private LayoutInflater mInflator;

    public List_company_student(Context context, int textViewResourceId, String[] name, String[] email, String[] phone, String[] cgpa, String[] mark10, String[] mark12, String[] skill) {
        super(context, textViewResourceId,name);
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.cgpa = cgpa;
        this.mark10 = mark10;
        this.mark12 = mark12;
        this.skill = skill;

        mInflator=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId=textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=mInflator.inflate(mViewResourceId,null);

//        View_feed user=users.get(position);
        if(name!=null){
            TextView sname= convertView.findViewById(R.id.stud_intern_name);
            TextView semail = convertView.findViewById(R.id.stud_intern_email);
            TextView sphone = convertView.findViewById(R.id.stud_intern_phone);
            TextView scgpa = convertView.findViewById(R.id.stud_intern_cgpa);
            TextView s10 = convertView.findViewById(R.id.stud_intern_10);
            TextView s12 = convertView.findViewById(R.id.stud_intern_12);
            TextView sskill = convertView.findViewById(R.id.stud_intern_skill);

            sname.setText(name[position]);
            semail.setText(email[position]);
            sphone.setText(phone[position]);
            scgpa.setText(cgpa[position]);
            s10.setText(mark10[position]);
            s12.setText(mark12[position]);
            sskill.setText(skill[position]);
        }
        return  convertView;
    }
}
