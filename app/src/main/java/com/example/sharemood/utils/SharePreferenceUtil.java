package com.example.sharemood.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.sharemood.App;
import com.example.sharemood.R;

/**
 * Created by Orz013 on 2018/4/14.
 */

public class SharePreferenceUtil {

    private static final String SHAREDPREFERENCES_NAME = "sp_" + R.string.app_name;

    public static SharedPreferences getAppSp() {
        return App.getInstance().getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
    }
    public static void setUserInfo(String name,String nickName,String phone,String password,String toKen,Boolean checkBox){
        getAppSp().edit().putString(Constants.SM_NAME,name).commit();
        getAppSp().edit().putString(Constants.SM_NickNAME,nickName).commit();
        getAppSp().edit().putString(Constants.SM_PHONE,phone).commit();
        getAppSp().edit().putString(Constants.SM_PASSWORD,password).commit();
        getAppSp().edit().putString(Constants.SM_TOKEN,toKen).commit();
        getAppSp().edit().putBoolean(Constants.SM_CHECKBOX,checkBox).commit();
    }
    public static String getName(){
        return getAppSp().getString(Constants.SM_NAME,"");
    }
    public static String getNickName(){
        return getAppSp().getString(Constants.SM_NickNAME,"");
    }
    public static String getPhone(){
        return getAppSp().getString(Constants.SM_PHONE,"");
    }
    public static String getPassWord(){
        return getAppSp().getString(Constants.SM_PASSWORD,"");
    }
    public static String getToKen(){
        return getAppSp().getString(Constants.SM_TOKEN,"");
    }
    public static boolean getCheckBox(){
        return getAppSp().getBoolean(Constants.SM_CHECKBOX,false);
    }
    public static String getObjectId(){
        return getAppSp().getString(Constants.SM_OBJECTID,"");
    }
    public static void setObjectId(String obJectId){
        getAppSp().edit().putString(Constants.SM_OBJECTID,obJectId).commit();
    }
    public static String getHeardImagePath(){
        return getAppSp().getString(Constants.SM_HEARTIMAGEPATH,"");
    }
    public static void setHeardImagePath(String imagePath){
        getAppSp().edit().putString(Constants.SM_HEARTIMAGEPATH,imagePath).commit();
    }
}