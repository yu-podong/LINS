package com.yupodong.lins.Qna;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yupodong.lins.R;

import java.util.ArrayList;

public class QnaAdapter extends BaseAdapter {
    Context context;
    ArrayList<QnaList> qnaListArrayList;

    TextView Qna_title; // 제목
    TextView Qna_day; // 작성 날짜
    ImageView Qna_icon; // 아이콘
    TextView Qna_comment; // 댓글 수


    public QnaAdapter(Context context, ArrayList<QnaList> qnaListArrayList) {
        this.context = context;
        this.qnaListArrayList = qnaListArrayList;
    }

    @Override
    public int getCount() {
        return this.qnaListArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.qnaListArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView= LayoutInflater.from(context).inflate(R.layout.qnalist_layout,null);
            Qna_title=(TextView)convertView.findViewById(R.id.qna_write_title);
            Qna_day=(TextView)convertView.findViewById(R.id.qna_write_day);
            Qna_icon=(ImageView)convertView.findViewById(R.id.qna_comment_icon);
            Qna_comment=(TextView)convertView.findViewById(R.id.qna_comment_num);
        }

        Qna_title.setText(qnaListArrayList.get(position).getQ_title());
        Qna_day.setText(qnaListArrayList.get(position).getQ_day());
        Qna_icon.setImageResource(qnaListArrayList.get(position).getQ_comment_icon());
        Qna_comment.setText(qnaListArrayList.get(position).getQ_comment_num());
        return convertView;
    }
}
