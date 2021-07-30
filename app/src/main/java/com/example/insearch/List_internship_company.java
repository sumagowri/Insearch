package com.example.insearch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

public class List_internship_company extends ArrayAdapter<String> {
    private LayoutInflater mInflator;
    Context context;
    String[] name;
    String[] skill;
    String[] duration;
    String[] slots;
    String[] intern_ids;
    String comp_id;
    String intern_id;
    private int mViewResourceId;

    public List_internship_company(Context context, int textViewResourceId, String[] name, String[] skill, String[] duration,String[] slots,String[] intern_id,String comp_id) {
        super(context, textViewResourceId,name);
//        this.users = users;
        this.name = name;
        this.skill=skill;
        this.duration = duration;
        this.slots = slots;
        this.intern_ids = intern_id;
        this.context=context;
        this.comp_id=comp_id;
        mInflator=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId=textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        convertView=mInflator.inflate(mViewResourceId,null);

//        View_feed user=users.get(position);
        if(name!=null){
            TextView iName=(TextView) convertView.findViewById(R.id.intern_name);
            TextView iSkill=(TextView) convertView.findViewById(R.id.intern_skill);
            TextView iDuration=(TextView) convertView.findViewById(R.id.intern_durations);
            TextView iSlots = convertView.findViewById(R.id.slots_available);
            Button view_applicants = convertView.findViewById(R.id.view_applicants);

            iName.setText(name[position]);
            iSkill.setText(skill[position]);
            iDuration.setText(duration[position]);
            iSlots.setText(slots[position]);
            view_applicants.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intern_id = intern_ids[position];
                    Bundle bundle = new Bundle();
                    bundle.putString("intern_id",intern_id);
                    bundle.putString("comp_id",comp_id);
                    Intent intent = new Intent(context,Company_student_view.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }
        return convertView;
    }
}
