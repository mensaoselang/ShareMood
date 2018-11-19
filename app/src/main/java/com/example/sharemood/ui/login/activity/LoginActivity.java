package com.example.sharemood.ui.login.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sharemood.MainActivity;
import com.example.sharemood.R;
import com.example.sharemood.base.BaseActivity;
import com.example.sharemood.ui.login.presenter.LoginPresenter;
import com.example.sharemood.utils.SystemUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.router.Router;

public class LoginActivity extends BaseActivity <LoginPresenter>{
    @BindView(R.id.cb_rb_password)
    CheckBox cbRbPassword;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.tv_forget_password)
    TextView tvForgetPassword;
    @BindView(R.id.tv_registered)
    TextView tvRegistered;
    @BindView(R.id.tv_qq)
    TextView tvQq;
    @BindView(R.id.tv_weixin)
    TextView tvWeixin;
    @BindView(R.id.iv_clean_up)
    ImageView ivCleanUp;
    @BindView(R.id.iv_show_password)
    ImageView ivShowPassword;

    @Override
    public void initData(Bundle savedInstanceState) {
      initView();
    }
    public void initView(){
        //显示清除图标
        getP().showIcCleanUp(etAccount,etPassword,ivCleanUp);
        //改变登陆按钮状态
        getP().loginButtonChange(btLogin,etAccount,etPassword);
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public LoginPresenter newP() {
        return new LoginPresenter();
    }

    public static void toLoginActivity(Activity activity) {
        Router.newIntent(activity).to(LoginActivity.class).launch();
    }
    @OnClick({R.id.bt_login, R.id.tv_forget_password, R.id.tv_registered, R.id.tv_qq, R.id.tv_weixin,R.id.iv_clean_up,R.id.iv_show_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                MainActivity.toMainActivity(this);
                break;
            case R.id.tv_forget_password:
                ModifyPasswordActivity.toModifyPasswordActivity(this);
                break;
            case R.id.tv_registered:
                RegisterActivity.toRegisterActivity(this);
                break;
            case R.id.tv_qq:
                break;
            case R.id.tv_weixin:
                break;
            case R.id.iv_clean_up:
                etAccount.setText("");
                break;
            case R.id.iv_show_password:
                SystemUtil.isShowPassword(ivShowPassword, etPassword);
                break;
        }
    }
}
