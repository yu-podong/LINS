package com.yupodong.lins.Community;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.yupodong.lins.R;

public class commentActivity extends LinearLayout {

    public commentActivity(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public commentActivity(Context context) {
        super(context);

        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_comment, this, true);
    }
}