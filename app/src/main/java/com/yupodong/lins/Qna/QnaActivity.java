package com.yupodong.lins.Qna;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
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
import com.yupodong.lins.Community.CommuListActivity;
import com.yupodong.lins.Community.WriteActivity;
import com.yupodong.lins.DTO.communication;
import com.yupodong.lins.DTO.question;
import com.yupodong.lins.DTO.user;
import com.yupodong.lins.License.LicenseActivity;
import com.yupodong.lins.MainActivity;
import com.yupodong.lins.Mypage.MyPage;
import com.yupodong.lins.R;
import com.yupodong.lins.Scheduler.SchedulerActivity;

import java.util.ArrayList;
import java.util.List;

public class QnaActivity extends AppCompatActivity {
    ListView qnaview;
    QnaAdapter qnaAdapter;
    ArrayList<QnaList> qnaListArrayList;

    List<question> listQuestion = new ArrayList<question>();
    user user = new user();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qna);

        //---------------------문의 작성하기 버튼을 클릭하면 글쓰는 페이지로 이동 ---------------------
        Button writingBtn = (Button)findViewById(R.id.writeBtn);

        writingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QnaActivity.this, QnaWriteActivity.class);
                startActivity(intent);
            }
        });

        //------------------------------- 해당 사용자가 작성한 문의글 가져오기 -----------------------
        qnaview=(ListView)findViewById(R.id.qnalistview);
        qnaListArrayList = new ArrayList<QnaList>();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();


        if(currentUser != null) {
            // User에서 현재 로그인한 사용자의 닉네임 찾기
            firestore.collection("User").whereEqualTo("id", currentUser.getEmail())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            // 찾은 user의 object를 저장하는 과정
                            QuerySnapshot document = task.getResult();
                            user = document.toObjects(user.class).get(0);

                            // Question에 들어있는 QID를 기준으로 정렬
                            firestore.collection("Question").orderBy("qid").get()
                                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        // 해당 닉네임이 작성한 문의사항의 개수 count
                                        firestore.collection("Question").whereEqualTo("nickName", user.getNickName())
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    Integer listCount = 0;
                                                    // 해당 닉네임이 작성한 문의사항들을 question에 저장 (List 형태로)
                                                    for(QueryDocumentSnapshot document : task.getResult()) {
                                                        listQuestion.add(document.toObject(question.class));
                                                        listCount++;
                                                    }
                                                    if(listCount == 0) {
                                                        // 추후에 작성된 글이 없다는 것을 띄울 것
                                                    }

                                                    // ListView에 보여지기 위한 작업
                                                    for(int i = 0; i < listCount; i++) {
                                                        qnaListArrayList.add(
                                                                new QnaList(listQuestion.get(i).getQID(), listQuestion.get(i).getTitle(),listQuestion.get(i).getQuesDate(),
                                                                        R.drawable.ic_comment,listQuestion.get(i).getCommentCount())
                                                        );
                                                    }

                                                    // Adapter로 보내기 위한 작업 진행
                                                    qnaAdapter = new QnaAdapter(QnaActivity.this,qnaListArrayList);
                                                    qnaview.setAdapter(qnaAdapter);
                                                }
                                            });
                                    }
                                });
                        }
                    });
        }
        else {
            // 로그인 후에 이용하라는 메세지 호출
            Toast.makeText(this, "로그인을 한 후에 이용하실 수 있습니다.", Toast.LENGTH_SHORT).show();
            // 현재 Activity 종료
            finish();
        }

        //--------------------------------------- 하단 및 상단 버튼 listener----------------------------------
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
                Intent intent = new Intent(QnaActivity.this, MyPage.class);

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
                Intent intent = new Intent(QnaActivity.this, LicenseActivity.class);

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
                Intent intent = new Intent(QnaActivity.this, SchedulerActivity.class);
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
                Intent intent = new Intent(QnaActivity.this, MainActivity.class);
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
                Intent intent = new Intent(QnaActivity.this, CommuActivity.class);
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
                Intent intent = new Intent(QnaActivity.this, QnaActivity.class);
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
    // 액티비티를 다시 시작할 때 ( 뒤로가기를 통해 다시 실행될 때 - 실시간 업데이트를 위해 )
    @Override
    protected void onRestart() {
        super.onRestart();
        qnaview=(ListView)findViewById(R.id.qnalistview);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        if(currentUser != null) {
            // User에서 현재 로그인한 사용자의 닉네임 찾기
            firestore.collection("User").whereEqualTo("id", currentUser.getEmail())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            // 찾은 user의 object를 저장하는 과정
                            QuerySnapshot document = task.getResult();
                            user = document.toObjects(user.class).get(0);

                            // Question에 들어있는 QID를 기준으로 정렬
                            firestore.collection("Question").orderBy("qid").get()
                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            // 해당 닉네임이 작성한 문의사항의 개수 count
                                            firestore.collection("Question").whereGreaterThan("qid", listQuestion.get(listQuestion.size()-1).getQID())
                                                    .whereEqualTo("nickName", user.getNickName())
                                                    .get()
                                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                            Integer listCount = 0;
                                                            listQuestion.clear();

                                                            // 해당 닉네임이 작성한 문의사항들을 question에 저장 (List 형태로)
                                                            for(QueryDocumentSnapshot document : task.getResult()) {
                                                                listQuestion.add(document.toObject(question.class));
                                                                listCount++;
                                                            }

                                                            // ListView에 보여지기 위한 작업
                                                            for(int i = 0; i < listCount; i++) {
                                                                qnaListArrayList.add(
                                                                        new QnaList(listQuestion.get(i).getQID(), listQuestion.get(i).getTitle(),listQuestion.get(i).getQuesDate(),
                                                                                R.drawable.ic_comment,listQuestion.get(i).getCommentCount())
                                                                );
                                                            }

                                                            // Adapter로 보내기 위한 작업 진행
                                                            qnaAdapter = new QnaAdapter(QnaActivity.this,qnaListArrayList);
                                                            qnaview.setAdapter(qnaAdapter);
                                                        }
                                                    });
                                        }
                                    });
                        }
                    });
        }
        else {
            // 로그인 후에 이용하라는 메세지 호출
            Toast.makeText(this, "로그인을 한 후에 이용하실 수 있습니다.", Toast.LENGTH_SHORT).show();
            // 현재 Activity 종료
            finish();
        }
    }
}

