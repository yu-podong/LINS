package com.yupodong.lins.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yupodong.lins.DTO.user;
import com.yupodong.lins.R;

import java.util.List;

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

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DatabaseReference mData = FirebaseDatabase.getInstance().getReference();

        EditText id = (EditText)findViewById(R.id.id);
        EditText phone = (EditText)findViewById(R.id.phone);
        Button findpw= (Button)findViewById(R.id.findpwBtn);


        findpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String input_id = id.getText().toString();
                String input_phone = phone.getText().toString();

                firestore.collection("User").whereEqualTo("id",input_id).get()
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
                                                                user user = document.toObject(user.class);
                                                                Toast.makeText(FindPwActivity.this, user.getPassword(), Toast.LENGTH_SHORT).show();
                                                                break;
                                                            }

                                                        } else {
                                                            Toast.makeText(FindPwActivity.this, "실패", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                        break;
                                    }
                                } else {
                                    Toast.makeText(FindPwActivity.this, "실패", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });
    }
}