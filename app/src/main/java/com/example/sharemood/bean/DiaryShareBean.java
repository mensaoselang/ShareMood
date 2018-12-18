package com.example.sharemood.bean;

import com.example.sharemood.ui.login.Bean.MyUserBean;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by acer on 2018/12/3.
 */

public class DiaryShareBean extends BmobObject implements Serializable{
    private Integer typeId;
    private long sqlId;
    private String userPhone;
    private String nickName;
    private String content;
    private String weather;
    private String temperature;
    private String location;
    private String moodColor;
    private String moodIndex;
    private String time;
    private String dayOfWeek;
    private String dateNumber;
    private String month;
    private String year;
    List<BmobFile> imageFiles;
    private String userHeartImage;
   List<String> objectLike;

    public List<String> getObjectLike() {
        return objectLike;
    }

    public void setObjectLike(List<String> objectLike) {
        this.objectLike = objectLike;
    }

    private MyUserBean myUserBean;
    public MyUserBean getMyUserBean() {
        return myUserBean;
    }
    public DiaryShareBean setMyUserBean(MyUserBean myUserBean) {
        this.myUserBean = myUserBean;
        return this;
    }
    public String getUserHeartImage() {
        return userHeartImage;
    }

    public void setUserHeartImage(String userHeartImage) {
        this.userHeartImage = userHeartImage;
    }

    public List<BmobFile> getImageFiles() {
        return imageFiles;
    }

    public void setImageFiles(List<BmobFile> imageFiles) {
        this.imageFiles = imageFiles;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public long getSqlId() {
        return sqlId;
    }

    public void setSqlId(long sqlId) {
        this.sqlId = sqlId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMoodColor() {
        return moodColor;
    }

    public void setMoodColor(String moodColor) {
        this.moodColor = moodColor;
    }

    public String getMoodIndex() {
        return moodIndex;
    }

    public void setMoodIndex(String moodIndex) {
        this.moodIndex = moodIndex;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getDateNumber() {
        return dateNumber;
    }

    public void setDateNumber(String dateNumber) {
        this.dateNumber = dateNumber;
    }

}
