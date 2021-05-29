package com.yupodong.lins.Mypage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
import com.google.firebase.firestore.QuerySnapshot;
import com.yupodong.lins.Community.CommuActivity;
import com.yupodong.lins.DTO.user;
import com.yupodong.lins.License.LicenseActivity;
import com.yupodong.lins.MainActivity;
import com.yupodong.lins.R;
import com.yupodong.lins.Scheduler.SchedulerActivity;

import java.util.ArrayList;

public class ChangeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        // 지금까지 열려있는 Activity 저장하는 ArrayList
        ArrayList<Activity> actList = new ArrayList<Activity>();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        // 이전 액티비티에서 전달한 값을 저장
        String clickChangeData = bundle.getString("changeData");

        LinearLayout changePasswordLayout = (LinearLayout)findViewById(R.id.changePasswordLayout);
        LinearLayout changeEmailLayout = (LinearLayout)findViewById(R.id.changeEmailLayout);

        //----------------------------------- 비밀번호 변경을 클릭한 경우 ------------------------------------
        if(clickChangeData == "password") {
            changePasswordLayout.setVisibility(View.VISIBLE);
            changeEmailLayout.setVisibility(View.GONE);

            // 새로운 비밀번호를 입력하는 EditText 가져오기
            EditText c_pw = (EditText)findViewById(R.id.c_pw);
            EditText c_check_pw = (EditText)findViewById(R.id.c_check_pw);
            // 비밀번호를 변경할 버튼 가져오기
            Button changeBtn = (Button)findViewById(R.id.changeBtn);

            // 비밀번호 변경 버튼의 listener 등록
            changeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 만약, 두 EditText를 모두 입력하지 않았다면
                    if(c_pw.getText().toString() == null || c_check_pw.getText().toString() == null) {
                        Toast.makeText(ChangeActivity.this, "모든 입력칸을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    // 둘 다 입력했다면
                    else {
                        // 2개의 EditText에 들어있는 비밀번호가 같은지 비교
                        if( c_pw.getText().toString() == c_check_pw.getText().toString()) {
                            FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                            // 같다면, 해당 사용자의 비밀번호 변경
                            firestore.collection("User").document(currentUser.getEmail().toString())
                                    .update("password", c_pw.getText().toString())
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Intent intent = new Intent(ChangeActivity.this, MainActivity.class);

                                            Toast.makeText(ChangeActivity.this, "비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT).show();

                                            // 지금까지 열려있는 Activity들을 모두 종료
                                            for (int i = 0; i < actList.size(); i++)
                                                actList.get(i).finish();

                                            // MainActivity로 이동
                                            startActivity(intent);
                                            // 현재 Activity 종료 후
                                            finish();
                                        }
                                    });
                        }
                        // 다르다면, error Toast 띄우기
                        else {
                            Toast.makeText(ChangeActivity.this, "비밀번호를 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
        //-----------------------------------이메일 변경을 클릭한 경우 ---------------------------------------
        else if (clickChangeData == "email") {
            changePasswordLayout.setVisibility(View.GONE);
            changeEmailLayout.setVisibility(View.VISIBLE);

            // 새로운 이메일을 입력하는 EditText 가져오기
            EditText c_email = (EditText)findViewById(R.id.c_email);
            // 비밀번호를 변경할 버튼 가져오기
            Button change_emailBtn = (Button)findViewById(R.id.change_emailBtn);

            // 비밀번호 변경 버튼의 listener 등록
            change_emailBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 만약, 이메일을 입력하지 않았다면
                    if(c_email.getText().toString() == null) {
                        Toast.makeText(ChangeActivity.this, "새로운 이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    // 이메일을 입력했다면
                    else {
                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                        // 입력한 이메일이 새로운 이메일이 아니면
                        if( c_email.getText().toString() == currentUser.getEmail().toString()) {
                            Toast.makeText(ChangeActivity.this, "이미 해당 이메일은 사용중입니다.", Toast.LENGTH_SHORT).show();
                        }
                        // 입력한 이메일이 새로운 이메일이라면,
                        else {
                            FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                            
                            // 현재 사용자의 기존 이메일과 관련된 사용자 정보를 가져옴
                            firestore.collection("User").document(currentUser.getEmail().toString())
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            // user class에 저장
                                            user user = task.getResult().toObject(user.class);

                                            // 해당 사용자정보의 document name 업데이트
                                            firestore.collection("User").document(c_email.getText().toString())
                                                    .set(user)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            // 새롭게 수정된 document에 사용자가 입력한 e-mail로 update
                                                            firestore.collection("User").document(c_email.getText().toString())
                                                                    .update("id", c_email.getText().toString())
                                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                        @Override
                                                                        public void onSuccess(Void aVoid) {
                                                                            Intent intent = new Intent(ChangeActivity.this, MainActivity.class);

                                                                            Toast.makeText(ChangeActivity.this, "E-mail이 변경되었습니다.", Toast.LENGTH_SHORT).show();
                                                                            // 지금까지 열려있는 Activity들을 모두 종료
                                                                            for (int i = 0; i < actList.size(); i++)
                                                                                actList.get(i).finish();

                                                                            // MainActivity로 이동
                                                                            startActivity(intent);
                                                                            // 현재 Activity 종료 후
                                                                            finish();
                                                                        }
                                                                    });
                                                        }
                                                    });
                                        }
                                    });
                        }
                    }
                }
            });
        }

        //----------------------------- 상단 및 하단 버튼들 clickListener 등록--------------------------------
        // 상단 버튼
        ImageButton backBtn = (ImageButton) findViewById(R.id.backBtn);
        ImageButton myBtn = (ImageButton) findViewById(R.id.myBtn);
        // 하단 이미지 버튼
        ImageButton licenBtn = (ImageButton) findViewById(R.id.licenBtn);
        ImageButton calenBtn = (ImageButton) findViewById(R.id.calenBtn);
        ImageButton homeBtn = (ImageButton) findViewById(R.id.homeBtn);
        ImageButton commBtn = (ImageButton) findViewById(R.id.commBtn);



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
                Intent intent = new Intent(ChangeActivity.this, MyPage.class);

                // 지금까지 열려있는 Activity들을 모두 종료
                for (int i = 0; i < actList.size(); i++)
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
                Intent intent = new Intent(ChangeActivity.this, LicenseActivity.class);

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
                Intent intent = new Intent(ChangeActivity.this, SchedulerActivity.class);
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
                Intent intent = new Intent(ChangeActivity.this, MainActivity.class);
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
                Intent intent = new Intent(ChangeActivity.this, CommuActivity.class);
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
