package com.example.sharemood.ui.login.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import com.example.sharemood.ui.login.Bean.MyUserBean;
import com.example.sharemood.ui.login.presenter.LoginPresenter;
import com.example.sharemood.utils.SharePreferenceUtil;
import com.example.sharemood.utils.SystemUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
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
        if (SharePreferenceUtil.getCheckBox()){
            cbRbPassword.setChecked(true);
        }else {
            cbRbPassword.setChecked(false);
        }
        etAccount.setText(SharePreferenceUtil.getPhone());
        etPassword.setText(SharePreferenceUtil.getPassWord());

        //显示清除图标
        getP().showIcCleanUp(etAccount,etPassword,ivCleanUp);
        //改变登陆按钮状态
        getP().loginButtonChange(btLogin,etAccount,etPassword);
        if (!"".equals(SharePreferenceUtil.getPassWord())&&!"".equals(SharePreferenceUtil.getPhone())){
            getP().login(SharePreferenceUtil.getPhone(),SharePreferenceUtil.getPassWord());
        }
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
    @OnClick({R.id.bt_login, R.id.tv_forget_password, R.id.tv_registered, R.id.tv_qq, R.id.tv_weixin,R.id.iv_clean_up,R.id.iv_show_password,R.id.cb_rb_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                String userName=etAccount.getText().toString().trim();
                String password=etPassword.getText().toString().trim();
                getP().login(userName,password);
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
            case R.id.cb_rb_password:
                break;
        }
    }
    //登陆成功
    public void loginSucceed(MyUserBean myUserBean){
        String name=myUserBean.getUsername();
        String nickName=myUserBean.getNickName();
        String phone=myUserBean.getMobilePhoneNumber();
        String password=etPassword.getText().toString().trim();
        String sessionToken=myUserBean.getSessionToken();
        if (cbRbPassword.isChecked()){
            SharePreferenceUtil.setUserInfo(name,nickName,phone,password,sessionToken,true);
        }else {
            SharePreferenceUtil.setUserInfo(name,nickName,phone,"",sessionToken,false);
        }
        BmobUser bmobUser=BmobUser.getCurrentUser(MyUserBean.class);
        SharePreferenceUtil.setObjectId(bmobUser.getObjectId());
        MainActivity.toMainActivity(this);
        finish();
    }
}
