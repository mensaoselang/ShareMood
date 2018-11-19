package com.example.sharemood.ui.login.presenter;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.sharemood.R;
import com.example.sharemood.base.BasePresenter;
import com.example.sharemood.ui.login.activity.LoginActivity;
import com.example.sharemood.utils.EditTextCheckUtil;
import com.example.sharemood.utils.IEditTextChangeListener;

/**
 * Created by acer on 2018/11/17.
 */

public class LoginPresenter extends BasePresenter<LoginActivity> {
    //根据是否有内容改变登陆按钮状态
    public void loginButtonChange(final Button button, EditText etLoginAccount, EditText etLoginPassword){
        //1.创建工具类对象 把要改变颜色的tv先传过去
        EditTextCheckUtil.buttonChangeListener buttonChangeListener= new EditTextCheckUtil.buttonChangeListener(button);
        //2.把所有要监听的edittext都添加进去
        buttonChangeListener.addAllEditText(etLoginAccount, etLoginPassword);

        //3.接口回调 在这里拿到boolean变量 根据isHasContent的值决定 tv 应该设置什么颜色
        EditTextCheckUtil.setChangeListener(new IEditTextChangeListener() {
            @Override
            public void textChange(boolean isHasContent) {
                if (isHasContent) {
                    button.setTextColor(Color.parseColor("#ffffff"));
                    button.setBackgroundResource(R.drawable.button_blue_bg);
                } else {
                    button.setTextColor(Color.parseColor("#2DB4FF"));
                    button.setBackgroundResource(R.drawable.button_white_bg);
                }
            }
        });
    }
    //监听手机号是否输入显示清除图标
    public void showIcCleanUp(final EditText etLoginAccount, final EditText etLoginPassword, final ImageView ivCleanUp){
        /**
         * 监听edittext是否输入
         * @param editText
         * @param TAG
         */
        etLoginAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) ivCleanUp.setVisibility(View.VISIBLE);
                else ivCleanUp.setVisibility(View.INVISIBLE);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etLoginAccount.getText().toString().isEmpty()) {
                    etLoginPassword.setText(null);
                }
            }
        });

    }
}
