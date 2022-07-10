package com.example.alarm_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView demolist;

    ArrayList alarmlist = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Alarminfo info1 = new Alarminfo(9,00,"오전","수빈","전화로 깨워주세요");
        alarmlist.add(info1);
        Alarminfo info2 = new Alarminfo(10,00,"오전","동재","직접 깨워주세요");
        alarmlist.add(info2);
        Alarminfo info3 = new Alarminfo(12,20,"오후","석우","전화로 깨워주세요");
        alarmlist.add(info3);

        demolist = findViewById(R.id.alarmListView);
        ButtonListAdaper buttonListAdaper = new ButtonListAdaper(this,alarmlist);

        demolist.setAdapter(buttonListAdaper);
    }
}