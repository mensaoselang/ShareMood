package com.example.sharemood.bean;

import com.example.sharemood.ui.login.Bean.MyUserBean;

import java.io.Serializable;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by acer on 2018/12/8.
 */

public class CommentBean extends BmobObject implements Serializable{
    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论的用户
     */
    private MyUserBean user;

    /**
     * 所评论的帖子
     */
    private DiaryShareBean diaryShare;

    private String date;

    /**
     * 二级评论
     */
    private List<ReplyCommentBean> replyList;

    public List<ReplyCommentBean> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<ReplyCommentBean> replyList) {
        this.replyList = replyList;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MyUserBean getUser() {
        return user;
    }

    public void setUser(MyUserBean user) {
        this.user = user;
    }

    public DiaryShareBean getDiaryShare() {
        return diaryShare;
    }

    public void setDiaryShare(DiaryShareBean diaryShare) {
        this.diaryShare = diaryShare;
    }
}
