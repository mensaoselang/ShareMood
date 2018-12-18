package com.example.sharemood.chart.presenter;

import com.example.sharemood.base.BasePresenter;
import com.example.sharemood.chart.activity.PieChartActivity;
import com.example.sharemood.chart.bean.PieChartMoodBean;
import com.example.sharemood.ui.login.Bean.MyUserBean;
import com.example.sharemood.utils.ToastUtil;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by acer on 2018/12/12.
 */

public class PieChartPresenter extends BasePresenter<PieChartActivity> {
    public void getData(){
        BmobQuery<PieChartMoodBean> query = new BmobQuery<>();
        query.addWhereEqualTo("myUserBean", BmobUser.getCurrentUser(MyUserBean.class));
        query.findObjects(new FindListener<PieChartMoodBean>() {
            @Override
            public void done(List<PieChartMoodBean> object, BmobException e) {
                if (e == null) {
                    getV().initPieChart(object);//初始视图
                } else {
                    ToastUtil.showShort("饼图失败" + e.getMessage());
                }
            }
        });

    }
}
