package com.example.sharemood.ui.login.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sharemood.R;
import com.example.sharemood.base.BaseActivity;
import com.example.sharemood.ui.login.presenter.ModifyPasswordPresenter;
import com.example.sharemood.utils.CountDownTimerUtils;
import com.example.sharemood.utils.EditTextCheckUtil;
import com.example.sharemood.utils.IEditTextChangeListener;
import com.example.sharemood.utils.SystemUtil;
import com.example.sharemood.utils.ToastUtil;
import com.example.sharemood.view.BackLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.router.Router;

public class ModifyPasswordActivity extends BaseActivity <ModifyPasswordPresenter>{
    @BindView(R.id.ll_toolbar_left)
    BackLinearLayout llToolbarLeft;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.et_pw)
    EditText etPw;
    @BindView(R.id.et_confirm_pw)
    EditText etConfirmPw;
    @BindView(R.id.bt_confirm)
    Button btConfirm;
    @BindView(R.id.iv_clean)
    ImageView ivClean;
    @BindView(R.id.iv_see_pw)
    ImageView ivSeePw;
    @BindView(R.id.iv_cf_see_pw)
    ImageView ivCfSeePw;
    @BindView(R.id.tv_getcode)
    TextView tvGetcode;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;

    private CountDownTimerUtils mCountDownTimerUtils;
    @Override
    public void initData(Bundle savedInstanceState) {
        tvToolbarTitle.setText("忘记密码");
        //改变按钮状态
        ButtonChange();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_modify_password;
    }

    @Override
    public ModifyPasswordPresenter newP() {
        return new ModifyPasswordPresenter();
    }

    public static void toModifyPasswordActivity(Activity activity) {
        Router.newIntent(activity).to(ModifyPasswordActivity.class).launch();
    }

    @OnClick({R.id.bt_confirm, R.id.iv_clean, R.id.iv_see_pw, R.id.iv_cf_see_pw, R.id.tv_getcode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_confirm:
                String phoneNumber=etAccount.getText().toString().trim();
                String code=etCode.getText().toString().trim();
                String password=etPw.getText().toString().trim();
                String confirmPassword=etConfirmPw.getText().toString().trim();
                //重置密码
                getP().resetPassword(phoneNumber,code,password,confirmPassword);
                break;
            case R.id.iv_clean:
                etAccount.setText("");
                break;
            case R.id.iv_see_pw:
                SystemUtil.isShowPassword(ivSeePw, etPw);
                break;
            case R.id.iv_cf_see_pw:
                SystemUtil.isShowPassword(ivCfSeePw, etConfirmPw);
                break;
            case R.id.tv_getcode:
                getP().getSmsCode(etAccount.getText().toString().trim());
                break;
        }
    }
    //根据是否有内容改变登陆按钮状态
    public void ButtonChange(){
        //1.创建工具类对象 把要改变颜色的tv先传过去
        EditTextCheckUtil.buttonChangeListener buttonChangeListener= new EditTextCheckUtil.buttonChangeListener(btConfirm);
        //2.把所有要监听的edittext都添加进去
        buttonChangeListener.addAllEditText(etAccount, etCode,etPw,etConfirmPw);

        //3.接口回调 在这里拿到boolean变量 根据isHasContent的值决定 tv 应该设置什么颜色
        EditTextCheckUtil.setChangeListener(new IEditTextChangeListener() {
            @Override
            public void textChange(boolean isHasContent) {
                if (isHasContent) {
                    btConfirm.setTextColor(Color.parseColor("#ffffff"));
                    btConfirm.setBackgroundResource(R.drawable.button_blue_bg);
                } else {
                    btConfirm.setTextColor(Color.parseColor("#333333"));
                    btConfirm.setBackgroundResource(R.drawable.button_gray_bg);
                }
            }
        });
    }
    //设置验证码倒计时背景
    public void setSmscode(Integer smscode){
        doStartCountDown();
        ToastUtil.showShort(smscode+"验证码");
    }
    /**
     * 验证码倒数计时
     */
    public void doStartCountDown() {
        if (mCountDownTimerUtils == null) {
            mCountDownTimerUtils = new CountDownTimerUtils(tvGetcode, 60000, 1000);
        }
        mCountDownTimerUtils.start();
    }
}
