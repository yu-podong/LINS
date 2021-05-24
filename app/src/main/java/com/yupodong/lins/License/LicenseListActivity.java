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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.yupodong.lins.Community.CommuActivity;
import com.yupodong.lins.Crawler.TOEIC;
import com.yupodong.lins.DTO.crawling;
import com.yupodong.lins.MainActivity;
import com.yupodong.lins.R;
import com.yupodong.lins.Scheduler.SchedulerActivity;

import org.w3c.dom.Document;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class LicenseListActivity extends AppCompatActivity {
    // 지금까지 item들을 List로 가져옴
    ListView licenseview;
    LicenseAdapter licenseAdapter;
    ArrayList<licenselist> licenselistArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license_list);
        //------------------------------- 시험일정을 보여주기 위한 작업-------------------------------------
        // 시험일정을 저장할 List 선언
        List<crawling> listTOEIC = new ArrayList<crawling>();
        List<crawling> listEIP = new ArrayList<crawling>();
        List<crawling> listTOPCIT = new ArrayList<crawling>();
        // 클릭한 자격증을 상단에 보여주기 위해
        TextView licenseTitle = (TextView)findViewById(R.id.licenseName);

        // 이전 액티비티(LicenseActivity)를 가져옴
        Intent intent = getIntent();
        // 이전 액티비티에서 전달한 값을 저장
        String clickLicenseName = intent.getStringExtra("licenseName");

        // 사용자 이전 액티비티에서 TOEIC을 클릭했다면, TOEIC 일정 가져오기
        if(clickLicenseName == "TOEIC") {
            licenseTitle.setText("TOEIC");
            listTOEIC = getListTOEIC();

            for(int i = 0; i < listTOEIC.size(); i++){
                licenselistArrayList.add(
                        new licenselist(listTOEIC.get(i).getLicenseDate(),listTOEIC.get(i).getApplyPeriod(), R.drawable.ic_scrap)  //임의로 내용 지정
                );
            }
        }
        // 사용자 이전 액티비티에서 TOEIC을 클릭했다면, TOPCIT 일정 가져오기
        else if(clickLicenseName == "TOPCIT") {
            licenseTitle.setText("TOPCIT");
            listTOPCIT = getListTOPCIT();

            for(int i = 0; i < listTOPCIT.size(); i++){
                licenselistArrayList.add(
                        new licenselist(listTOPCIT.get(i).getLicenseDate(),listTOPCIT.get(i).getApplyPeriod(), R.drawable.ic_scrap)  //임의로 내용 지정
                );
            }
        }
        // 사용자 이전 액티비티에서 TOEIC을 클릭했다면, EIP 일정 가져오기
        else if(clickLicenseName == "EIP") {
            licenseTitle.setText("정보처리기사");
            listEIP = getListEIP();

            for(int i = 0; i < listEIP.size(); i++){
                licenselistArrayList.add(
                        new licenselist(listEIP.get(i).getLicenseDate(),listEIP.get(i).getApplyPeriod(), R.drawable.ic_scrap)  //임의로 내용 지정
                );
            }
        }

        
        // firebase에서 가져온 시험일정을 보여주는 부분
        licenseview = (ListView)findViewById(R.id.licenselistview);
        licenselistArrayList = new ArrayList<licenselist>();
        licenseAdapter = new LicenseAdapter(LicenseListActivity.this,licenselistArrayList);
        licenseview.setAdapter(licenseAdapter);

        //----------------------------- 상단 및 하단 버튼들 clickListener 등록--------------------------------
        // 상단 버튼
        ImageButton backBtn = (ImageButton)findViewById(R.id.backBtn);
        ImageButton myBtn = (ImageButton)findViewById(R.id.myBtn);
        // 하단 이미지 버튼
        ImageButton licenBtn = (ImageButton)findViewById(R.id.licenBtn);
        ImageButton calenBtn = (ImageButton)findViewById(R.id.calenBtn);
        ImageButton homeBtn = (ImageButton)findViewById(R.id.homeBtn);
        ImageButton commBtn = (ImageButton)findViewById(R.id.commBtn);

        // 지금까지 열려있는 Activity 저장하는 ArrayList
        ArrayList<Activity> actList = new ArrayList<Activity>();

        // 페이지 이동
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

    // firebase에서 TOEIC 시험일정을 전부 가져옴
    public List<crawling> getListTOEIC() {
        // Firebase를 위해 필요
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        // return할 list를 담을 친구
        List<crawling> TOEIC = new ArrayList<crawling>();
        
        // data 가져오기
        firestore.collection("TOEIC").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    int i = 0;
                    for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        TOEIC.add(documentSnapshot.toObject(crawling.class));
                        System.out.println(TOEIC.get(i).getApplyPeriod());
                        i++;
                    }
                }
                else {
                    Toast.makeText(LicenseListActivity.this, "TOEIC 시험일정을 가져오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 이게 왜 실행이 안되지?
        for(int i = 0; i < TOEIC.size(); i++) {
            System.out.println(TOEIC.get(i).getApplyPeriod());
        }

        return TOEIC;
    }
    // firebase에서 TOPCIT 시험일정을 가져옴
    public List<crawling> getListTOPCIT() {
        // Firebase를 위해 필요
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        // return할 list를 담을 친구
        List<crawling> TOPCIT = new ArrayList<crawling>();

        // data 가져오기
        firestore.collection("TOPCIT").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    int i = 0;
                    for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        TOPCIT.add(documentSnapshot.toObject(crawling.class));
                        System.out.println(TOPCIT.get(i).getApplyPeriod());
                        i++;
                    }
                }
                else {
                    Toast.makeText(LicenseListActivity.this, "TOPCIT 시험일정을 가져오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 이게 왜 실행이 안되지?
        for(int i = 0; i < TOPCIT.size(); i++) {
            System.out.println(TOPCIT.get(i).getApplyPeriod());
        }

        return TOPCIT;
    }
    // firebase에서 정보처리기사 시험일정을 가져옴
    public List<crawling> getListEIP() {
        // Firebase를 위해 필요
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        // return할 list를 담을 친구
        List<crawling> EIP = new ArrayList<crawling>();

        // data 가져오기
        firestore.collection("EIP").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    int i = 0;
                    for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        EIP.add(documentSnapshot.toObject(crawling.class));
                        System.out.println(EIP.get(i).getApplyPeriod());
                        i++;
                    }
                }
                else {
                    Toast.makeText(LicenseListActivity.this, "TOEIC 시험일정을 가져오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 이게 왜 실행이 안되지?
        for(int i = 0; i < EIP.size(); i++) {
            System.out.println(EIP.get(i).getApplyPeriod());
        }

        return EIP;
    }
}