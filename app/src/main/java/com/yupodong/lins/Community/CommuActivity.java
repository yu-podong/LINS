package com.yupodong.lins.Community;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.yupodong.lins.MainActivity;
import com.yupodong.lins.R;

public class CommuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commu);

        Button commu1 = (Button)findViewById(R.id.commu1);
        Button commu2 = (Button)findViewById(R.id.commu2);
        Button commu3 = (Button)findViewById(R.id.commu3);
        Button commu4= (Button)findViewById(R.id.commu4);

        Intent intent = new Intent(CommuActivity.this, CommuListActivity.class);
        // 모든 commu button은 CommuListActivity로 Activity 전환으로 이동
        // 왜냐하면 뒤로가기 버튼으로 메인에 돌아올 수 있도록 하기 위해
        // CommuListActivity만 있는 이유 : 해당 skin만 만들어서 firebase에서 data를 가져와서 skin에 보여줄 것임
        // (skin에다가 data를 보여주는 방법은 DB 담당 팀원이 할 것)
        // CommuListActivity에는 figma에 있는 예시 화면을 구현해줄 것 (모르겠으면 질문)
        
        commu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

        commu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });


        commu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

        commu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });



        ImageButton backBtn = (ImageButton)findViewById(R.id.backBtn);
        ImageButton myBtn = (ImageButton)findViewById(R.id.myBtn);
        ImageButton licenBtn = (ImageButton)findViewById(R.id.licenBtn);
        ImageButton calenBtn = (ImageButton)findViewById(R.id.calenBtn);
        ImageButton homeBtn = (ImageButton)findViewById(R.id.homeBtn);
        ImageButton commBtn = (ImageButton)findViewById(R.id.commBtn);
        ImageButton chalBtn = (ImageButton)findViewById(R.id.chalBtn);



        backBtn.setOnClickListener(new View.OnClickListener() {  //뒤로 돌아가기
            @Override
            public void onClick(View view) {
                setContentView(R.layout.activity_main);
            }
        });

        myBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.activity_main);  //수정페이지 없어서 일단 메인으로 이동
            }
        });

        licenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.activity_license_list);
            }
        });

        calenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.activity_sche);
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {  //메인페이지 버튼
            @Override
            public void onClick(View view) {
                setContentView(R.layout.activity_main);
            }
        });

        commBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.activity_commu);
            }
        });

        chalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.activity_commu);  //문의사항 페이지 없음 새로 생성해야 함  일단 커뮤로 이동
            }
        });
    }
}