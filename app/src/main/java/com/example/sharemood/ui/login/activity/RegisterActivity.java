package com.example.sharemood.ui.login.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sharemood.R;
import com.example.sharemood.base.BaseActivity;
import com.example.sharemood.view.BackLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.router.Router;

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.ll_toolbar_left)
    BackLinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
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
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.iv_clean_name)
    ImageView ivCleanName;
    @BindView(R.id.tv_tologin)
    TextView tvTologin;

    @Override
    public void initData(Bundle savedInstanceState) {
        tvToolbarTitle.setText("注册");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public Object newP() {
        return null;
    }

    public static void toRegisterActivity(Activity activity) {
        Router.newIntent(activity).to(RegisterActivity.class).launch();
    }
    @OnClick({R.id.bt_confirm, R.id.iv_clean, R.id.iv_see_pw, R.id.iv_cf_see_pw, R.id.tv_getcode, R.id.iv_clean_name, R.id.tv_tologin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_confirm:
                break;
            case R.id.iv_clean:
                break;
            case R.id.iv_see_pw:
                break;
            case R.id.iv_cf_see_pw:
                break;
            case R.id.tv_getcode:
                break;
            case R.id.iv_clean_name:
                break;
            case R.id.tv_tologin:
                break;
        }
    }
}
