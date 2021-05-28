package com.yupodong.lins.Mypage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.yupodong.lins.Community.CommuActivity;
import com.yupodong.lins.License.LicenseActivity;
import com.yupodong.lins.MainActivity;
import com.yupodong.lins.R;
import com.yupodong.lins.Scheduler.SchedulerActivity;

import java.util.ArrayList;

public class ScrapActivity extends AppCompatActivity {

    ListView scrapview;
    ScrapAdapter scrapAdapter;
    ArrayList<ScrapList> scrapListArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrap);
        scrapview= (ListView)findViewById(R.id.scraplistview);
        scrapListArrayList = new ArrayList<ScrapList>();

        for(int i=0;i<6;i++){
            scrapListArrayList.add(
                    new ScrapList("2021년 5월 26일","2021년 6월 14일",R.drawable.ic_star)
            );
        }

        scrapAdapter = new ScrapAdapter(ScrapActivity.this,scrapListArrayList);
        scrapview.setAdapter(scrapAdapter);


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
            Intent intent = new Intent(ScrapActivity.this, LicenseActivity.class);

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
            Intent intent = new Intent(ScrapActivity.this, SchedulerActivity.class);
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
            Intent intent = new Intent(ScrapActivity.this, MainActivity.class);
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
            Intent intent = new Intent(ScrapActivity.this, CommuActivity.class);
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
