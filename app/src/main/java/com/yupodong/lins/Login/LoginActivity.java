package com.yupodong.lins.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yupodong.lins.MainActivity;
import com.yupodong.lins.R;

import org.w3c.dom.Text;

public class LoginActivity<auth> extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText id = (EditText)findViewById(R.id.id);
        EditText password = (EditText)findViewById(R.id.password);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        //회원가입페이지로 넘어가는 부분
        TextView signup= (TextView)findViewById(R.id.singnup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // singnupActivity로 전환
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        //ID/PW 찾기로 넘어가는 부분
        Button find= (Button)findViewById(R.id.find);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // FindIdActivity로 전환
                Intent intent = new Intent(LoginActivity.this, FindIdActivity.class);
                startActivity(intent);
            }
        });

        //로그인하기 버튼 눌렀을 때
        Button loginBtn = (Button)findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 로그인하기

                String input_id = id.getText().toString();
                String input_pw = password.getText().toString();
                
                // 입력하지 않은 EditText가 있으면
                if (input_id.equals("") || input_pw.equals("")) {
                    Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호를 입력하지 않았습니다.", Toast.LENGTH_SHORT).show();
                }
                // 실제 firebase에 관할하는 code
                firebaseAuth.signInWithEmailAndPassword(input_id,input_pw)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {  //로그인 성공시
                                    Toast.makeText(LoginActivity.this, "로그인 완료", Toast.LENGTH_SHORT).show();
                                    // 메인화면으로 넘어감
                                    finish();
                                }
                                else { //로그인 실패시
                                    Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }
}
