package com.yupodong.lins.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yupodong.lins.DTO.user;
import com.yupodong.lins.MainActivity;
import com.yupodong.lins.R;
import com.yupodong.lins.Login.check_class;

import java.util.List;


public class FindIdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_id);

        EditText name = (EditText)findViewById(R.id.name);
        EditText phone = (EditText)findViewById(R.id.phone);

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        Button find_pw= (Button)findViewById(R.id.gofindpwBtn);
        find_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // FindIdActivity로 전환
                Intent intent = new Intent(FindIdActivity.this, FindPwActivity.class);
                startActivity(intent);
            }
        });

        Button findid= (Button)findViewById(R.id.findidBtn);

        findid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ID찾기
                String input_name = name.getText().toString();
                String input_phone = phone.getText().toString();

                check_class check = new check_class();

                firestore.collection("User").whereEqualTo("name",input_name).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        firestore.collection("User").whereEqualTo("phoneNum",input_phone).get()
                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                        if(task.isSuccessful()){
                                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                                Toast.makeText(FindIdActivity.this, document.getId(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        } else {
                                                            Toast.makeText(FindIdActivity.this, "실패", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    }
                                    } else {
                                    Toast.makeText(FindIdActivity.this, "실패", Toast.LENGTH_SHORT).show();
                                }

                                }
                        });

            }

        });

        Button login= (Button)findViewById(R.id.loginBtn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindIdActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


    }
}