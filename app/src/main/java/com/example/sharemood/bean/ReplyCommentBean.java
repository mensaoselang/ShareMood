package com.example.sharemood.bean;

import com.example.sharemood.ui.login.Bean.MyUserBean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by acer on 2018/12/16.
 */

public class ReplyCommentBean extends BmobObject implements Serializable{
    private String content;
    private MyUserBean myUserBean;
    private boolean second;
    private String otherNickname;
    public MyUserBean getMyUserBean() {
        return myUserBean;
    }
    public ReplyCommentBean setMyUserBean(MyUserBean myUserBean) {
        this.myUserBean = myUserBean;
        return this;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isSecond() {
        return second;
    }

    public void setSecond(boolean second) {
        this.second = second;
    }

    public String getOtherNickname() {
        return otherNickname;
    }

    public void setOtherNickname(String otherNickname) {
        this.otherNickname = otherNickname;
    }
}
