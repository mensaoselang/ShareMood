package com.example.sharemood.utils;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.sharemood.R;

public class SystemUtil {

    private static boolean isChecked = true;

    public static void isShowPassword(ImageView ivShowPassword, EditText etLoginPassword) {
        if (isChecked) {
            //如果选中，显示密码
            ivShowPassword.setImageResource(R.mipmap.ic_show_password_in);
            etLoginPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            isChecked = false;
        } else {
            //否则隐藏密码
            ivShowPassword.setImageResource(R.mipmap.ic_show_password);
            etLoginPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            isChecked = true;
        }
    }
}