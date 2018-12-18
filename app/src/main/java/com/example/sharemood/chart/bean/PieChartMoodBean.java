package com.example.sharemood.chart.bean;

import com.example.sharemood.ui.login.Bean.MyUserBean;

import cn.bmob.v3.BmobObject;

/**
 * Created by acer on 2018/12/12.
 */

public class PieChartMoodBean extends BmobObject{
    private String type;
    private Integer typeId;
    private Integer sum;
    private String color;
    private MyUserBean myUserBean;
    public MyUserBean getMyUserBean() {
        return myUserBean;
    }
    public PieChartMoodBean setMyUserBean(MyUserBean myUserBean) {
        this.myUserBean = myUserBean;
        return this;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }
}
