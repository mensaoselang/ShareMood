package com.example.sharemood.ui.login.Bean;

import android.net.Uri;

import com.example.sharemood.bean.DiaryShareBean;

import java.io.Serializable;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by acer on 2018/11/20.
 */

public class MyUserBean extends BmobUser implements Serializable{
    //扩展一个昵称值
   private String nickName;
//   private BmobFile imageFile;
   private String sex;
   private String quotations;
   private String imagePath;
   private String phone;

   private DiaryShareBean diaryShareBean;

    public DiaryShareBean getDiaryShareBean() {
        return diaryShareBean;
    }

    public MyUserBean setDiaryShareBean(DiaryShareBean diaryShareBean) {
        this.diaryShareBean = diaryShareBean;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getQuotations() {
        return quotations;
    }

    public void setQuotations(String quotations) {
        this.quotations = quotations;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
//    public BmobFile getImageFile() {
//        return imageFile;
//    }
//
//    public void setImageFile(BmobFile imageFile) {
//        this.imageFile = imageFile;
//    }
}
