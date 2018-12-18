package com.example.sharemood.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by acer on 2018/12/6.
 */

public class MoodTypeBean extends BmobObject{
    private String type;
    private int id;
    private String color;

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
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
