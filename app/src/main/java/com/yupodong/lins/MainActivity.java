package com.yupodong.lins;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.yupodong.lins.Crawler.EIP;
import com.yupodong.lins.Crawler.TOEIC;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yupodong.lins.Community.CommuActivity;
import com.yupodong.lins.DTO.*;
import com.yupodong.lins.License.LicenseActivity;
import com.yupodong.lins.Login.LoginActivity;
import com.yupodong.lins.Qna.QnaActivity;
import com.yupodong.lins.Scheduler.SchedulerActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG="MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().subscribeToTopic("notice");

//        Button logTokkenButton = findViewById(R.id.logTokenButton);
//        logTokkenButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//
//                FirebaseMessaging.getInstance().getToken()
//                        .addOnCompleteListener(new OnCompleteListener<String>() {
//                            @Override
//                            public void onComplete(@NonNull Task<String> task) {
//                                if (!task.isSuccessful()) {
//                                    Log.w(TAG, "Fetching FCM registration token failed", task.getException());
//                                    return;
//                                }
//
//                                // Get new FCM registration token
//                                String token = task.getResult();
//
//                                // Log and toast
//                                String msg = getString(R.string.msg_token_fmt, token);
//                                Log.d(TAG, msg);
//                                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
//                            }
//                        });
//
//            }
//        });


        // 수정된 부분 : Activity 전환을 위해 사용
        // 그냥 findViewById로 4개의 button에 접근하게 되면, error 발생 (null object)
//        LinearLayout firstBtnWrap = (LinearLayout)findViewById(R.id.firstBtnWrap);
//        LinearLayout secondBtnWrap = (LinearLayout)findViewById(R.id.secondBtnWrap);

        // APP에서 뒤로가기를 지원하기 위해 Activity이동으로 바꿈 (login만 setContentView 사용)

        TextView login = (TextView) findViewById(R.id.log);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // LicenseActivity로 전환
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        Button licensebtn = (Button)findViewById(R.id.licensebtn); //Button licensebtn = (Button) firstBtnWrap.findViewById(R.id.licensebtn);
        licensebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // LicenseActivity로 전환
                Intent intent = new Intent(MainActivity.this, LicenseActivity.class);
                startActivity(intent);
            }
        });

        Button schebtn = (Button)findViewById(R.id.schebtn); //Button schebtn = (Button) firstBtnWrap.findViewById(R.id.schebtn);
        schebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // SchedulerActivity로 전환
                Intent intent = new Intent(MainActivity.this, SchedulerActivity.class);
                startActivity(intent);
            }
        });

        Button commubtn = (Button)findViewById(R.id.commubtn); //Button commubtn = (Button) secondBtnWrap.findViewById(R.id.commubtn);
        commubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // CommuActivity로 전환
                Intent intent = new Intent(MainActivity.this, CommuActivity.class);
                startActivity(intent);
            }
        });

        Button qnabtn = (Button)findViewById(R.id.qnabtn); //Button qnabtn = (Button) secondBtnWrap.findViewById(R.id.qnabtn);
        qnabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, QnaActivity.class);
                startActivity(intent);
//                Toast.makeText(MainActivity.this, "아직 구현 중입니다.", Toast.LENGTH_SHORT).show();
            }
        });
        
        /*// TOEIC 크롤러 실행
        crawlingTOEIC();
        // EIP 크롤러 실행
        crawlingEIP();
        // TOPCIT 크롤러 실행
        crawlingTOPCIT();*/




    }

    public void crawlingTOEIC() {
        // Main Thread에서 https에 접근하는 행위를 함께 실행하면 error 발생 -> 새로운 Thread 생성
        new Thread() {
            public void run() {
                // TOEIC 크롤러를 실행하기 위한 객체 호출
                TOEIC toeic = new TOEIC();
                // TOEIC 크롤러가 반환하는 TOEIC 시험일정의 리스트를 받을 crawling list 생성
                List<crawling> toeicList = toeic.crawlingToeic();
                // firebase에 접근하기 위해 필요
                FirebaseFirestore firestore = FirebaseFirestore.getInstance();

                for(int i = 0; i < toeicList.size(); i++) {
                    firestore.collection("TOEIC").document().set(toeicList.get(i))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    //Toast.makeText(MainActivity.this, "성공했습니다.", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        }.start();
    }

    public void crawlingEIP() {
        new Thread() {
            public void run() {
                // 정보처리기사 크롤러를 실행하기 위한 EIP 객체 생성
                EIP eip = new EIP();
                // 정보처리기사 크롤러러가 반환하는 EIP 시험정보 list를 저장
                List<crawling> eipList = eip.crawlingsEIP();
                // firebase에 접근하기 위해 필요
                FirebaseFirestore firestore = FirebaseFirestore.getInstance();

                for (int i = 0; i < eipList.size(); i++) {
                    firestore.collection("EIP").document().set(eipList.get(i))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                //Toast.makeText(MainActivity.this, "성공했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                }
            }
        }.start();
    }
    public void crawlingTOPCIT() {
        new Thread() {
            public void run() {
                /*// 정보처리기사 크롤러를 실행하기 위한 EIP 객체 생성
                EIP eip = new EIP();
                // 정보처리기사 크롤러러가 반환하는 EIP 시험정보 list를 저장
                List<crawling> eipList = eip.crawlingsEIP();*/
                // firebase에 접근하기 위해 필요
                crawling topcit = new crawling();

                topcit.setLicenseLink("https://www.topcit.or.kr/receipt/evalList.do");
                topcit.setLicenseName("TOPCIT");
                topcit.setApplyPeriod("2021.04.12 ~ 2021.04.23");
                topcit.setLicenseDate("2021.05.22 오전 09:30~12:00");
                FirebaseFirestore firestore = FirebaseFirestore.getInstance();

                firestore.collection("TOPCIT").document().set(topcit)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                //Toast.makeText(MainActivity.this, "성공했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }.start();
    }



}