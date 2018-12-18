package com.example.sharemood.square.presenter;

import com.example.sharemood.base.BasePresenter;
import com.example.sharemood.bean.CommentBean;
import com.example.sharemood.bean.ReplyCommentBean;
import com.example.sharemood.square.activity.CommentActivity;
import com.example.sharemood.ui.login.Bean.MyUserBean;
import com.example.sharemood.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by acer on 2018/12/16.
 */

public class CommentPresenter extends BasePresenter<CommentActivity> {
    //二级评论
    private CommentBean cm;
    public void  replyComment(CommentBean commentBean,String comment,boolean second,String otherName){
        cm=commentBean;
        ReplyCommentBean replyCommentBean=new ReplyCommentBean();
        replyCommentBean.setOtherNickname(otherName);
        replyCommentBean.setSecond(second);
        replyCommentBean.setContent(comment);
        replyCommentBean.setMyUserBean(BmobUser.getCurrentUser(MyUserBean.class));
        if (commentBean.getReplyList()==null){
            List<ReplyCommentBean> replyCommentBeanList=new ArrayList<>();
            replyCommentBeanList.add(replyCommentBean);
            commentBean.setReplyList(replyCommentBeanList);
        }else {
            commentBean.getReplyList().add(replyCommentBean);
        }
        commentBean.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e==null){
                     getV().replySucceed(cm);
                }else {
                    ToastUtil.showShort("二级回复失败"+e.getMessage());
                }
            }
        });
    }
    //删除对应的二级评论
    public void deleteReplay(final CommentBean commentBean, ReplyCommentBean replyCommentBean){
        commentBean.getReplyList().remove(replyCommentBean);
        commentBean.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e==null){
                    getV().replySucceed(commentBean);
                }else {
                    ToastUtil.showShort("二级回复删除失败"+e.getMessage());
                }
            }
        });
    }
}
