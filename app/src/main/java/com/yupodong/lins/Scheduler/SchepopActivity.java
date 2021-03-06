package com.yupodong.lins.Scheduler;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.yupodong.lins.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SchepopActivity extends Activity {
    ListView listView;  //일정 리스트 구현
    MyListAdapter myListAdapter;
    ArrayList<list_item> list_itemArrayList;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView((R.layout.activity_sche_pop));
        TextView scheDate = (TextView)findViewById(R.id.schepop_date);

        Intent intent = getIntent();
        String year = intent.getStringExtra("year");
        String mon = intent.getStringExtra("mon");
        String day = intent.getStringExtra("day");
        ArrayList<String> licenseName = intent.getStringArrayListExtra("licenseName");

        listView = (ListView)findViewById(R.id.sche_listview);  //리스트 구현 연결 및 객체생성
        list_itemArrayList = new ArrayList<list_item>();

       for(int i = 0; i < licenseName.size(); i++) {
           list_itemArrayList.add(
                   new list_item(licenseName.get(i), String.valueOf(CalendarDay.today()), R.drawable.circle)  //임의로 내용 지정
           );
       }


        scheDate.setText(String.format("%s. %s. %s",year, mon, day));

        myListAdapter = new MyListAdapter(SchepopActivity.this,list_itemArrayList);
        listView.setAdapter(myListAdapter);
    }
}