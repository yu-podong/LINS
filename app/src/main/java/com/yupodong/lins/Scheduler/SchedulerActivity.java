package com.yupodong.lins.Scheduler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
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
import com.yupodong.lins.Mypage.MyPage;
import com.yupodong.lins.Qna.QnaActivity;
import com.yupodong.lins.R;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SchedulerActivity extends AppCompatActivity{

    private Map<CalendarDay, Integer> dayInstanceMap;

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

        //materialCalendarView.addDecorators(new EventDecorator(Color.RED , Collections.singleton(CalendarDay.today())));  //오늘날짜에 빨간 점 찍기
        OneDayDecorator oneDayDecorator = new OneDayDecorator();


        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -2);
        ArrayList<CalendarDay> dates = new ArrayList<>();
        for (int i = 0; i < 30; i++) {    // 이부분 달력에 날짜 여러개 포함하는 부분
            CalendarDay day = CalendarDay.from(calendar);
            dates.add(day);
            calendar.add(Calendar.DATE, 5);
        }
        ArrayList<CalendarDay> date = new ArrayList<>();


        for (int i=1;i<5;i++){// 시험 일정 수 만큼 반복하기
            CalendarDay day = CalendarDay.from(2021,4,i);  //시험 날짜를 받아와서
                //데이터의 갯수 저장
                EventDecorator[] decorator = new EventDecorator[3/*데이터의 수*/];  //시험일정이 있는 날짜에 시험 수 만큼 객체 생성 후
                for (int j = 0; j < decorator.length; j++) {
                    decorator[j] = new EventDecorator(Color.RED, 5, j); //그 수 만큼 점 찍기
                    decorator[j].addDate(day);  //해당 날짜를 event 객체에 저장하고
                    materialCalendarView.addDecorators(decorator[j]);  //달력에 표시

            }
        }

        EventDecorator[] decoratorArray = new EventDecorator[4]; //Max 4 dots
        for(int i = 0; i<decoratorArray.length; i++){
                decoratorArray[i] = new EventDecorator(Color.YELLOW,5,i);
                decoratorArray[i].addDate(CalendarDay.today());
                materialCalendarView.addDecorators(decoratorArray[i]);  //달력에 표시
        }



        materialCalendarView.addDecorators(  //달력 커스텀
                new SundayDecorator(),
                new SaturdayDecorator(),
              // new EventDecorator(Color.BLUE, Collections.singleton(CalendarDay.today())),
              // new EventDecorator(Color.RED, dates),
                /*decoratorArray[0],
                decoratorArray[1],
                decoratorArray[2],
                decoratorArray[3],*/

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
                Intent intent = new Intent(SchedulerActivity.this, MyPage.class);

                // 지금까지 열려있는 Activity들을 모두 종료
                for(int i = 0; i < actList.size(); i++)
                    actList.get(i).finish();

                // LicenseActivity로 이동
                startActivity(intent);
                // 현재 Activity 종료 후
                finish();
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
                Intent intent = new Intent(SchedulerActivity.this, QnaActivity.class);
                 // 지금까지 열려있는 Activity들을 모두 종료
                for(int i = 0; i < actList.size(); i++)
                    actList.get(i).finish();

                // LicenseActivity로 이동
                startActivity(intent);
                // 현재 Activity 종료 후
                finish();
            }
        });
    }



}