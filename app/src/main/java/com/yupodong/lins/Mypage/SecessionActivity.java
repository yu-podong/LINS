package com.yupodong.lins.Mypage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yupodong.lins.Community.CommuActivity;
import com.yupodong.lins.DTO.user;
import com.yupodong.lins.License.LicenseActivity;
import com.yupodong.lins.MainActivity;
import com.yupodong.lins.R;
import com.yupodong.lins.Scheduler.SchedulerActivity;

import java.util.ArrayList;

public class SecessionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secession);

        //------------------------------ 회원탈퇴 과정 -------------------------------------
        EditText secession_pw = (EditText)findViewById(R.id.secession_pw);
        Button secession_Btn = (Button)findViewById(R.id.secession_Btn);

        secession_Btn.setOnClickListener(new View.OnClickListener() {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseFirestore firestore = FirebaseFirestore.getInstance();

            @Override
            public void onClick(View view) {
                // 비밀번호를 입력하지 않았다면
                if (secession_pw.getText().toString() == null) {
                    Toast.makeText(SecessionActivity.this, "현재 로그인한 계정의 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                // 비밀번호를 입력했다면,
                else {
                    // 현재 사용자의 비밀번호를 가져옴
                    firestore.collection("User").document(currentUser.getEmail().toString())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                user user = task.getResult().toObject(user.class);

                                // 입력한 비밀번호가 현재 사용자의 비밀번호와 동일하다면
                                if(secession_pw.getText().toString().equals(user.getPassword())) {
                                    // 해당 사용자의 정보를 삭제
                                    firestore.collection("User").document(currentUser.getEmail().toString())
                                            .delete()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(SecessionActivity.this, "회원탈퇴가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                                // 동일하지 않다면
                                else {
                                    Toast.makeText(SecessionActivity.this, "올바른 비밀번호가 아닙니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                }

                // error 메세지 출력
                // 올바른 비밀번호를 입력했다면
            }
        });
        //----------------------------- 상단 및 하단 버튼들 clickListener 등록--------------------------------
        // 상단 버튼
        ImageButton backBtn = (ImageButton) findViewById(R.id.backBtn);
        ImageButton myBtn = (ImageButton) findViewById(R.id.myBtn);
        // 하단 이미지 버튼
        ImageButton licenBtn = (ImageButton) findViewById(R.id.licenBtn);
        ImageButton calenBtn = (ImageButton) findViewById(R.id.calenBtn);
        ImageButton homeBtn = (ImageButton) findViewById(R.id.homeBtn);
        ImageButton commBtn = (ImageButton) findViewById(R.id.commBtn);

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

        licenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecessionActivity.this, LicenseActivity.class);

                // 지금까지 열려있는 Activity들을 모두 종료
                for (int i = 0; i < actList.size(); i++)
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
                Intent intent = new Intent(SecessionActivity.this, SchedulerActivity.class);
                // 지금까지 열려있는 Activity들을 모두 종료
                for (int i = 0; i < actList.size(); i++)
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
                Intent intent = new Intent(SecessionActivity.this, MainActivity.class);
                // 지금까지 열려있는 Activity들을 모두 종료
                for (int i = 0; i < actList.size(); i++)
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
                Intent intent = new Intent(SecessionActivity.this, CommuActivity.class);
                // 지금까지 열려있는 Activity들을 모두 종료
                for (int i = 0; i < actList.size(); i++)
                    actList.get(i).finish();

                // CommuActivity로 이동
                startActivity(intent);
                // 현재 Activity 종료 후
                finish();
            }
        });
    }
}
