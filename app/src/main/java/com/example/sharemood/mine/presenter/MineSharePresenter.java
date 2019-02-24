package com.example.sharemood.mine.presenter;

import com.example.sharemood.base.BasePresenter;
import com.example.sharemood.bean.DiaryShareBean;
import com.example.sharemood.mine.activity.MineShareActivity;
import com.example.sharemood.ui.login.Bean.MyUserBean;
import com.example.sharemood.utils.ToastUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by acer on 2018/12/12.
 */

public class MineSharePresenter extends BasePresenter <MineShareActivity>{
        /**
     * 查询一对一关联，查询当前用户发表的所有帖子
     */
    public void queryPostAuthor() {
        if (BmobUser.isLogin()) {
            BmobQuery<DiaryShareBean> query = new BmobQuery<>();
            query.addWhereEqualTo("myUserBean", BmobUser.getCurrentUser(MyUserBean.class));
            query.order("-updatedAt");
            //包含作者信息
            query.include("objectLike,myUserBean");
            query.findObjects(new FindListener<DiaryShareBean>() {
                @Override
                public void done(List<DiaryShareBean> object, BmobException e) {
                    if (e == null) {
                        getV().initContentView(object);
                    } else {
                        ToastUtil.showShort("查询失败" + e.getMessage());
                    }
                }

            });
        } else {
            ToastUtil.showShort("请先登录");
            //  Snackbar.make(mFabAddPost, , Snackbar.LENGTH_LONG).show();
        }
    }
}
