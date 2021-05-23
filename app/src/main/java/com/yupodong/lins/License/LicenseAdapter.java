package com.yupodong.lins.License;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yupodong.lins.R;

import java.util.ArrayList;

public class LicenseAdapter extends BaseAdapter{
    Context context;
    ArrayList<licenselist> licenselistArrayList;

    TextView day_view; // 시험날짜
    TextView number_view; // 시험회차
    TextView line_view; // 구분선
    TextView place_view; // 시험장소
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

       return convertView;
    }
}




