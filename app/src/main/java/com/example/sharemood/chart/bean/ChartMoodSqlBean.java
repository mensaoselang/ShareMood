package com.example.sharemood.chart.bean;

import org.litepal.crud.LitePalSupport;

/**
 * Created by acer on 2018/12/12.
 */

public class ChartMoodSqlBean extends LitePalSupport {
    private long id;
    private String dayOfWeek;
    private int moodIndex;
    private long diaryLongId;
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getMoodIndex() {
        return moodIndex;
    }

    public void setMoodIndex(int moodIndex) {
        this.moodIndex = moodIndex;
    }

    public long getDiaryLongId() {
        return diaryLongId;
    }

    public void setDiaryLongId(long diaryLongId) {
        this.diaryLongId = diaryLongId;
    }
}
