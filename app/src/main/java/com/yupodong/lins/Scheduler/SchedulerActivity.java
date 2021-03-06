package com.yupodong.lins.Scheduler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
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
import android.widget.Toast;

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

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SchedulerActivity extends AppCompatActivity{
    // firebase?????? data??? ???????????? ??????
    List<licenseScrap> temp = new ArrayList<licenseScrap>();
    List<license> scrapList = new ArrayList<license>();

    private Map<CalendarDay, Integer> dayInstanceMap;
    OneDayDecorator oneDayDecorator = new OneDayDecorator();
    MaterialCalendarView materialCalendarView;   //????????? ??????
    
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

        //------------------------------------- ???????????? ??????????????? ?????? -------------------------------------------------
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if(currentUser == null) {
            Toast.makeText(this, "???????????? ??? ?????? ??????????????????.", Toast.LENGTH_SHORT).show();
            finish();
        }

        //-------------------------------------- ????????? ????????? ????????? ?????? ?????? -------------------------------------------
        materialCalendarView = findViewById(R.id.calendarView);
        materialCalendarView.setSelectedDate(CalendarDay.today());  //???????????? ????????????

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -2);
        ArrayList<CalendarDay> dates = new ArrayList<>();
        for (int i = 0; i < 30; i++) {    // ????????? ????????? ?????? ????????? ???????????? ??????
            CalendarDay day = CalendarDay.from(calendar);
            dates.add(day);
            calendar.add(Calendar.DATE, 5);
        }
        ArrayList<CalendarDay> date = new ArrayList<>();

        //----------------------------------------------- ???????????? ???????????? ??????????????? ?????????(?????? ?????????) -------------------------------
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();


        // ?????? ???????????? ???????????? ????????? ????????? count
        firestore.collection("Scrap").whereEqualTo("nickName", currentUser.getEmail()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {

                            // ?????? ???????????? ???????????? ????????? lite ????????? list??? ??????
                            for(QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                temp.add(documentSnapshot.toObject(licenseScrap.class));
                            }
                            
                            // TOEIC ??????????????? ?????? ???????????? ?????? temp??? licenseID??? ??????
                            firestore.collection("TOEIC").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()){
                                        // TOEIC??? ??? ??????????????? ????????? ?????????
                                        for(QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                            license temp2 = documentSnapshot.toObject(license.class);

                                            // ?????? ??????????????? ID??? temp??? ???????????? ?????? ID??? ??????
                                            for(int i = 0; i < temp.size(); i++ ) {
                                                if(temp2.getLicenseID().equals(temp.get(i).getLicenseID()) && temp2.getLicenseName().equals(temp.get(i).getLicenseName())) {
                                                    scrapList.add(documentSnapshot.toObject(license.class));
                                                }
                                            }
                                        }

                                        // TOPCIT ??????????????? ?????? ???????????? ?????? temp??? licenseID??? ??????
                                        firestore.collection("TOPCIT").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if(task.isSuccessful()){
                                                    // TOEIC??? ??? ??????????????? ????????? ?????????
                                                    for(QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                                        license temp2 = documentSnapshot.toObject(license.class);

                                                        // ?????? ??????????????? ID??? temp??? ???????????? ?????? ID??? ??????
                                                        for(int i = 0; i < temp.size(); i++ ) {
                                                            if(temp2.getLicenseID().equals(temp.get(i).getLicenseID()) && temp2.getLicenseName().equals(temp.get(i).getLicenseName())) {
                                                                scrapList.add(documentSnapshot.toObject(license.class));
                                                            }
                                                        }
                                                    }
                                                    // EIP ??????????????? ?????? ???????????? ?????? temp??? licenseID??? ??????
                                                    firestore.collection("EIP").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                            if(task.isSuccessful()){
                                                                // TOEIC??? ??? ??????????????? ????????? ?????????
                                                                for(QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                                                    license temp2 = documentSnapshot.toObject(license.class);

                                                                    // ?????? ??????????????? ID??? temp??? ???????????? ?????? ID??? ??????
                                                                    for(int i = 0; i < temp.size(); i++ ) {
                                                                        if(temp2.getLicenseID().equals(temp.get(i).getLicenseID()) && temp2.getLicenseName().equals(temp.get(i).getLicenseName())) {
                                                                            scrapList.add(documentSnapshot.toObject(license.class));
                                                                        }
                                                                    }
                                                                }

                                                                // ???????????? ???????????? ?????? ?????? ?????? ?????? ????????????
                                                                for (int i = 0; i < scrapList.size(); i++){
                                                                    // i?????? ???????????? ???????????? ????????? ???,???,?????? Integer??? ??????
                                                                    Integer year = Integer.parseInt(scrapList.get(i).getLicenseDate().substring(0,4));
                                                                    Integer month = Integer.parseInt(scrapList.get(i).getLicenseDate().substring(5,7));
                                                                    Integer day = Integer.parseInt(scrapList.get(i).getLicenseDate().substring(8,10));

                                                                    // ????????? ???, ???, ?????? ?????? ????????? ???????????? ??????
                                                                    CalendarDay date = CalendarDay.from(year, month-1, day);

                                                                    // ??????????????? ????????? ?????? ?????? (?????? ?????????)
                                                                    EventDecorator decorator = new EventDecorator(Color.RED, 5, 1);
                                                                    // ?????? ???????????? ??????????????? event ????????? ????????????
                                                                    decorator.addDate(date);
                                                                    materialCalendarView.addDecorators(decorator);  //????????? ??????
                                                                }

                                                                materialCalendarView.addDecorators(  //?????? ?????????
                                                                        new SundayDecorator(),
                                                                        new SaturdayDecorator(),
                                                                        oneDayDecorator
                                                                );
                                                            }
                                                        }
                                                    });
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                        }
                        else {
                            // nothing do.. (?????? ????????? ??????)
                        }
                    }
                });


        //????????? ?????? ?????? ??????

        Intent intent = new Intent(this, SchepopActivity.class);
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {  //?????? ????????? ????????? ?????????
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {



                Date CurrenTime = date.getDate();  //?????? ????????????

                SimpleDateFormat weekdayFormat = new SimpleDateFormat("EE", Locale.getDefault());  //????????? ????????? ?????? ??????
                SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
                SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
                SimpleDateFormat yearFormat = new SimpleDateFormat("YYYY", Locale.getDefault());

                String weekday = weekdayFormat.format(CurrenTime);  //???????????? ??????
                String year = yearFormat.format(CurrenTime);
                String month = monthFormat.format(CurrenTime);
                String day = dayFormat.format(CurrenTime);
                ArrayList<String> licenseName = new ArrayList<String>();


                for(int i = 0; i < scrapList.size(); i++) {
                    if(scrapList.get(i).getLicenseDate().substring(0,10).equals(year + "." + month + "." + day)) {
                        licenseName.add(scrapList.get(i).getLicenseName());
                    }
                }

                intent.putExtra("year", year);  //???????????? ?????? ??????
                intent.putExtra("mon" ,month);
                intent.putExtra("day" ,day);
                intent.putStringArrayListExtra("licenseName", licenseName);
                startActivityForResult(intent,3);
            }

        });
        //--------------------------------------- ?????? ??? ?????? ?????? listener----------------------------------
        ArrayList<Activity> actList = new ArrayList<Activity>();

        backBtn.setOnClickListener(new View.OnClickListener() {  //???????????? ????????? ??????
            @Override
            public void onClick(View view) {
                // ?????? Activity??? ???????????? ?????? ???????????? ????????????
                finish();
            }
        });

        myBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SchedulerActivity.this, MyPage.class);

                // ???????????? ???????????? Activity?????? ?????? ??????
                for(int i = 0; i < actList.size(); i++)
                    actList.get(i).finish();

                // LicenseActivity??? ??????
                startActivity(intent);
                // ?????? Activity ?????? ???
                finish();
            }
        });

        licenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(SchedulerActivity.this, LicenseActivity.class);

                    // ???????????? ???????????? Activity?????? ?????? ??????
                    for(int i = 0; i < actList.size(); i++)
                        actList.get(i).finish();

                    // LicenseActivity??? ??????
                startActivity(intent);
                // ?????? Activity ?????? ???
                finish();
            }
        });

        calenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SchedulerActivity.this, SchedulerActivity.class);
                // ???????????? ???????????? Activity?????? ?????? ??????
                for(int i = 0; i < actList.size(); i++)
                    actList.get(i).finish();
                // SchedulerActivity??? ??????
                startActivity(intent);
                // ?????? Activity ?????? ???
                finish();
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {  //??????????????? ??????
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SchedulerActivity.this, MainActivity.class);
                // ???????????? ???????????? Activity?????? ?????? ??????
                for(int i = 0; i < actList.size(); i++)
                    actList.get(i).finish();
                // MainActivity??? ??????
                startActivity(intent);
                // ?????? Activity ?????? ???
                finish();
            }
        });

        commBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SchedulerActivity.this, CommuActivity.class);
                // ???????????? ???????????? Activity?????? ?????? ??????
                for(int i = 0; i < actList.size(); i++)
                    actList.get(i).finish();

                // CommuActivity??? ??????
                startActivity(intent);
                // ?????? Activity ?????? ???
                finish();
            }
        });

        chalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SchedulerActivity.this, QnaActivity.class);
                 // ???????????? ???????????? Activity?????? ?????? ??????
                for(int i = 0; i < actList.size(); i++)
                    actList.get(i).finish();

                // LicenseActivity??? ??????
                startActivity(intent);
                // ?????? Activity ?????? ???
                finish();
            }
        });
    }



}