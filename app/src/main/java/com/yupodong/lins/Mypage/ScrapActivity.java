package com.yupodong.lins.Mypage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yupodong.lins.Community.CommuActivity;
import com.yupodong.lins.DTO.commuScrap;
import com.yupodong.lins.DTO.communication;
import com.yupodong.lins.DTO.license;
import com.yupodong.lins.DTO.licenseScrap;
import com.yupodong.lins.License.LicenseActivity;
import com.yupodong.lins.MainActivity;
import com.yupodong.lins.R;
import com.yupodong.lins.Scheduler.SchedulerActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

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
        //------------------------------------ My page에서 넘긴 값 받기 ----------------------------------
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        // 이전 액티비티에서 전달한 값을 저장
        String clickScrapType = bundle.getString("scrapType");

        //------------------------------------- 클릭한 카테고리에 해당하는 스크랩 정보 가져오기
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if(clickScrapType == "license") {
            // 1차적으로 해당 사용자가 스크랩한 정보를 저장하는 용도
            List<license> resultScrap = new ArrayList<license>();

            // 해당 사용자의 scrap에 있는 모든 스크랩 가져오기 (ID 순으로)
            firestore.collection("Scrap").whereEqualTo("nickName", currentUser.getEmail()).orderBy("licenseID")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                licenseScrap temp = documentSnapshot.toObject(licenseScrap.class);

                                // 각 license에 해당하는 ArrayList에 ID값 저장
                                if(temp.getLicenseName() == "TOEIC") {
                                    resultScrap.add(
                                            firestore.collection("TOEIC").whereEqualTo("licenseID", temp.getLicenseID())
                                            .get().getResult().toObjects(license.class).get(0)
                                    );
                                }
                                else if(temp.getLicenseName() == "TOPCIT") {
                                    resultScrap.add(
                                            firestore.collection("TOPCIT").whereEqualTo("licenseID", temp.getLicenseID())
                                                    .get().getResult().toObjects(license.class).get(0)
                                    );
                                }
                                else if(temp.getLicenseName() == "EIP") {
                                    resultScrap.add(
                                            firestore.collection("EIP").whereEqualTo("licenseID", temp.getLicenseID())
                                                    .get().getResult().toObjects(license.class).get(0)
                                    );
                                }
                            }
                            // 가져온 list들을 listView의 ArrayList에 저장
                            for(int i = 0; i < resultScrap.size(); i++){
                                scrapListArrayList.add(
                                        new ScrapList(resultScrap.get(i).getLicenseDate(),resultScrap.get(i).getLicenseName(),
                                                R.drawable.ic_star, "", resultScrap.get(i).getLicenseID())
                                );
                            }

                            // ListView에 나타내기 위한 작업
                            scrapAdapter = new ScrapAdapter(ScrapActivity.this,scrapListArrayList);
                            scrapview.setAdapter(scrapAdapter);
                        }
                    });
        }
        else if (clickScrapType == "commu") {
            // 1차적으로 해당 사용자가 스크랩한 정보를 저장하는 용도
            List<communication> resultScrap = new ArrayList<communication>();

            // 해당 사용자의 scrap에 있는 모든 스크랩 가져오기 (ID 순으로)
            firestore.collection("CommuScrap").whereEqualTo("userID", currentUser.getEmail()).orderBy("writingID")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                commuScrap temp = documentSnapshot.toObject(commuScrap.class);
                                
                                // 해당하는 ID를 가지는 커뮤니티 게시글의 정보를 저장
                                resultScrap.add(
                                        firestore.collection("Commu").whereEqualTo("writingID", temp.getWritingID())
                                                .get().getResult().toObjects(communication.class).get(0)
                                );
                            }

                            // 가져온 list들을 listView의 ArrayList에 저장
                            for(int i = 0; i < resultScrap.size(); i++){
                                // 커뮤니티 작성 날짜 중에서 시간을 표시해주는 것을 제거
                                String writingDate = resultScrap.get(i).getWriteDate();
                                Integer splitEndIndex = writingDate.indexOf(" ");

                                writingDate.substring(0, splitEndIndex-1);
                                resultScrap.get(i).setWriteDate(writingDate);

                                scrapListArrayList.add(
                                        new ScrapList(resultScrap.get(i).getTitle(),resultScrap.get(i).getCategory(),
                                                0, resultScrap.get(i).getNickName() + " | " + resultScrap.get(i).getWriteDate()
                                                , resultScrap.get(i).getWritingID())
                                );
                            }

                            // ListView에 나타내기 위한 작업
                            scrapAdapter = new ScrapAdapter(ScrapActivity.this,scrapListArrayList);
                            scrapview.setAdapter(scrapAdapter);
                        }
                    });
        }


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
