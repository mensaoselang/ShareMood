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
//    public static void setDoctorMobile(String Mobile){
//        getAppSp().edit().putString(Constants.Mobile,Mobile).commit();
//    }
//    public static String getDoctorMobile(){
//        return getAppSp().getString(Constants.Mobile,"");
//    }
//    public static void setHistorSearch(String keyword) {
//        getAppSp().edit().putString(Constants.HISTORY_SEARCH, keyword).commit();
//    }
//    public static void setDoctorWay(String way){
//        getAppSp().edit().putString(Constants.DOCTORWAY,way).commit();
//    }
//    public static void setDoctorName(String way){
//        getAppSp().edit().putString(Constants.DOCTORNAME,way).commit();
//    }
//    public static String getDoctorWay(){
//        return getAppSp().getString(Constants.DOCTORWAY,"");
//    }
//    public static String getDoctorName(){
//        return getAppSp().getString(Constants.DOCTORNAME,"");
//    }
//    public static String getHistorSearch(){
//        return getAppSp().getString(Constants.HISTORY_SEARCH,"");
//    }
//    public static void setLoginSucceed(boolean isSucceed) {
//        getAppSp().edit().putBoolean(Constants.SP_ISSUCCEED, isSucceed).commit();
//    }
//    public static void setLocationLat(String currentLat){
//        getAppSp().edit().putString(Constants.PU_LocationLat, currentLat).commit();
//    }
//    public static void setLocationLon( String currentLon){
//        getAppSp().edit().putString(Constants.PU_LocationLon, currentLon).commit();
//    }
//    public static String getLoactionLat(){
//        return getAppSp().getString(Constants.PU_LocationLat, "");
//    }
//    public static String getLoactionLon(){
//        return getAppSp().getString(Constants.PU_LocationLon, "");
//    }
//    public static void setLoginInfo(int id, String loginToken, String memberOpenId, String mobile, String isBindMobile, String headUrl, String sex) {
//        getAppSp().edit().putInt(Constants.SP_MEMID, id).commit();
//        getAppSp().edit().putString(Constants.SP_LOGINTOCKEN, loginToken).commit();
//        getAppSp().edit().putString(Constants.SP_MEMBEROPENID, memberOpenId).commit();
//        getAppSp().edit().putString(Constants.SP_MEM_MOBILE, mobile).commit();
//        getAppSp().edit().putString(Constants.isBindMobile, isBindMobile).commit();
//        getAppSp().edit().putString(Constants.SP_HEADURL, headUrl).commit();
//        getAppSp().edit().putString(Constants.SP_SEX, sex).commit();
//    }
//
//
//    public static void setOpenID(String openID) {
//        getAppSp().edit().putString(Constants.SP_OPENID, openID).commit();
//    }
//
//    public static String getOpenID() {
//        return getAppSp().getString(Constants.SP_OPENID, "");
//    }
//
//    public static int getMemId() {
//        return getAppSp().getInt(Constants.SP_MEMID, 0);
//    }
//
//    public static String getIsBindMobile() {
//        return getAppSp().getString(Constants.isBindMobile, "");
//    }
//
//    public static String getLoginTocken() {
//        return getAppSp().getString(Constants.SP_LOGINTOCKEN, "");
//    }
//
//    public static String getMemberOpenId() {
//        return getAppSp().getString(Constants.SP_MEMBEROPENID, "");
//    }
//
//    public static String getMobile() {
//        return getAppSp().getString(Constants.SP_MEM_MOBILE, "");
//    }
//
//    public static Boolean getLoginSucceed() {
//        return getAppSp().getBoolean(Constants.SP_ISSUCCEED, false);
//    }
//
//    public static String getHeadUrl() {
//        return getAppSp().getString(Constants.SP_HEADURL, "");
//    }
//
//    public static String getSex() {
//        return getAppSp().getString(Constants.SP_SEX, "");
//    }
//    //保存咨询医生的id
//    public static void setChatInfo(String id,String name){
//        getAppSp().edit().putString(Constants.CHATID,id).commit();
//        getAppSp().edit().putString(Constants.CHATNAME,name).commit();
//    }
//   public static String getChatId(){
//        return getAppSp().getString(Constants.CHATID,"");
//   }
//   public static String getChatName(){
//       return getAppSp().getString(Constants.CHATNAME,"");
//   }
//
//    public static void setPublicInfo(String name,String mainPic,String phone,String qqNumber,String aboutUs){
//        getAppSp().edit().putString(Constants.PU_NAME, name).commit();
//        getAppSp().edit().putString(Constants.PU_MAINPIC, mainPic).commit();
//        getAppSp().edit().putString(Constants.PU_PHONE, phone).commit();
//        getAppSp().edit().putString(Constants.PU_QQNUMBER, qqNumber).commit();
//        getAppSp().edit().putString(Constants.PU_ABOUTUS, aboutUs).commit();
//    }
//
//    public static String getPuName(){
//        return getAppSp().getString(Constants.PU_NAME, "");
//    }
//    public static String getPuMainPic(){
//        return getAppSp().getString(Constants.PU_MAINPIC, "");
//    }
//    public static String getPuPhone(){
//        return getAppSp().getString(Constants.PU_PHONE, "");
//    }
//    public static String getPuQQNumber(){
//        return getAppSp().getString(Constants.PU_QQNUMBER, "");
//    }
//    public static String getPuAbout(){
//        return getAppSp().getString(Constants.PU_ABOUTUS, "");
//    }
}