package com.yupodong.lins.License;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private ListView licenseview;
    private LicenseAdapter licenseAdapter;
    private ArrayList<licenselist> licenselistArrayList;
    private List<crawling> list = new ArrayList<crawling>();

    public List<crawling> getList() {
        return list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license_list);
        licenseview = (ListView)findViewById(R.id.licenselistview);
        licenselistArrayList = new ArrayList<licenselist>();
        //------------------------------- 시험일정을 보여주기 위한 작업-------------------------------------
        // 클릭한 자격증을 상단에 보여주기 위해
        TextView licenseName = (TextView)findViewById(R.id.licenseName);
        // 이전 액티비티(LicenseActivity)를 가져옴
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        // 이전 액티비티에서 전달한 값을 저장
        String clickLicenseName = bundle.getString("licenseName");

        licenseName.setText(clickLicenseName);
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        // data 가져오기 (내림차순으로 - orderBy)
        firestore.collection(clickLicenseName).orderBy("licenseID").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    // 모든 문서(각 시험일정)를 list에 저장
                    for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        list.add(documentSnapshot.toObject(crawling.class));
                    }

                    // 가져온 시험일정을 xml로 보여주기 위한 작업 실행
                    for(int j = 0; j < list.size(); j++){
                        licenselistArrayList.add(
                                new licenselist(list.get(j).getLicenseName(),j+1, list.get(j).getLicenseDate(),list.get(j).getApplyPeriod(), R.drawable.ic_scrap)  //임의로 내용 지정
                        );
                    }
                    // 각 시험일정을 xml을 적용시킨 list를 listview에 보여줌 (최종)
                    licenseAdapter = new LicenseAdapter(LicenseListActivity.this,licenselistArrayList);
                    licenseview.setAdapter(licenseAdapter);
                }
                else {
                    Toast.makeText(LicenseListActivity.this, "시험일정을 가져오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
}