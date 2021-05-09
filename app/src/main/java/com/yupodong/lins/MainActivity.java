package com.yupodong.lins;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yupodong.lins.DTO.*;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText id = (EditText)findViewById(R.id.id);
        EditText password = (EditText)findViewById(R.id.password);
        EditText name = (EditText)findViewById(R.id.name);

        Button insertBtn = (Button)findViewById(R.id.button);   // 데이터 삽입 BTN
        Button getBtn = (Button)findViewById(R.id.button2);     // 특정 데이터 가져오기 BTN

        TextView resultText = (TextView)findViewById((R.id.textView));

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();  // 필수로 작성해야 함

        // 데이터 삽입 Btn Click시,
        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // user class 사용 (앞으로 collection = table = DTO이름)
                userDTO user;
                user = new userDTO(id.getText().toString(), password.getText().toString());

                // firebase에 컬랙션 이름, 문서 이름 지정 -> data 삽입 (아래의 형식으로 데이터 삽입할 것)
                firestore.collection("User").document(name.getText().toString()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // 데이터 삽입 성공 시, Toast 메세지 띄움
                        Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firestore.collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            String test = "";
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                test += document.getId() + " : " + document.getData() + "\n";
                            }
                            resultText.setText(test);
                        } else {
                            Toast.makeText(MainActivity.this, "Fail getting Data", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}