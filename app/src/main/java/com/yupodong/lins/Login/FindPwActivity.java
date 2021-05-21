package com.yupodong.lins.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yupodong.lins.R;

public class FindPwActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pw);

        Button find_id= (Button)findViewById(R.id.gofindidBtn);
        find_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // FindIdActivity로 전환
                Intent intent = new Intent(FindPwActivity.this, FindIdActivity.class);
                startActivity(intent);
            }
        });

        Button login= (Button)findViewById(R.id.loginBtn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // FindIdActivity로 전환
                Intent intent = new Intent(FindPwActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}