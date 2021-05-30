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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.yupodong.lins.Community.CommuActivity;
import com.yupodong.lins.DTO.license;
import com.yupodong.lins.DTO.licenseScrap;
import com.yupodong.lins.License.LicenseActivity;
import com.yupodong.lins.MainActivity;
import com.yupodong.lins.Mypage.MyPage;
import com.yupodong.lins.Mypage.ScrapList;
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

        //-------------------------------------- 커스텀 달력에 필요한 요소 작성 -------------------------------------------
        MaterialCalendarView materialCalendarView;   //커스텀 달력
        materialCalendarView = findViewById(R.id.calendarView);
        materialCalendarView.setSelectedDate(CalendarDay.today());  //오늘날짜 선택하기

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

        //----------------------------------------------- 사용자가 스크랩한 시험일정을 가져옴(아직 미완성) -------------------------------
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // 해당 사용자가 스크랩한 정보의 개수를 count
        firestore.collection("Scrap").whereEqualTo("nickName", currentUser.getEmail()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            List<licenseScrap> temp = new ArrayList<licenseScrap>();
                            List<license> scrapList = new ArrayList<license>();
                            // 해당 사용자가 스크랩한 정보의 lite 버전을 list로 저장
                            for(QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                temp.add(documentSnapshot.toObject(licenseScrap.class));
                            }
                            // 가져온 스크랩 정보에 저장된 license name과 ID를 이용하여 해당 시험일정을 알맞은 DB에서 가져옴
                            System.out.println(temp.get(0).getLicenseName());


                            // 사용자가 스크랩한 시험 일정 개수 만큼 반복하기
                            for (int i = 0; i < temp.size(); i++){
                                firestore.collection(temp.get(i).getLicenseName()).whereEqualTo("licenseID", temp.get(i).getLicenseID())
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                scrapList.add(task.getResult().toObjects(license.class).get(0));
                                            }
                                        });
                                System.out.println(scrapList.get(0).getLicenseName());
                                // i번째 스크랩의 시험일정 정보를 년,월,일로 Integer로 변환
                                Integer year = Integer.parseInt(scrapList.get(i).getLicenseDate().substring(0,3));
                                Integer month = Integer.parseInt(scrapList.get(i).getLicenseDate().substring(6,6));
                                Integer day = Integer.parseInt(scrapList.get(i).getLicenseDate().substring(8,9));

                                // 위에서 년, 월, 일로 나눈 날짜를 가져와서 사용
                                CalendarDay date = CalendarDay.from(year, month, day);
                                // 시험일정이 있다는 것을 표시 (빨간 점으로)
                                EventDecorator decorator = new EventDecorator(Color.RED, 5, 1);
                                // 어느 자격증의 시험일정을 event 객체에 저장하고
                                decorator.addDate(date);
                                materialCalendarView.addDecorators(decorator);  //달력에 표시
                            }

                            materialCalendarView.addDecorators(  //달력 커스텀
                                    new SundayDecorator(),
                                    new SaturdayDecorator(),
                                    oneDayDecorator
                            );
                        }
                        else {
                            // nothing do.. (현재 날짜만 표시)
                        }
                    }
                });


        //팝업창 정보 전달 객체

        Intent intent = new Intent(this, SchepopActivity.class);
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
        //--------------------------------------- 하단 및 상단 버튼 listener----------------------------------
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