package com.yupodong.lins.Community;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yupodong.lins.DTO.comment;
import com.yupodong.lins.DTO.commuScrap;
import com.yupodong.lins.DTO.communication;
import com.yupodong.lins.DTO.user;
import com.yupodong.lins.License.LicenseActivity;
import com.yupodong.lins.MainActivity;
import com.yupodong.lins.Mypage.MyPage;
import com.yupodong.lins.Qna.QnaActivity;
import com.yupodong.lins.R;
import com.yupodong.lins.Scheduler.SchedulerActivity;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReadActivity extends AppCompatActivity {
    private communication readWriting = new communication();
    user user = new user();
    comment comment = new comment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commu_read);
        //------------------------------------ CommuList의 Adapter에서 전달한 값을 넘겨받음 -------------------
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String writingCate = bundle.getString("category");
        Integer writingID = bundle.getInt("writingID");

        //------------------------------------- 클릭한 commulist의 내용을 가져와서 보여줌----------------------
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        TextView nickName = (TextView)findViewById(R.id.nickName);
        TextView writingDate = (TextView)findViewById(R.id.writingDate);
        TextView title = (TextView)findViewById(R.id.title);
        TextView viewCount = (TextView)findViewById(R.id.viewCount);
        TextView commentCount= (TextView)findViewById(R.id.commentCount);
        ImageView scrapBtn = (ImageView) findViewById(R.id.scrapBtn);
        TextView scrapCount = (TextView)findViewById(R.id.scrapCount);
        TextView content = (TextView)findViewById(R.id.content);

        firestore.collection("Commu").whereEqualTo("writingID", writingID).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            // writingID에 해당하는 게시글을 object 형태로 가져옴
                            QuerySnapshot document = task.getResult();
                            readWriting = task.getResult().toObjects(communication.class).get(0);

                            nickName.setText(readWriting.getNickName());
                            writingDate.setText(readWriting.getWriteDate());
                            title.setText(readWriting.getTitle());

                            viewCount.setText(readWriting.getViewCount().toString());
                            commentCount.setText(readWriting.getCommentCount().toString());
                            scrapCount.setText(readWriting.getScrapCount().toString());
                            content.setText(readWriting.getContent());
                        }
                        else {
                            Toast.makeText(ReadActivity.this, "글을 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
        
        //------------------------ scrap 버튼을 눌렀을 때, scrap에 추가 및 count 증가 -------------------------
        scrapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                Integer scrapNum = Integer.parseInt((String) scrapCount.getText());
                // 로그인된 사용자일 때
                if(currentUser != null) {
                    // Firebase로 넘길 data 생성
                    commuScrap scrap = new commuScrap();
                    scrap.setWritingCate(writingCate);
                    scrap.setWritingID(writingID);
                    scrap.setUserID(currentUser.getEmail());

                    // scrap에 추가한 거라면
                    if (firestore.collection("Commu").whereEqualTo("writingID", writingID).get().getResult() == null) {
                        // 사용자에게 보여지는 scrapCount 수정
                        scrapCount.setText(Integer.toString(scrapNum + 1));
                        scrapBtn.setColorFilter(Color.parseColor("#E9C87B"));

                        // 클릭한 commuWriting의 ID와 현재 로그인중인 사용자의 id를 commuScrap에 저장
                        firestore.collection("CommuScrap").document(Integer.toString(scrap.getWritingID())).set(scrap)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // 해당 writingID의 scrapCount를 update
                                        firestore.collection("Commu").document(Integer.toString(scrap.getWritingID()))
                                                .update("scrapCount", scrapNum+1)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Toast.makeText(ReadActivity.this, "해당 게시글이 스크랩에 저장되었습니다.", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                        });
                    }
                    // scrap을 취소한 거라면 (이미 중복된 데이터가 들어갔다고 생각하고)
                    else if (firestore.collection("Commu").whereEqualTo("writingID", writingID).get().getResult() != null){
                        // 사용자에게 보여지는 scrapCount 수정
                        scrapCount.setText(Integer.toString(scrapNum - 1));
                        scrapBtn.setColorFilter(Color.parseColor("#9D9D9D"));
                        // commuScrap에서 해당 writingID를 찾아서 삭제
                        firestore.collection("CommuScrap").document(Integer.toString(scrap.getWritingID()))
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // Commu에서 해당 writingID의 scrapCount--
                                        firestore.collection("Commu").document(Integer.toString(scrap.getWritingID()))
                                                .update("scrapCount", scrapNum-1)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(ReadActivity.this, "해당 게시글을 스크랩에서 삭제했습니다.", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                });
                    }
                }
                // 로그인이 안된 사용자라면,
                else {
                    // Toast message로 로그인 후 이용해달라는 메세지 출력
                    Toast.makeText(ReadActivity.this, "로그인을 하셔야 스크랩이 가능합니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //---------------------------------------- 해당 게시글에 달린 댓글을 보여줌 ----------------------------------------
        LinearLayout commentSection = (LinearLayout)findViewById(R.id.commentSection);
        List<comment> commentList = new ArrayList<comment>();

        firestore.collection("Comment").whereEqualTo("writingCate", writingCate).whereEqualTo("writingID", writingID).orderBy("writingID")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()) {
                        // 해당 게시글ID과 Cate과 동일한 comment list를 가져옴
                        for(QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            commentList.add(documentSnapshot.toObject(comment.class));

                            // 새로운 댓글란 생성 및 추가
                            commentActivity n_layout = new commentActivity(getApplicationContext());
                            commentSection.addView(n_layout);
                            // 새로운 ID 부여
                            n_layout.setId(commentList.size()-1);

                            // 댓글 내용 채우기
                            TextView commentWriter = (TextView)n_layout.findViewById(R.id.commentWriter);
                            TextView commentDate = (TextView)n_layout.findViewById(R.id.commentDate);
                            TextView commentContent = (TextView)n_layout.findViewById(R.id.commentContent);

                            commentWriter.setText(commentList.get(commentList.size()-1).getNickName());
                            commentDate.setText(commentList.get(commentList.size()-1).getCommentDate());
                            commentContent.setText(commentList.get(commentList.size()-1).getContent());
                        }

                    }
                }
            });

        //--------------------------- 댓글 작성 버튼 listener 생성 -----------------------------------------------------
        EditText editConmment = (EditText)findViewById(R.id.editConmment);
        TextView commentSendBtn = (TextView)findViewById(R.id.commentSendBtn);

        commentSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                // 만약, 사용자가 댓글 입력란에 아무것도 작성하지 않았다면
                if(editConmment.getText().toString() == "") {
                    Toast.makeText(ReadActivity.this, "댓글을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                // 사용자가 댓글 입력란에 내용을 작성
                else {
                    // 현재 로그인되어있는 사용자의 nickName 구하기
                    firestore.collection("User").whereEqualTo("id", currentUser.getEmail()).get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                    // nickName을 알아냈다면
                                    if(task.isSuccessful()) {
                                        //닉네임을 저장
                                        user = task.getResult().toObjects(user.class).get(0);
                                        
                                        // 현재 들어있는 comment의 개수 구하기 (commentID를 구하기 위해)
                                        firestore.collection("Comment").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                // Comment에 있는 field들을 가져왔다면
                                                if(task.isSuccessful()) {
                                                    Integer Count = 0;

                                                    for(QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                                        Count++;
                                                    }

                                                    // 현재 시간 구하기 (writeDate)
                                                    SimpleDateFormat format1 = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
                                                    Date time = new Date();
                                                    String time1 = format1.format(time);

                                                    comment.setWritingID(writingID);
                                                    comment.setWritingCate(writingCate);
                                                    comment.setNickName(user.getNickName());
                                                    comment.setContent(editConmment.getText().toString());
                                                    comment.setCommentID(Count+1);
                                                    comment.setCommentDate(time1);

                                                    // firebase에 저장하기
                                                    firestore.collection("Comment").document(Integer.toString(Count+1)).set(comment)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                // 해당 게시글의 commentCount의 숫자를 증가 & firebase에 update
                                                                firestore.collection("Commu").document(Integer.toString(writingID))
                                                                        .update("commentCount", Integer.parseInt(commentCount.getText().toString())+1)
                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                commentCount.setText(Integer.toString(Integer.parseInt(commentCount.getText().toString())+1));

                                                                                // 새로운 댓글란 추가
                                                                                commentActivity n_layout = new commentActivity(getApplicationContext());
                                                                                commentSection.addView(n_layout);

                                                                                // 댓글 내용 채우기
                                                                                TextView commentWriter = (TextView)findViewById(R.id.commentWriter);
                                                                                TextView commentDate = (TextView)findViewById(R.id.commentDate);
                                                                                TextView commentContent = (TextView)findViewById(R.id.commentContent);

                                                                                commentWriter.setText(user.getNickName());
                                                                                commentDate.setText(time1);
                                                                                commentContent.setText(editConmment.getText().toString());

                                                                                Toast.makeText(ReadActivity.this, "댓글 전송 완료", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        });
                                                            }
                                                        });
                                                }
                                            }
                                        });
                                    }
                                }
                            });

                }
            }
        });

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
                Intent intent = new Intent(ReadActivity.this, MyPage.class);

                // 지금까지 열려있는 Activity들을 모두 종료
                for(int i = 0; i < actList.size(); i++)
                    actList.get(i).finish();

                // 현재 Activity 종료 후
                finish();
                // LicenseActivity로 이동
                startActivity(intent);
            }
        });

        licenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReadActivity.this, LicenseActivity.class);

                // 지금까지 열려있는 Activity들을 모두 종료
                for(int i = 0; i < actList.size(); i++)
                    actList.get(i).finish();

                // 현재 Activity 종료 후
                finish();
                // LicenseActivity로 이동
                startActivity(intent);
            }
        });

        calenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReadActivity.this, SchedulerActivity.class);
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
                Intent intent = new Intent(ReadActivity.this, MainActivity.class);
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
                Intent intent = new Intent(ReadActivity.this, CommuActivity.class);
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
                Intent intent = new Intent(ReadActivity.this, QnaActivity.class);
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