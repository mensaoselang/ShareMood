package com.example.sharemood.utils.DialogUtils;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.example.sharemood.R;


/**
 * Created by acer on 2018/10/10.
 */

public class BackgroundLayout extends LinearLayout {
    private float mCornerRadius;
    private int mBackgroundColor;

    public BackgroundLayout(Context context) {
        super(context);
        init();
    }

    public BackgroundLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public BackgroundLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @SuppressWarnings("deprecation")
    private void init() {
        int color = getContext().getResources().getColor(R.color.colorLoadingProgressBg);
        initBackground(color, mCornerRadius);
    }

    private void initBackground(int color, float cornerRadius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(color);
        drawable.setCornerRadius(cornerRadius);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(drawable);
        } else {
            setBackgroundDrawable(drawable);
        }
    }

    public void setCornerRadius(float radius) {
        mCornerRadius = ScaleUtilDialogs.dip2px(radius);
        initBackground(mBackgroundColor, mCornerRadius);
    }

    public void setBaseColor(int color) {
        mBackgroundColor = color;
        initBackground(mBackgroundColor, mCornerRadius);
    }

    //正方形显示
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int size = Math.max(width, height);
        setMeasuredDimension(size, size);
    }
}
