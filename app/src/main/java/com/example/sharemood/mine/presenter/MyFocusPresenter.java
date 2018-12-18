package com.example.sharemood.mine.presenter;

import com.example.sharemood.base.BasePresenter;
import com.example.sharemood.bean.DiaryShareBean;
import com.example.sharemood.mine.activity.MyFocusActivity;
import com.example.sharemood.ui.login.Bean.MyUserBean;
import com.example.sharemood.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by acer on 2018/12/18.
 */

public class MyFocusPresenter extends BasePresenter<MyFocusActivity> {
    /**
     * 查询我喜欢关注的帖子
     */
    List<DiaryShareBean> diaryShareBeanList;
    BmobUser user=BmobUser.getCurrentUser(MyUserBean.class);
    public void queryPostMylike() {
        diaryShareBeanList=new ArrayList<>();
        if (BmobUser.isLogin()) {
            BmobQuery<DiaryShareBean> query = new BmobQuery<>();
//            query.addWhereEqualTo("myUserBean", BmobUser.getCurrentUser(MyUserBean.class));
            query.order("-updatedAt");
            //包含作者信息
            query.include("myUserBean");
            query.include("objectLike");
            query.findObjects(new FindListener<DiaryShareBean>() {
                @Override
                public void done(List<DiaryShareBean> object, BmobException e) {
                    if (e == null) {
                        for (int i=0;i<object.size();i++){
                            if (object.get(i).getObjectLike()!=null){
                                if (object.get(i).getObjectLike().size()>0){
                                    for (int j=0;j<object.get(i).getObjectLike().size();j++){
                                        if (object.get(i).getObjectLike().get(j).equals(user.getObjectId())){
                                            if (object.get(i)!=null){
                                                diaryShareBeanList.add(object.get(i));
                                                break;
                                            }
                                        }
                                    };
                                }
                            }

                        }
                        getV().initContentView(diaryShareBeanList);
                    } else {
                        ToastUtil.showShort("查询失败" + e.getMessage());
                    }
                }
            });
        } else {
            ToastUtil.showShort("请先登录");
        }
    }
}
