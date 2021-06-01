package com.yupodong.lins.Qna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.yupodong.lins.R;

public class CommentActivity extends LinearLayout {

    public CommentActivity(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public CommentActivity(Context context) {
        super(context);

        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_comment_ques, this, true);
    }
}