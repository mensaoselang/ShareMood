package com.example.sharemood.diary.bean;

import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acer on 2018/12/3.
 */

public class DiarySQLBean extends LitePalSupport {
    private long id;
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
    List<String> imagePathList = new ArrayList<>();
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public List<String> getImagePathList() {
        return imagePathList;
    }

    public void setImagePathList(List<String> imagePathList) {
        this.imagePathList = imagePathList;
    }
}
