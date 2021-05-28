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
        ImageButton back = (ImageButton) findViewById(R.id.schepop_back);

        listView = (ListView)findViewById(R.id.sche_listview);  //리스트 구현 연결 및 객체생성
        list_itemArrayList = new ArrayList<list_item>();

        for(int i=0;i<5;i++){  //해당 날짜의 일정 수 만큼 반복
            list_itemArrayList.add(
                    new list_item("1"/*시험 이름 저장*/,String.valueOf(CalendarDay.today()),R.mipmap.ic_launcher_round)  //임의로 내용 지정
            );
        }
//        list_itemArrayList.add(
//                new list_item("1",new Date(CalendarDay.today()),R.mipmap.ic_launcher)  //임의로 내용 지정
//        );

        Intent intent = getIntent();
        String year = intent.getStringExtra("year");
        String mon = intent.getStringExtra("mon");
        String day = intent.getStringExtra("day");

        scheDate.setText(String.format("%s년 %s월 %s일",year, mon, day));

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });

        myListAdapter = new MyListAdapter(SchepopActivity.this,list_itemArrayList);
        listView.setAdapter(myListAdapter);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

 /*   @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }*/


}