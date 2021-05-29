package com.yupodong.lins.Mypage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.yupodong.lins.Community.CommuActivity;
import com.yupodong.lins.Community.CommuListActivity;
import com.yupodong.lins.DTO.user;
import com.yupodong.lins.License.LicenseActivity;
import com.yupodong.lins.MainActivity;
import com.yupodong.lins.Qna.QnaActivity;
import com.yupodong.lins.R;
import com.yupodong.lins.Scheduler.SchedulerActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);
        //---------------------------------- My page에서 액티비티의 이동이 필요한 listener------------
        TextView pwFindtxt = (TextView) findViewById(R.id.pwFindtxt);
        TextView emailFindtxt = (TextView)findViewById(R.id.emailFindtxt);
        TextView examtxt = (TextView)findViewById((R.id.examtxt));
        TextView commutxt = (TextView)findViewById(R.id.commutxt);
        TextView writecommtxt = (TextView)findViewById(R.id.writecommtxt);
        TextView secessiontxt = (TextView)findViewById(R.id.secessiontxt);

        pwFindtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyPage.this, ChangeActivity.class);
                intent.putExtra("changeData", "password");

                startActivity(intent);
            }
        });

        emailFindtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyPage.this, ChangeActivity.class);
                intent.putExtra("changeData", "email");

                startActivity(intent);
            }
        });

        examtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyPage.this, ScrapActivity.class);
                intent.putExtra("scrapType", "license");

                startActivity(intent);
            }
        });

        commutxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyPage.this, ChangeActivity.class);
                intent.putExtra("scrapType", "commu");

                startActivity(intent);
            }
        });

        writecommtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyPage.this, ChangeActivity.class);
                startActivity(intent);
            }
        });

        secessiontxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyPage.this, ChangeActivity.class);
                startActivity(intent);
            }
        });

        //------------------------------- my page에 사용자의 정보 표시 -----------------------------------
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        TextView nicknametxt = (TextView)findViewById(R.id.nicknametxt);
        TextView nametxt = (TextView)findViewById(R.id.nametxt);

        // 현재 이용자가 로그인을 한 경우
        if(currentUser != null) {
            firestore.collection("User").whereEqualTo("id", currentUser.getEmail())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            user user = task.getResult().toObjects(user.class).get(0);

                            nicknametxt.setText(user.getNickName());
                            nametxt.setText(user.getName() + " | " + user.getId());
                        }
                    });
        }
        // 현재 이용자가 로그인을 하지 않을 경우
        else {
            Toast.makeText(this, "로그인 후에 이용하실 수 있습니다.", Toast.LENGTH_SHORT).show();
        }

        //---------------------------------- 상단 및 하단 버튼들 lisetener --------------------------
        ImageButton backBtn = (ImageButton)findViewById(R.id.backBtn);
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


        licenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyPage.this, LicenseActivity.class);

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
                Intent intent = new Intent(MyPage.this, SchedulerActivity.class);
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
                Intent intent = new Intent(MyPage.this, MainActivity.class);
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
                Intent intent = new Intent(MyPage.this, CommuActivity.class);
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
                Intent intent = new Intent(MyPage.this, QnaActivity.class);
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