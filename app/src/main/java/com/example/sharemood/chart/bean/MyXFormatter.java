package com.example.sharemood.chart.bean;

import android.util.Log;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * Created by acer on 2018/12/11.
 */

public class MyXFormatter  implements IAxisValueFormatter {

    private final String[] mvalues;

    public MyXFormatter(String[] values) {
        mvalues= values;
    }
    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // "value" represents the position of the label on the axis (x or y)
        try {
            int tag = (int) ((value * 10 + 5) / 10);
            return mvalues[(int) tag];
        }catch (Exception e){
            return mvalues[0];
        }

    }
}