package com.yupodong.lins.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yupodong.lins.R;

public class LoginActivity<auth> extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText id = (EditText)findViewById(R.id.id);
        EditText password = (EditText)findViewById(R.id.password);

        Button loginBtn = (Button)findViewById(R.id.loginBtn);   // 데이터 삽입 BTN
        //Button getBtn = (Button)findViewById(R.id.button2);     // 특정 데이터 가져오기 BTN

        TextView resultText = (TextView)findViewById((R.id.textView));

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();  // 필수로 작성해야 함
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();



        //회원가입페이지로 넘어가는 부분
        Button signup= (Button)findViewById(R.id.singnup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // singnupActivity로 전환
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

    }
}
