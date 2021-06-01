package com.yupodong.lins.Qna;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yupodong.lins.Community.CommuActivity;
import com.yupodong.lins.Community.WriteActivity;
import com.yupodong.lins.DTO.question;
import com.yupodong.lins.DTO.user;
import com.yupodong.lins.License.LicenseActivity;
import com.yupodong.lins.MainActivity;
import com.yupodong.lins.Mypage.MyPage;
import com.yupodong.lins.R;
import com.yupodong.lins.Scheduler.SchedulerActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class QnaWriteActivity extends AppCompatActivity {
    Integer listCount = 0;
    user current = new user();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commu_write);

        //----------------------------- 작성한 글을 firebase에 저장하는 부분 -----------------------------
        EditText title = (EditText)findViewById(R.id.title);
        EditText content = (EditText)findViewById(R.id.content);
        Button sendBtn = (Button)findViewById(R.id.sendBtn);

        // 글쓰기 버튼 clickListener 등록
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 입력한 제목과 내용을 가져옴
                String inputTitle = title.getText().toString();
                String inputContent = content.getText().toString();

                // 입력하지 않은 내용이 있을 때
                if (inputContent.equals("") || inputTitle.equals("")) {
                    Toast.makeText(QnaWriteActivity.this, "제목 또는 내용이 작성되지 않았습니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    question listQues = new question();

                    // 현재 로그인되어있는 사용자의 nickName 구하기
                    firestore.collection("User").whereEqualTo("id", currentUser.getEmail())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    // 해당 id를 사용하는 사용자의 닉네임을 찾았다면
                                    if(task.isSuccessful()) {
                                        // 닉네임을 저장
                                        for(QueryDocumentSnapshot queryDocumentSnapshot:task.getResult()){
                                            current = queryDocumentSnapshot.toObject(user.class);
                                            break;
                                        }
                                        // 현재 들어있는 커뮤니티 개수 구하기 (writing ID를 부여하기 위해 만들었는데 error 발생 - 수정중!)
                                        firestore.collection("Question").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                                // Commu에 있는 field들을 가져왔다면
                                                if (task.isSuccessful()) {
                                                    // 각각의 field를 가져오면서 count 증가
                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                        listCount++;
                                                    }

                                                    // 현재 시간 구하기 (writeDate)
                                                    SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
                                                    Date time = new Date();
                                                    String time1 = format1.format(time);

                                                    // 필요한 내용을 DTO class에 삽입
                                                    listQues.setQID(listCount+1);
                                                    listQues.setTitle(inputTitle);
                                                    listQues.setNickName(current.getNickName());
                                                    listQues.setQuesDate(time1);
                                                    listQues.setContent(inputContent);
                                                    listQues.setCommentCount(0);

                                                    // firebase에 저장하기
                                                    firestore.collection("Question").document(Integer.toString(listCount+1)).set(listQues)
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    Toast.makeText(QnaWriteActivity.this, "작성된 글이 업로드되었습니다.", Toast.LENGTH_SHORT).show();
                                                                    finish();
                                                                }
                                                            });
                                                } else {
                                                    // do something..
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                    // Question에 있는 data 개수 세기
                    //
                }
            }
        });

        //--------------------------- 바로가기 버튼 및 상단 버튼 clickListener 등록--------------------------------------
        ImageButton backBtn = (ImageButton)findViewById(R.id.backBtn);
        ImageButton myBtn = (ImageButton)findViewById(R.id.myBtn);
        ImageButton licenBtn = (ImageButton)findViewById(R.id.licenBtn);
        ImageButton calenBtn = (ImageButton)findViewById(R.id.calenBtn);
        ImageButton homeBtn = (ImageButton)findViewById(R.id.homeBtn);
        ImageButton commBtn = (ImageButton)findViewById(R.id.commBtn);
        ImageButton chalBtn = (ImageButton)findViewById(R.id.chalBtn);

        // 지금까지 열러있는 Activities들을 List로 가져옴
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
                Intent intent = new Intent(QnaWriteActivity.this, MyPage.class);

                // 지금까지 열려있는 Activity들을 모두 종료
                for(int i = 0; i < actList.size(); i++)
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
                Intent intent = new Intent(QnaWriteActivity.this, LicenseActivity.class);

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
                Intent intent = new Intent(QnaWriteActivity.this, SchedulerActivity.class);
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
                Intent intent = new Intent(QnaWriteActivity.this, MainActivity.class);
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
                Intent intent = new Intent(QnaWriteActivity.this, CommuActivity.class);
                // 지금까지 열려있는 Activity들을 모두 종료
                for(int i = 0; i < actList.size(); i++)
                    actList.get(i).finish();

                // CommuActivity로 이동
                startActivity(intent);
                // 현재 Activity 종료 후
                finish();
            }
        });

        chalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(QnaWriteActivity.this, QnaActivity.class);
                // 지금까지 열려있는 Activity들을 모두 종료
                for(int i = 0; i < actList.size(); i++)
                    actList.get(i).finish();

                // LicenseActivity로 이동
                startActivity(intent);
                // 현재 Activity 종료 후
                finish();

            }
        });
    }
}