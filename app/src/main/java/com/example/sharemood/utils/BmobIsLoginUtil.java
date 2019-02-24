package com.example.sharemood.utils;

import android.app.Activity;
import android.content.Context;

import com.example.sharemood.ui.login.activity.LoginActivity;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

/**
 * Created by acer on 2019/1/15.
 */

public class BmobIsLoginUtil {
    private volatile static BmobIsLoginUtil intstance;
    private BmobIsLoginUtil(){}
    public static BmobIsLoginUtil getIntstance(){
        if (intstance==null){
            synchronized (BmobIsLoginUtil.class){
                if (intstance==null){
                    intstance=new BmobIsLoginUtil();
                }
            }
        }
        return intstance;
    }
    public boolean isLogin(Activity activity){
        if(!BmobUser.isLogin()){
            ToastUtil.showShort("请先登陆");
            activity.finish();
            LoginActivity.toLoginActivity(activity);
            return false;
        }else {
            return true;
        }
    }
}
