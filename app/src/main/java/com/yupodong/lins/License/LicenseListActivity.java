package com.yupodong.lins.License;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.yupodong.lins.Community.CommuActivity;
import com.yupodong.lins.DTO.crawling;
import com.yupodong.lins.MainActivity;
import com.yupodong.lins.R;
import com.yupodong.lins.Scheduler.SchedulerActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class LicenseListActivity extends AppCompatActivity {
    // 지금까지 item들을 List로 가져옴
    ListView licenseview;
    LicenseAdapter licenseAdapter;
    ArrayList<licenselist> licenselistArrayList;
    List<crawling> TOEIC = new ArrayList<crawling>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license_list);
        // Firebase를 사용하기 위해 작성함
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference collectionTOEIC = firestore.collection("TOEIC");

        collectionTOEIC.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // getId() : document 이름, getData() : document에 들어있는 data들
                            Integer i = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                crawling getItem = new crawling();
                                getItem.setLicenseDate((String) document.getData().get("LicenseDate"));
                                getItem.setLicenseLink((String) document.getData().get("LicenseLink"));
                                getItem.setLicenseName((String) document.getData().get("LicenseName"));
                                getItem.setApplyPeriod((String) document.getData().get("applyPeriod"));

                                TOEIC.add(getItem);
                            }
                        } else {
                            Toast.makeText(LicenseListActivity.this, "에러가 발생했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        for(int i = 0; i < TOEIC.size(); i++) {
            Toast.makeText(LicenseListActivity.this, TOEIC.get(i).getLicenseName(), Toast.LENGTH_SHORT).show();
        }
        licenseview = (ListView)findViewById(R.id.licenselistview);
        licenselistArrayList = new ArrayList<licenselist>();

        for(int i=0;i<TOEIC.size();i++){
            licenselistArrayList.add(
                    new licenselist(TOEIC.get(i).getLicenseDate(),TOEIC.get(i).getApplyPeriod(), R.drawable.ic_scrap)  //임의로 내용 지정
            );
        }

        licenseAdapter = new LicenseAdapter(LicenseListActivity.this,licenselistArrayList);
        licenseview.setAdapter(licenseAdapter);

        // 하단 이미지 버튼
        ImageButton backBtn = (ImageButton)findViewById(R.id.backBtn);
        ImageButton myBtn = (ImageButton)findViewById(R.id.myBtn);
        ImageButton licenBtn = (ImageButton)findViewById(R.id.licenBtn);
        ImageButton calenBtn = (ImageButton)findViewById(R.id.calenBtn);
        ImageButton homeBtn = (ImageButton)findViewById(R.id.homeBtn);
        ImageButton commBtn = (ImageButton)findViewById(R.id.commBtn);
        ImageButton chalBtn = (ImageButton)findViewById(R.id.chalBtn);
        TextView licenseTitle = (TextView)findViewById(R.id.test_title);

        //조금 더 고민해보는거루 ><
        //licenseTitle.setText("TOEIC");

        // 페이지 이동
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
                Intent intent = new Intent(LicenseListActivity.this, LicenseActivity.class);

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
                Intent intent = new Intent(LicenseListActivity.this, SchedulerActivity.class);
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
                Intent intent = new Intent(LicenseListActivity.this, MainActivity.class);
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
                Intent intent = new Intent(LicenseListActivity.this, CommuActivity.class);
                // 지금까지 열려있는 Activity들을 모두 종료
                for(int i = 0; i < actList.size(); i++)
                    actList.get(i).finish();

                // CommuActivity로 이동
                startActivity(intent);
                // 현재 Activity 종료 후
                finish();
            }
        });
    }
}