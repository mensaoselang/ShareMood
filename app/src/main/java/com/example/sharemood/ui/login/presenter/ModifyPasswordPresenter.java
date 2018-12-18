package com.example.sharemood.ui.login.presenter;

import android.text.TextUtils;

import com.example.sharemood.base.BasePresenter;
import com.example.sharemood.ui.login.Bean.MyUserBean;
import com.example.sharemood.ui.login.activity.ModifyPasswordActivity;
import com.example.sharemood.utils.ToastUtil;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.droidlover.xdroidmvp.kit.Kits;

/**
 * Created by acer on 2018/11/20.
 */

public class ModifyPasswordPresenter extends BasePresenter<ModifyPasswordActivity>{

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

    //重置密码
    public void resetPassword(String phoneNumber,String smsCode,String password ,String confirmPassword) {
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
        /**
         * 验证码重置密码
         * @param security_code 短信验证码
         * @param new_pwd 新密码
         * @param listener 回调
         */

        BmobUser.resetPasswordBySMSCode(smsCode, confirmPassword, new UpdateListener() {
            @Override
            public void done(BmobException e) {
             if (e==null){
                 ToastUtil.showShort("修改密码成功");
             }else {
                 ToastUtil.showShort("修改失败:"+e.getMessage());
             }
            }
        });
    }

}
