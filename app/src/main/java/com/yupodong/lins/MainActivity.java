package com.yupodong.lins;
import com.yupodong.lins.Crawler.EIP;
import com.yupodong.lins.Crawler.TOEIC;
import com.yupodong.lins.Crawler.TOEIC.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yupodong.lins.Community.CommuActivity;
import com.yupodong.lins.DTO.*;
import com.yupodong.lins.License.LicenseActivity;
import com.yupodong.lins.Login.LoginActivity;
import com.yupodong.lins.Scheduler.SchedulerActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 수정된 부분 : Activity 전환을 위해 사용
        // 그냥 findViewById로 4개의 button에 접근하게 되면, error 발생 (null object)
        LinearLayout firstBtnWrap = findViewById(R.id.firstBtnWrap);
        LinearLayout secondBtnWrap = findViewById(R.id.secondBtnWrap);

        // APP에서 뒤로가기를 지원하기 위해 Activity이동으로 바꿈 (login만 setContentView 사용)

        Button login = (Button) findViewById(R.id.log);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // LicenseActivity로 전환
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        Button licensebtn = (Button) firstBtnWrap.findViewById(R.id.licensebtn);
        licensebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // LicenseActivity로 전환
                Intent intent = new Intent(MainActivity.this, LicenseActivity.class);
                startActivity(intent);
            }
        });

        Button schebtn = (Button) firstBtnWrap.findViewById(R.id.schebtn);
        schebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // SchedulerActivity로 전환
                Intent intent = new Intent(MainActivity.this, SchedulerActivity.class);
                startActivity(intent);
            }
        });

        Button commubtn = (Button) secondBtnWrap.findViewById(R.id.commubtn);
        commubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // CommuActivity로 전환
                Intent intent = new Intent(MainActivity.this, CommuActivity.class);
                startActivity(intent);
            }
        });

        Button qnabtn = (Button) secondBtnWrap.findViewById(R.id.qnabtn);
        qnabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "아직 구현 중입니다.", Toast.LENGTH_SHORT).show();
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
                    firestore.collection("TOEIC").document(Integer.toString(i+1)).set(toeicList.get(i))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(MainActivity.this, "성공했습니다.", Toast.LENGTH_SHORT).show();
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
                    firestore.collection("EIP").document(Integer.toString(i+1)).set(eipList.get(i))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MainActivity.this, "성공했습니다.", Toast.LENGTH_SHORT).show();
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

                firestore.collection("TOPCIT").document("1").set(topcit)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MainActivity.this, "성공했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }.start();
    }

}