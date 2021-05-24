package com.yupodong.lins.License;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yupodong.lins.DTO.licenseScrap;
import com.yupodong.lins.License.LicenseListActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.yupodong.lins.R;

import java.util.ArrayList;

public class LicenseAdapter extends BaseAdapter{
    private ArrayList<String> items;
    Context context;
    ArrayList<licenselist> licenselistArrayList;

    TextView day_view; // 시험날짜
    TextView number_view; // 시험회차
    ImageButton scrap_view; // 스크랩버튼


    public LicenseAdapter(Context context, ArrayList<licenselist> licenselistArrayList) {
        this.context = context;
        this.licenselistArrayList = licenselistArrayList;
    }

    @Override
    public int getCount() {
        return this.licenselistArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.licenselistArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       if(convertView==null) {
           convertView = LayoutInflater.from(context).inflate(R.layout.licenselist_layout, null);
           day_view = (TextView) convertView.findViewById(R.id.day);
           number_view = (TextView) convertView.findViewById(R.id.applyDate);
           scrap_view = (ImageButton) convertView.findViewById(R.id.scrap);
       }
        day_view.setText(licenselistArrayList.get(position).getLicense_day());
        number_view.setText(licenselistArrayList.get(position).getLicense_number());
        scrap_view.setImageResource(licenselistArrayList.get(position).getLicense_scrap());

        // 각 시험일정 안에 들어있는 스크랩 버튼
        ImageButton scrapBtn = (ImageButton) convertView.findViewById(R.id.scrap);
        // 스크랩 버튼 clickListener
        scrapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 현재 로그인한 사용자를 보기 위함
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                // 만약, 현재 로그인한 사용자가 있다면 (LINS를 로그인 후 사용하고 있다면)
                if (currentUser != null) {
                    // scrapDTO를 선언하여 각 attribute에 알맞은 값 저장
                    licenseScrap scraplist = new licenseScrap();
                    scraplist.setNickName(currentUser.getEmail());
                    scraplist.setLicenseID(licenselistArrayList.get(position).getLicenseID());
                    // 사용자의 이메일 삽입 (이메일은 겹칠 일이 없기 때문에)
                    scraplist.setLicenseName(licenselistArrayList.get(position).getLicenseName());

                    // firebase에 add
                    firestore.collection("Scrap").document().set(scraplist).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(context, "스크랩에 저장했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                // LINS를 로그인을 하지 않고 사용할 경우
                else {
                    // 로그인 후에 이용해달라는 Toast 메세지 출력
                    Toast.makeText(context, "로그인 후에 이용해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

       return convertView;
    }
}




