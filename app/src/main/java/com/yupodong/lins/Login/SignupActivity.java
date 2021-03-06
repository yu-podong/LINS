package com.yupodong.lins.Login;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yupodong.lins.MainActivity;
import com.yupodong.lins.R;
import com.yupodong.lins.DTO.user;

public class SignupActivity<auth> extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        EditText name = (EditText)findViewById(R.id.name);
        EditText phone = (EditText)findViewById(R.id.phone);
        EditText nick = (EditText)findViewById(R.id.nick);
        EditText id = (EditText)findViewById(R.id.id);
        EditText password = (EditText)findViewById(R.id.password);

        Button insertBtn = (Button)findViewById(R.id.sing_up); //데이터 삽입입

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();  // 필수로 작성해야 함
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        // 데이터 삽입 Btn Click시,
        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // user class 사용 (앞으로 collection = table = DTO이름)

                String input_id = id.getText().toString();

                firestore.collection("User").document(input_id).get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()  {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                DocumentSnapshot Document = task.getResult();
                                if(Document.exists()) {
                                    Toast.makeText(SignupActivity.this, "이미 존재하는 ID입니다.", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    user user;
                                    user = new user(id.getText().toString(), password.getText().toString(),name.getText().toString(),nick.getText().toString(),phone.getText().toString(),0);

                                    // firebase에 컬랙션 이름, 문서 이름 지정 -> data 삽입 (아래의 형식으로 데이터 삽입할 것)
                                    firestore.collection("User").document(id.getText().toString()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            // 데이터 삽입 성공 시, Toast 메세지 띄움
                                            Toast.makeText(SignupActivity.this,"환영합니다, " + name.getText().toString() + "님!",Toast.LENGTH_SHORT).show();
                                            // 현재 회원가입 Activity 종료 (로그인 화면으로 넘어감)
                                            finish();
                                        }
                                    });

                                    firebaseAuth.createUserWithEmailAndPassword(id.getText().toString(),password.getText().toString())
                                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if (task.isSuccessful()) {  //아이디 생성완료시
                                                        Toast.makeText(SignupActivity.this, "환영합니다, " + name.getText().toString() + "님!", Toast.LENGTH_SHORT).show();}

                                                    else { //생성 실패시
                                                        Toast.makeText(SignupActivity.this, "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });

                                }
                        }
                        });


            }
        });


    }
}

