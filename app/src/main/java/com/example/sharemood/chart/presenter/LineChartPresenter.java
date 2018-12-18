package com.example.sharemood.chart.presenter;

import com.example.sharemood.base.BasePresenter;
import com.example.sharemood.chart.activity.LineChartActivity;
import com.example.sharemood.chart.bean.ChartMoodSqlBean;

import org.litepal.LitePal;

import java.util.List;

/**
 * Created by acer on 2018/12/13.
 */

public class LineChartPresenter extends BasePresenter<LineChartActivity> {
    public void getData(){
        List<ChartMoodSqlBean> list= LitePal.findAll(ChartMoodSqlBean.class);
        getV().initLineData(list);
    }
}
