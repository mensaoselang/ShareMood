package com.example.sharemood.ui.login.presenter;

import android.text.TextUtils;

import com.example.sharemood.base.BasePresenter;
import com.example.sharemood.bean.MoodTypeBean;
import com.example.sharemood.chart.bean.PieChartMoodBean;
import com.example.sharemood.ui.login.Bean.MyUserBean;
import com.example.sharemood.ui.login.activity.RegisterActivity;
import com.example.sharemood.utils.ToastUtil;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.droidlover.xdroidmvp.kit.Kits;

/**
 * Created by acer on 2018/11/20.
 */

public class RegisterPresenter extends BasePresenter<RegisterActivity>{

    //注册
    public void singUp(String nickName,String phoneNumber,String smsCode,String password,String confirmPassword){

        if (TextUtils.isEmpty(nickName)) {
            ToastUtil.showShort("请输入昵称");
            return;
        }
        if (TextUtils.isEmpty(phoneNumber)) {
            ToastUtil.showShort("请输入手机号码");
            return;
        }
        if (!Kits.Regular.isPhoneNumber(phoneNumber)) {
            ToastUtil.showShort("手机号码不合法");
            return;
        }
        if (TextUtils.isEmpty(smsCode)) {
            ToastUtil.showShort("请输入验证码");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtil.showShort("密码不能为空");
            return;
        }
        if (password.length() < 6) {
            ToastUtil.showShort("请输入至少6位密码");
            return;
        }
        if (!TextUtils.equals(confirmPassword, password)) {
            ToastUtil.showShort("两次密码输入不一致");
            return;
        }
        MyUserBean myUserBean=new MyUserBean();
        myUserBean.setNickName(nickName);//昵称
        myUserBean.setUsername(phoneNumber);//用手机号作为用户名
        myUserBean.setMobilePhoneNumber(phoneNumber);//手机号
        myUserBean.setPassword(password);//密码
        //手机验证码注册
       myUserBean.signOrLogin(smsCode,new SaveListener<MyUserBean>() {
           @Override
           public void done(MyUserBean myUserBean, BmobException e) {
              if (e==null){
                  ToastUtil.showShort("注册成功");
                  getTitleData(myUserBean);//获取首页类型列表并给新用户建一个默认心情分享比重表
                  getV().singupSucceed();
              }else {
                  ToastUtil.showShort("注册失败"+e.getMessage());
              }
           }
       });
    }

    //获取验证码
    public void getSmsCode(String phone){
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.showShort("手机号码不能为空");
            return;
        }
        if (!Kits.Regular.isPhoneNumber(phone)) {
            ToastUtil.showShort("手机号码不合法");
            return;
        }
        /**
         * TODO template 如果是自定义短信模板，此处替换为你在控制台设置的自定义短信模板名称；如果没有对应的自定义短信模板，则使用默认短信模板。
         */
        BmobSMS.requestSMSCode(phone, "", new QueryListener<Integer>() {
            @Override
            public void done(Integer smsId, BmobException e) {
                if (e == null) {
                    //验证码倒计时
                    getV().setSmscode(smsId);
                    ToastUtil.showShort("发送验证码成功，短信ID：" + smsId + "\n");
                } else {
                    ToastUtil.showShort("发送验证码失败：" + e.getErrorCode() + "-" + e.getMessage() + "\n");
                }
            }
        });
    }

    public void getTitleData(final MyUserBean myUserBean){
        BmobQuery<MoodTypeBean> typeQuery=new BmobQuery<>();
        typeQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);   // 先从缓存获取数据，如果没有，再从网络获取。
        typeQuery.findObjects(new FindListener<MoodTypeBean>() {
            @Override
            public void done(List<MoodTypeBean> list, BmobException e) {
                if (e==null){
                    if (list.size()>0){
                        for (int i=0;i<list.size();i++){

                            //添加关联，用户关联帖子;
                            PieChartMoodBean pieChartMoodBean=new PieChartMoodBean();
                            pieChartMoodBean.setType(list.get(i).getType());
                            pieChartMoodBean.setSum(0);
                            pieChartMoodBean.setTypeId(list.get(i).getId());
                            pieChartMoodBean.setColor(list.get(i).getColor());
                            pieChartMoodBean.setMyUserBean(myUserBean);
                            pieChartMoodBean.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if (e!=null){
                                        ToastUtil.showShort("心情分享比重建表失败"+e.getMessage());
                                    }
                                }
                            });

                        }
                    }
                }else {
                    ToastUtil.showShort("获取首页类型表失败");
                }

            }
        });
    }
}
