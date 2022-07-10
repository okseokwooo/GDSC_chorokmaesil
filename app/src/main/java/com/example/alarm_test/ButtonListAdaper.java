package com.example.alarm_test;

import android.widget.BaseAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ButtonListAdaper extends BaseAdapter{
    Context context;
    LayoutInflater inflater;
    ArrayList<Alarminfo> data;

    public ButtonListAdaper(Context context, ArrayList<Alarminfo> data){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        View view = inflater.inflate(R.layout.listview_item,parent,false);

        View bodyView = view.findViewById(R.id.bodyView);
        TextView timeView = view.findViewById(R.id.timeView);
        TextView messageView = view.findViewById(R.id.messageView);
        Switch alarmSwitch = view.findViewById(R.id.alarmSwitch);

        bodyView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(context, "click list body", Toast.LENGTH_SHORT).show();
            }
        });

        alarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                Toast.makeText(context,"click switch view",Toast.LENGTH_SHORT).show();
            }
        });
        return view;

    }

}
