package com.example.sharemood.ui.index.bean;

import com.example.sharemood.ui.login.Bean.MyUserBean;

import cn.bmob.v3.BmobObject;

/**
 * Created by acer on 2018/12/8.
 */
//图表的心情指数
public class ChartMoodBean extends BmobObject {
    private int index;
    private long diarySqlId;
    private String diaryObjectId;
    private MyUserBean myUserBean;
    public MyUserBean getMyUserBean() {
        return myUserBean;
    }
    public ChartMoodBean setMyUserBean(MyUserBean myUserBean) {
        this.myUserBean = myUserBean;
        return this;
    }
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public long getDiarySqlId() {
        return diarySqlId;
    }

    public void setDiarySqlId(long diarySqlId) {
        this.diarySqlId = diarySqlId;
    }

    public String getDiaryObjectId() {
        return diaryObjectId;
    }

    public void setDiaryObjectId(String diaryObjectId) {
        this.diaryObjectId = diaryObjectId;
    }
}
