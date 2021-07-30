package com.example.insearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class List_feed extends ArrayAdapter<String> {


    private LayoutInflater mInflator;
//    private ArrayList<View_feed> users;
    String[] name;
    String[] review;
    String[] rate;

    private int mViewResourceId;

    public List_feed( Context context, int textViewResourceId, String[] name,String[] review,String[] rate) {
        super(context, textViewResourceId, name);
//        this.users = users;
        this.name = name;
        this.review=review;
        this.rate = rate;
        mInflator=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId=textViewResourceId;
    }


    public View getView(int position,  View convertView,  ViewGroup parent) {

        convertView=mInflator.inflate(mViewResourceId,null);

//        View_feed user=users.get(position);
        if(name!=null){
            TextView tName=(TextView) convertView.findViewById(R.id.sname);
            TextView tfeed=(TextView) convertView.findViewById(R.id.sfeed);
            TextView trating=(TextView) convertView.findViewById(R.id.srating);


            tName.setText(name[position]);
            tfeed.setText(review[position]);
            trating.setText(rate[position]);
        } return convertView;

    }
}