package com.example.sharemood.square.presenter;

import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import com.example.sharemood.base.BasePresenter;
import com.example.sharemood.bean.CommentBean;
import com.example.sharemood.bean.DiaryShareBean;
import com.example.sharemood.bean.ReplyCommentBean;
import com.example.sharemood.chart.bean.PieChartMoodBean;
import com.example.sharemood.square.activity.SquareDetailActivity;
import com.example.sharemood.ui.login.Bean.MyUserBean;
import com.example.sharemood.utils.ToastUtil;

import org.w3c.dom.Comment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by acer on 2018/12/7.
 */

public class SquareDetailPresenter extends BasePresenter<SquareDetailActivity> {
    //发送评论
    CommentBean comment;
    public void sendComment(final String diaryShareObjectId, String content) {
        long now = System.currentTimeMillis();
        SimpleDateFormat simpleDay = new SimpleDateFormat("MM-dd");
        MyUserBean user = BmobUser.getCurrentUser(MyUserBean.class);
        DiaryShareBean diaryShareBean = new DiaryShareBean();
        diaryShareBean.setObjectId(diaryShareObjectId);
        comment = new CommentBean();
        comment.setContent(content);
        comment.setDiaryShare(diaryShareBean);
        comment.setUser(user);
        comment.setDate(simpleDay.format(now));
        comment.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    ToastUtil.showShort("评论发表成功");
                       getComment(diaryShareObjectId);
                      //    getV().addCommentData(comment);
                } else {
                    ToastUtil.showShort("评论发表失败" + e.getMessage());
                }
            }

        });
    }

    //获取评论
    public void getComment(String diaryShareObjectId) {
        BmobQuery<CommentBean> query = new BmobQuery<CommentBean>();
        //用此方式可以构造一个BmobPointer对象。只需要设置objectId就行
        DiaryShareBean diaryShareBean = new DiaryShareBean();
        diaryShareBean.setObjectId(diaryShareObjectId);
        query.addWhereEqualTo("diaryShare", new BmobPointer(diaryShareBean));
        //希望同时查询该评论的发布者的信息，以及该帖子的作者的信息，这里用到上面`include`的并列对象查询和内嵌对象的查询
//        query.include("user,diaryShare.myUserBean");
        query.include("user");//查询出发布评论的人的信息
        query.findObjects(new FindListener<CommentBean>() {
            @Override
            public void done(List<CommentBean> objects, BmobException e) {
                if (e == null) {
                    getV().setComment(objects);
                } else {
                }
            }
        });
    }

    //删除评论
    public void deleteComment(String objectId) {
        CommentBean commentBean = new CommentBean();
        commentBean.setObjectId(objectId);
        commentBean.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    getV().deleteCommentSucceed();
                } else {
                    ToastUtil.showShort("删除失败" + e.getMessage());
                }

            }
        });
    }

    public void deletePost(String objectId, final Integer typeId) {
        final DiaryShareBean diaryShareBean = new DiaryShareBean();
        diaryShareBean.setObjectId(objectId);
        diaryShareBean.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    updatePieChart(typeId);//删除分享的贴纸就把对应的心情比重给减一
                    getV().deletePostSucceed();
                } else {
                    ToastUtil.showShort("删除失败" + e.getMessage());
                }
            }
        });
    }


    //更新保存分享的类型作为PieChart的心情分享数据
    public void updatePieChart(Integer typeId) {
        BmobQuery<PieChartMoodBean> query = new BmobQuery<>();
        query.addWhereEqualTo("myUserBean", BmobUser.getCurrentUser(MyUserBean.class));
//        query.order("-updatedAt");
        query.addWhereEqualTo("typeId", typeId);
        query.findObjects(new FindListener<PieChartMoodBean>() {
            @Override
            public void done(List<PieChartMoodBean> object, BmobException e) {
                if (e == null) {
                    //更新Person表里面id为6b6c11c537的数据
                    if (object.get(0).getSum() > 0) {
                     PieChartMoodBean pieChartMoodBean = new PieChartMoodBean();
                        pieChartMoodBean.setSum(object.get(0).getSum() - 1);//每分享一次就加一
                        pieChartMoodBean.update(object.get(0).getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                } else {
                                    ToastUtil.showShort("心情饼图更新失败");
                                }
                            }
                        });
                    }
                } else {
                    ToastUtil.showShort("饼图减总量失败" + e.getMessage());
                }
            }
        });
    }
    //二级评论
    public void  replyComment(CommentBean commentBean,String comment, final String diaryShareObjectId){
        ReplyCommentBean replyCommentBean=new ReplyCommentBean();
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
                    getComment(diaryShareObjectId);
                }else {
                    ToastUtil.showShort("二级回复失败"+e.getMessage());
                }
            }
        });
    }
    //喜欢该贴子绑定
    public void addLike(DiaryShareBean diaryShareBean){
        MyUserBean user = BmobUser.getCurrentUser(MyUserBean.class);
        if (diaryShareBean.getObjectLike()==null){
            List<String> likes=new ArrayList<>();
            likes.add(user.getObjectId());
            diaryShareBean.setObjectLike(likes);
        }else {
            diaryShareBean.getObjectLike().add(user.getObjectId());
        }

        diaryShareBean.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    getV().addLikeSucceed();//添加喜欢成功
                    Log.i("bmob","多对多关联添加成功");
                }else{
                    Log.i("bmob","失败："+e.getMessage());
                }
            }

        });
    }
    //移除队该帖子的喜欢
    public void removeLike(DiaryShareBean diaryShareBean){
        MyUserBean user = BmobUser.getCurrentUser(MyUserBean.class);
        diaryShareBean.getObjectLike().remove(user.getObjectId());
        diaryShareBean.update(new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    getV().removeLikeSucceed();
                    Log.i("bmob","关联关系删除成功");
                }else{
                    Log.i("bmob","失败："+e.getMessage());
                }
            }

        });
    }
    //查找是否喜欢这个帖子
    MyUserBean user = BmobUser.getCurrentUser(MyUserBean.class);
    public void whetherLike(String objectId) {
        BmobQuery<DiaryShareBean> bmobQuery = new BmobQuery<DiaryShareBean>();
        bmobQuery.include("objectLike");
        //包含作者信息
        bmobQuery.include("myUserBean");
        bmobQuery.getObject(objectId, new QueryListener<DiaryShareBean>() {
            @Override
            public void done(DiaryShareBean diaryShareBean, BmobException e) {
                if (e==null){
                    if (diaryShareBean.getObjectLike()!=null){
                if (diaryShareBean.getObjectLike().size()>0){
                for (int i=0;i<diaryShareBean.getObjectLike().size();i++){
                    if (diaryShareBean.getObjectLike().get(i).equals(user.getObjectId())){
                        getV().setFocusonBackground(true);
                        break;
                    }else {
                        getV().setFocusonBackground(false);
                    }
                }
            }else {
                    getV().setFocusonBackground(false);
                }
                        getV().setTvFocusonNumber(diaryShareBean.getObjectLike().size());
        }
              getV().changBean(diaryShareBean);
                }
            }
        });
    }
}