package com.example.sharemood.chart.presenter;

import com.example.sharemood.base.BasePresenter;
import com.example.sharemood.chart.activity.BarChartActivity;
import com.example.sharemood.chart.bean.ChartMoodSqlBean;

import org.litepal.LitePal;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import retrofit2.http.PUT;

/**
 * Created by acer on 2018/12/13.
 */

public class BarChartPresenter extends BasePresenter<BarChartActivity>{
    public void getData() {
        List<ChartMoodSqlBean> list= LitePal.findAll(ChartMoodSqlBean.class);
        getV().initBarData(list);
    }
}
