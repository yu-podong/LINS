package com.yupodong.lins.Mypage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yupodong.lins.R;

import java.util.ArrayList;

public class ScrapAdapter extends BaseAdapter {
    Context context;
    ArrayList<ScrapList> scrapListArrayList;

    TextView Scrap_day; // 날짜
    TextView Scrap_applydate; // 접수일정
    ImageView Scrap_icon; // 아이콘

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
        }

        Scrap_day.setText(scrapListArrayList.get(position).getS_day());
        Scrap_applydate.setText(scrapListArrayList.get(position).getS_applydate());
        Scrap_icon.setImageResource(scrapListArrayList.get(position).getS_icon());
        return convertView;
    }
}
