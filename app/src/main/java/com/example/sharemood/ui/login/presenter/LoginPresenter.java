package com.example.sharemood.ui.login.presenter;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.sharemood.R;
import com.example.sharemood.base.BasePresenter;
import com.example.sharemood.ui.login.Bean.MyUserBean;
import com.example.sharemood.ui.login.activity.LoginActivity;
import com.example.sharemood.utils.EditTextCheckUtil;
import com.example.sharemood.utils.IEditTextChangeListener;
import com.example.sharemood.utils.ToastUtil;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.droidlover.xdroidmvp.kit.Kits;

/**
 * Created by acer on 2018/11/17.
 */

public class LoginPresenter extends BasePresenter<LoginActivity> {

    /**
     * 登录
     */
    public void login(String userName,String passWord) {
        if (TextUtils.isEmpty(userName)) {
            ToastUtil.showShort("请输入手机号码");
            return;
        }
        if (!Kits.Regular.isPhoneNumber(userName)) {
            ToastUtil.showShort("手机号码不合法");
            return;
        }
        if (TextUtils.isEmpty(passWord)) {
            ToastUtil.showShort("密码不能为空");
            return;
        }
        if (passWord.length() < 6) {
            ToastUtil.showShort("请输入至少6位密码");
            return;
        }

        final MyUserBean myUserBean=new MyUserBean();
        myUserBean.setUsername(userName);
        myUserBean.setPassword(passWord);
        myUserBean.login(new SaveListener<MyUserBean>() {
            @Override
            public void done(MyUserBean myUserBean, BmobException e) {
                if (e == null) {
                    getV().loginSucceed(myUserBean);
//                    MyUserBean myUserBean1 = BmobUser.getCurrentUser(MyUserBean.class);
                } else {
                    ToastUtil.showShort("登陆失败"+e.getMessage());
                }
            }
        });
    }

    //根据是否有内容改变登陆按钮状态
    public void loginButtonChange(final Button button, EditText etLoginAccount, EditText etLoginPassword){
        if (!TextUtils.isEmpty(etLoginAccount.getText().toString().trim())&&!TextUtils.isEmpty(etLoginPassword.getText().toString().trim())) {
            button.setTextColor(Color.parseColor("#ffffff"));
            button.setBackgroundResource(R.drawable.button_blue_bg);
        } else {
            button.setTextColor(Color.parseColor("#2DB4FF"));
            button.setBackgroundResource(R.drawable.button_white_bg);
        }
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
