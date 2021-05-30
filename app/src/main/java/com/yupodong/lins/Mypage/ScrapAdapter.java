package com.yupodong.lins.Mypage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yupodong.lins.Community.ReadActivity;
import com.yupodong.lins.R;

import java.util.ArrayList;

public class ScrapAdapter extends BaseAdapter {
    Context context;
    ArrayList<ScrapList> scrapListArrayList;

    TextView Scrap_day; // 날짜
    TextView Scrap_applydate; // 접수일정
    ImageView Scrap_icon; // 아이콘
    TextView Scrap_writingInfo;

    public ScrapAdapter(Context context, ArrayList<ScrapList> scrapListArrayList) {
        this.context = context;
        this.scrapListArrayList = scrapListArrayList;
    }

    @Override
    public int getCount() {
        return this.scrapListArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.scrapListArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView= LayoutInflater.from(context).inflate(R.layout.scraplist_layout,null);
            Scrap_day = (TextView)convertView.findViewById(R.id.s_day);
            Scrap_applydate = (TextView)convertView.findViewById(R.id.s_applyDate);
            Scrap_icon = (ImageView)convertView.findViewById(R.id.s_scrap);
            Scrap_writingInfo = (TextView)convertView.findViewById(R.id.writerInfo);
        }
        
        // license를 클릭한 경우
        if (Scrap_writingInfo.getText() == null) {
            Scrap_day.setText(scrapListArrayList.get(position).getS_day());
            Scrap_applydate.setText(scrapListArrayList.get(position).getS_applydate());
            Scrap_icon.setImageResource(scrapListArrayList.get(position).getS_icon());
            Scrap_icon.setVisibility(View.VISIBLE);
            Scrap_icon.setColorFilter(Color.parseColor("#E9C87B"));
            Scrap_writingInfo.setVisibility(View.GONE);

            // scrap버튼을 클릭했을 때
            Scrap_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 클릭한 license를 scrapList에서 제거
                    scrapListArrayList.remove(scrapListArrayList.get(position));
                }
            });
        }
        
        // commu를 클릭한 경우
        else if(Scrap_writingInfo.getText() != null) {
            Scrap_day.setText(scrapListArrayList.get(position).getS_day());
            Scrap_applydate.setText(scrapListArrayList.get(position).getS_applydate());
            Scrap_icon.setVisibility(View.GONE);
            Scrap_writingInfo.setText(scrapListArrayList.get(position).getWritingInfo());
            Scrap_writingInfo.setVisibility(View.VISIBLE);

            // 한 리스트를 클릭하면 read.xml로 넘어감
            RelativeLayout oneList = (RelativeLayout) convertView.findViewById(R.id.scrapOneList);

            oneList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ReadActivity.class);
                    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

                    // viewCount의 값을 1 증가하여 update 진행
                    firestore.collection("Commu").document(Integer.toString(scrapListArrayList.get(position).getID()))
                            .update("viewCount",scrapListArrayList.get(position).getID()+1)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    intent.putExtra("category", scrapListArrayList.get(position).getS_applydate());
                                    intent.putExtra("writingID", scrapListArrayList.get(position).getID());

                                    context.startActivity(intent);
                                }
                            });
                }
            });
        }

        return convertView;
    }
}
