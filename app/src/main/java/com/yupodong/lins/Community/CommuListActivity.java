package com.yupodong.lins.Community;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yupodong.lins.Crawler.EIP;
import com.yupodong.lins.DTO.communication;
import com.yupodong.lins.DTO.crawling;
import com.yupodong.lins.License.LicenseActivity;
import com.yupodong.lins.License.LicenseListActivity;
import com.yupodong.lins.MainActivity;
import com.yupodong.lins.R;
import com.yupodong.lins.Scheduler.SchedulerActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommuListActivity extends AppCompatActivity {
    ListView commuview;
    CommuAdapter commuAdapter;
    ArrayList<CommuList> commuListArrayList;
    List<communication> listCommu = new ArrayList<communication>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commu_list);

        //------------------------------- 클릭한 license로 Activity title 바꾸기 -----------------------
        TextView commuTitle = (TextView)findViewById(R.id.commu_title);
        TextView noWritingComment = (TextView)findViewById(R.id.noWritingComment);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        // 이전 액티비티에서 전달한 값을 저장
        String clickLicenseName = bundle.getString("licenseName");

        commuTitle.setText(clickLicenseName);

        //-------------------------------- 글쓰기 버튼을 클릭하면, writeActivity로 이동 ----------------
        Button writeBtn = (Button)findViewById(R.id.writeBtn);
        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommuListActivity.this, WriteActivity.class);
                intent.putExtra("licenseName",clickLicenseName);
                startActivity(intent);
            }
        });

        //---------------------------- 클릭한 license의 게시글 가져오기 (미완성)-------------------------
        commuview=(ListView)findViewById(R.id.commulistview);
        commuListArrayList = new ArrayList<CommuList>();

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = firestore.collection("Commu");
        // data 가져오기
        collectionReference.orderBy("writingID").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    int i = 0;
                    // 현재 Commu에 들어있는 게시글 중에 선택한 자격증에 해당하는 글들만 list에 저장하기
                    for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        listCommu.add(documentSnapshot.toObject(communication.class));
                        i++;
                    }
                    if(i == 0) {
                        noWritingComment.setVisibility(View.VISIBLE);
                    }

                    // 최신 글이 상단에 보이도록 하기위해 reverse
                    Collections.reverse(listCommu);

                    // Adapter로 보내기 위한 작업
                    for(int j = 0; j < listCommu.size(); j++){
                        commuListArrayList.add(
                                new CommuList(listCommu.get(j).getTitle(),listCommu.get(j).getNickName(),"|",listCommu.get(j).getWriteDate(),R.drawable.ic_view, listCommu.get(j).getViewCount(),R.drawable.ic_comment,listCommu.get(j).getCommentCount())
                        );
                    }
                    // ListView에 보여지기 위한 작업
                    commuAdapter=new CommuAdapter(CommuListActivity.this,commuListArrayList);
                    commuview.setAdapter(commuAdapter);

                }
                else {
                    //Toast.makeText(CommuListActivity.this, "해당 게시판의 게시글을 가져오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
                // 지금까지 열려있는 Activity들을 모두 종료
                for(int i = 0; i < actList.size(); i++)
                    actList.get(i).finish();
                setContentView(R.layout.activity_main);  //수정페이지 없어서 일단 메인으로 이동
            }
        });

        licenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommuListActivity.this, LicenseActivity.class);

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
                Intent intent = new Intent(CommuListActivity.this, SchedulerActivity.class);
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
                Intent intent = new Intent(CommuListActivity.this, MainActivity.class);
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
                Intent intent = new Intent(CommuListActivity.this, CommuActivity.class);
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
                /*
                Intent intent = new Intent(CommuListActivity.this, LicenseActivity.class);
                 // 지금까지 열려있는 Activity들을 모두 종료
                for(int i = 0; i < actList.size(); i++)
                    actList.get(i).finish();

                // LicenseActivity로 이동
                startActivity(intent);
                // 현재 Activity 종료 후
                finish();
                 */
            }
        });
    }
    // 액티비티를 다시 시작할 때 ( 뒤로가기를 통해 다시 실행될 때 - 실시간 업데이트를 위해 )

    @Override
    protected void onRestart() {
        super.onRestart();
        TextView noWritingComment = (TextView)findViewById(R.id.noWritingComment);
        commuview=(ListView)findViewById(R.id.commulistview);
        commuListArrayList.clear();


        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = firestore.collection("Commu");
        // data 가져오기
        collectionReference.orderBy("writingID").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    int i = 0;
                    int preListCount = listCommu.size();
                    // 현재 Commu에 들어있는 게시글 중에 선택한 자격증에 해당하는 글들만 list에 저장하기
                    for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        listCommu.add(documentSnapshot.toObject(communication.class));
                        i++;
                    }
                    if(i == 0) {
                        noWritingComment.setVisibility(View.VISIBLE);
                    }
                    else {
                        noWritingComment.setVisibility(View.GONE);
                    }

                    // 최신 글이 상단에 보이도록 하기위해 reverse
                    Collections.reverse(listCommu);

                    // Adapter로 보내기 위한 작업
                    for(int j = preListCount + 1; j < listCommu.size(); j++){
                        commuListArrayList.add(
                                new CommuList(listCommu.get(j).getTitle(),listCommu.get(j).getNickName(),"|",listCommu.get(j).getWriteDate(),R.drawable.ic_view, listCommu.get(j).getViewCount(),R.drawable.ic_comment,listCommu.get(j).getCommentCount())
                        );
                    }
                    // ListView에 보여지기 위한 작업
                    commuAdapter=new CommuAdapter(CommuListActivity.this,commuListArrayList);
                    commuview.setAdapter(commuAdapter);

                }
                else {
                    //Toast.makeText(CommuListActivity.this, "해당 게시판의 게시글을 가져오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}