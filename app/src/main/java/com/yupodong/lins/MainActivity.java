package com.yupodong.lins;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yupodong.lins.Community.CommuActivity;
import com.yupodong.lins.DTO.*;
import com.yupodong.lins.License.LicenseActivity;
import com.yupodong.lins.Login.LoginActivity;
import com.yupodong.lins.Scheduler.SchedulerActivity;

import org.w3c.dom.Text;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 수정된 부분 : Activity 전환을 위해 사용
        // 그냥 findViewById로 4개의 button에 접근하게 되면, error 발생 (null object)
        LinearLayout firstBtnWrap = findViewById(R.id.firstBtnWrap);
        LinearLayout secondBtnWrap = findViewById(R.id.secondBtnWrap);
    
        // APP에서 뒤로가기를 지원하기 위해 Activity이동으로 바꿈 (login만 setContentView 사용)

        Button login = (Button)findViewById(R.id.log);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // LicenseActivity로 전환
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        
        Button licensebtn = (Button)firstBtnWrap.findViewById(R.id.licensebtn);
        licensebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // LicenseActivity로 전환
                Intent intent = new Intent(MainActivity.this, LicenseActivity.class);
                startActivity(intent);
            }
       });

        Button schebtn = (Button)firstBtnWrap.findViewById(R.id.schebtn);
       schebtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               // SchedulerActivity로 전환
               Intent intent = new Intent(MainActivity.this, SchedulerActivity.class);
               startActivity(intent);
          }
       });

        Button commubtn= (Button)secondBtnWrap.findViewById(R.id.commubtn);
        commubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // CommuActivity로 전환
                Intent intent = new Intent(MainActivity.this, CommuActivity.class);
                startActivity(intent);
            }
        });

        Button qnabtn = (Button)secondBtnWrap.findViewById(R.id.qnabtn);
        qnabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "아직 구현 중입니다.", Toast.LENGTH_SHORT).show();
            }
        });


    }
}