package com.yupodong.lins.Community;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yupodong.lins.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CommuAdapter extends BaseAdapter{
    Context context;
    ArrayList<CommuList> commuListArrayList;

    TextView Commu_title; // 글 제목
    TextView Commu_nickname; // 닉네임
    TextView Line; // 구분선
    TextView Commu_date; // 작성날짜
    TextView Viewnum; // 조회수
    TextView Commenttnum; // 댓글수
    ImageView View_icon; // 조회수 아이콘
    ImageView Comment_icon; // 댓글 아이콘


    public CommuAdapter(Context context, ArrayList<CommuList> commuListArrayList) {
        this.context = context;
        this.commuListArrayList = commuListArrayList;
    }

    @Override
    public int getCount() {
        return this.commuListArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return commuListArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.commulist_layout, null);
            Commu_title = (TextView) convertView.findViewById(R.id.write_title);
            Commu_nickname = (TextView) convertView.findViewById(R.id.nickname);
            Line = (TextView) convertView.findViewById(R.id.line);
            Commu_date = (TextView) convertView.findViewById(R.id.write_day);
            View_icon = (ImageView) convertView.findViewById(R.id.views);
            Viewnum = (TextView) convertView.findViewById(R.id.view_num);
            Comment_icon = (ImageView) convertView.findViewById(R.id.comment_icon);
            Commenttnum = (TextView) convertView.findViewById(R.id.comment_num);
        }

        Commu_title.setText(commuListArrayList.get(position).getCommu_title());
        Commu_nickname.setText(commuListArrayList.get(position).getCommu_nickname());
        Line.setText(commuListArrayList.get(position).getLine());
        Commu_date.setText(commuListArrayList.get(position).getCommu_date());
        View_icon.setImageResource(commuListArrayList.get(position).getView_icon());
        Viewnum.setText(Integer.toString(commuListArrayList.get(position).getViewnum()));
        Comment_icon.setImageResource(commuListArrayList.get(position).getComment_icon());
        Commenttnum.setText(Integer.toString(commuListArrayList.get(position).getCommenttnum()));

        // 한 리스트를 클릭하면 read.xml로 넘어감
        LinearLayout oneList = (LinearLayout) convertView.findViewById(R.id.commuOneList);

        oneList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ReadActivity.class);
                FirebaseFirestore firestore = FirebaseFirestore.getInstance();

                // viewCount의 값을 1 증가하여 update 진행
                firestore.collection("Commu").document(Integer.toString(commuListArrayList.get(position).getWritingID()))
                        .update("viewCount",commuListArrayList.get(position).getViewnum()+1)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        intent.putExtra("category", commuListArrayList.get(position).getCommuName());
                        intent.putExtra("writingID", commuListArrayList.get(position).getWritingID());

                        context.startActivity(intent);
                    }
                });
            }
        });

        return convertView;
    }
}




