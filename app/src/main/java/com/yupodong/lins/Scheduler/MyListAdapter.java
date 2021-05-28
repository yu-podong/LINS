package com.yupodong.lins.Scheduler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.yupodong.lins.R;

import java.util.ArrayList;
import java.util.Date;

public class MyListAdapter extends BaseAdapter {
    Context context;
    ArrayList<list_item> list_itemArrayList;

    TextView schelist;
    ImageView color_image;


    public MyListAdapter(Context context, ArrayList<list_item> list_itemArrayList) {
        this.context = context;
        this.list_itemArrayList = list_itemArrayList;
    }

    @Override
    public int getCount() {
        return this.list_itemArrayList.size();  //리스트 목록 수
    }

    @Override
    public Object getItem(int position) {  //arraylist에 저장되어 있는 객체의 위치
        return this.list_itemArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {  //해당 아이템의 위치 표시
        return position;
    }

    @Override
    public View getView(int positon, View view, ViewGroup viewGroup) {  //아이템과 xml을 연결하고 화면에 표시
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item,null);  //item.xml 불러오기

            schelist = (TextView)view.findViewById(R.id.licensNameSche);
            color_image = (ImageView)view.findViewById(R.id.colorImage);
        }

        schelist.setText(list_itemArrayList.get(positon).getSchelist());
        color_image.setImageResource(list_itemArrayList.get(positon).getColor_image());
        return view;
    }
}
