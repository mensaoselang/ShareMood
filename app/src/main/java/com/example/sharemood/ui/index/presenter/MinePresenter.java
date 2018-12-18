package com.example.sharemood.ui.index.presenter;

import com.example.sharemood.base.BaseFragment;
import com.example.sharemood.base.BasePresenter;
import com.example.sharemood.ui.index.fragment.MineFragment;
import com.example.sharemood.ui.login.Bean.MyUserBean;
import com.example.sharemood.utils.SharePreferenceUtil;
import com.example.sharemood.utils.ToastUtil;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

import static cn.bmob.v3.b.From.e;

/**
 * Created by acer on 2018/12/3.
 */

public class MinePresenter extends BasePresenter <MineFragment>{
    public void getMyData(){
        MyUserBean myUserBean = BmobUser.getCurrentUser(MyUserBean.class);
//        BmobQuery<MyUserBean> bmobQuery = new BmobQuery<MyUserBean>();
//        bmobQuery.getObject(SharePreferenceUtil.getObjectId(), new QueryListener<MyUserBean>() {
//            @Override
//            public void done(MyUserBean myUserBean, BmobException e) {
//                if(e==null){
                    getV().initView(myUserBean);
//                }else{
//                    ToastUtil.showShort("获取个人信息失败:"+e.getMessage());
//                }
//            }
//        });
    }
}
