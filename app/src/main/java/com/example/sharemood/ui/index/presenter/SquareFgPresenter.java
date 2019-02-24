package com.example.sharemood.ui.index.presenter;

import com.example.sharemood.base.BasePresenter;
import com.example.sharemood.bean.DiaryShareBean;
import com.example.sharemood.bean.MoodTypeBean;
import com.example.sharemood.ui.index.fragment.SquareFragment;
import com.example.sharemood.ui.login.Bean.MyUserBean;
import com.example.sharemood.utils.BmobIsLoginUtil;
import com.example.sharemood.utils.ToastUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by acer on 2018/12/6.
 */

public class SquareFgPresenter extends BasePresenter<SquareFragment> {
    public void getTitleData(){
        BmobQuery<MoodTypeBean> typeQuery=new BmobQuery<>();
        typeQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);//先从缓存获取数据，如果没有，再从网络获取。
        typeQuery.findObjects(new FindListener<MoodTypeBean>() {
            @Override
            public void done(List<MoodTypeBean> list, BmobException e) {
                getV().initTitleView(list);
            }
        });
    }
    public void getContentData(int typeId,boolean cache){
        BmobQuery<DiaryShareBean> dsbQuery = new BmobQuery<>();
        if (cache){
            dsbQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);   // 先从缓存获取数据，如果没有，再从网络获取。
        }else {
            dsbQuery.clearCachedResult(DiaryShareBean.class);
        }
        dsbQuery.addWhereEqualTo("typeId", typeId);
        //包含作者信息
//        dsbQuery.include("myUserBean");
        dsbQuery.include("objectLike,myUserBean");
        dsbQuery.findObjects(new FindListener<DiaryShareBean>() {
            @Override
            public void done(List<DiaryShareBean> object, BmobException e) {
                if (e == null) {
                    getV().initContentView(object);
                } else {
//                    Log.e("BMOB", e.toString());
                    ToastUtil.showShort("获取数据失败"+e.getMessage());
                }
            }
        });
    }

    public void query(String phone){
        BmobQuery<MyUserBean> userQuery = new BmobQuery<>();
        userQuery.addWhereEqualTo("phone",phone);
        userQuery.findObjects(new FindListener<MyUserBean>() {
            @Override
            public void done(List<MyUserBean> object, BmobException e) {
                if (e == null) {
                } else {
//                    Log.e("BMOB", e.toString());
                    ToastUtil.showShort("获取数据失败"+e.getMessage());
                }
            }
        });
    }
}
