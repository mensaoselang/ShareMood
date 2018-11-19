package com.example.sharemood.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by acer on 2018/11/18.
 */

public class BackLinearLayout extends LinearLayout{
    public BackLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
                //点击结束当前Activity
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
             ((Activity)getContext()).finish();
            }
        });
    }
}
