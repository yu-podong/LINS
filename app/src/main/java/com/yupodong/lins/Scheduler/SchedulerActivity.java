package com.yupodong.lins.Scheduler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.OnLifecycleEvent;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.yupodong.lins.Community.CommuActivity;
import com.yupodong.lins.License.LicenseActivity;
import com.yupodong.lins.MainActivity;
import com.yupodong.lins.R;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class SchedulerActivity extends AppCompatActivity  {

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sche);

        ImageButton backBtn = (ImageButton)findViewById(R.id.backBtn);
        ImageButton myBtn = (ImageButton)findViewById(R.id.myBtn);
        ImageButton licenBtn = (ImageButton)findViewById(R.id.licenBtn);
        ImageButton calenBtn = (ImageButton)findViewById(R.id.calenBtn);
        ImageButton homeBtn = (ImageButton)findViewById(R.id.homeBtn);
        ImageButton commBtn = (ImageButton)findViewById(R.id.commBtn);
        ImageButton chalBtn = (ImageButton)findViewById(R.id.chalBtn);



        MaterialCalendarView materialCalendarView;   //커스텀 달력
        materialCalendarView = findViewById(R.id.calendarView);
        materialCalendarView.setSelectedDate(CalendarDay.today());  //오늘날짜 선택하기

        materialCalendarView.addDecorators(new EventDecorator(Color.RED , Collections.singleton(CalendarDay.today())));  //오늘날짜에 빨간 점 찍기
        OneDayDecorator oneDayDecorator = new OneDayDecorator();


        materialCalendarView.addDecorators(  //달력 커스텀
                new SundayDecorator(),
                new SaturdayDecorator(),
                oneDayDecorator
        );

        Intent intent = new Intent(this, SchepopActivity.class);  //팝업창 정보 전달 객체

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {  //날짜 선택시 팝업창 띄우기

            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {


                Date CurrenTime = date.getDate();  //날짜 받아오기

                SimpleDateFormat weekdayFormat = new SimpleDateFormat("EE", Locale.getDefault());  //패턴에 맞추어 변수 저장
                SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
                SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
                SimpleDateFormat yearFormat = new SimpleDateFormat("YYYY", Locale.getDefault());

                String weekday = weekdayFormat.format(CurrenTime);  //문자열로 변환
                String year = yearFormat.format(CurrenTime);
                String month = monthFormat.format(CurrenTime);
                String day = dayFormat.format(CurrenTime);

                intent.putExtra("year", year);  //팝업창에 정보 전달
                intent.putExtra("mon" ,month);
                intent.putExtra("day" ,day);
                startActivityForResult(intent,3);
            }

        });

        ArrayList<Activity> actList = new ArrayList<Activity>();

        backBtn.setOnClickListener(new View.OnClickListener() {  //커뮤니티 페이지 이동
            @Override
            public void onClick(View view) {
                // 현재 Activity를 종료하여 이전 화면으로 돌아가기
                finish();
            }
        });

        myBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 지금까지 열려있는 Activity들을 모두 종료
                for(int i = 0; i < actList.size(); i++)
                    actList.get(i).finish();
                setContentView(R.layout.activity_main);  //수정페이지 없어서 일단 메인으로 이동
            }
        });

        licenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SchedulerActivity.this, LicenseActivity.class);

                // 지금까지 열려있는 Activity들을 모두 종료
                for(int i = 0; i < actList.size(); i++)
                    actList.get(i).finish();

                // LicenseActivity로 이동
                startActivity(intent);
                // 현재 Activity 종료 후
                finish();
            }
        });

        calenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SchedulerActivity.this, SchedulerActivity.class);
                // 지금까지 열려있는 Activity들을 모두 종료
                for(int i = 0; i < actList.size(); i++)
                    actList.get(i).finish();
                // SchedulerActivity로 이동
                startActivity(intent);
                // 현재 Activity 종료 후
                finish();
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {  //메인페이지 버튼
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SchedulerActivity.this, MainActivity.class);
                // 지금까지 열려있는 Activity들을 모두 종료
                for(int i = 0; i < actList.size(); i++)
                    actList.get(i).finish();
                // MainActivity로 이동
                startActivity(intent);
                // 현재 Activity 종료 후
                finish();
            }
        });

        commBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SchedulerActivity.this, CommuActivity.class);
                // 지금까지 열려있는 Activity들을 모두 종료
                for(int i = 0; i < actList.size(); i++)
                    actList.get(i).finish();

                // CommuActivity로 이동
                startActivity(intent);
                // 현재 Activity 종료 후
                finish();
            }
        });

        chalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Intent intent = new Intent(CommuListActivity.this, LicenseActivity.class);
                 // 지금까지 열려있는 Activity들을 모두 종료
                for(int i = 0; i < actList.size(); i++)
                    actList.get(i).finish();

                // LicenseActivity로 이동
                startActivity(intent);
                // 현재 Activity 종료 후
                finish();
                 */
            }
        });
    }



}