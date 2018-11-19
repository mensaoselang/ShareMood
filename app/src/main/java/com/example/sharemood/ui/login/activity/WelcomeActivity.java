package com.example.sharemood.ui.login.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.sharemood.R;
import com.example.sharemood.base.BaseActivity;
import com.example.sharemood.ui.login.presenter.WelcomePresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class WelcomeActivity extends BaseActivity<WelcomePresenter> {

    @BindView(R.id.countdown_tv)
    TextView countdownTv;

    @Override
    public void initData(Bundle savedInstanceState) {
        //欢迎页倒计时
        getP().welcomeCountDown();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public WelcomePresenter newP() {
        return new WelcomePresenter();
    }


    public void setText(int countdown) {
        countdownTv.setText("跳过:" + countdown + "秒");
    }

    public void toOtherActivity() {
        getP().cancelCountDown();
        countdownTv.setVisibility(View.GONE);
        LoginActivity.toLoginActivity(WelcomeActivity.this);
        finish();
    }
    @OnClick(R.id.countdown_tv)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.countdown_tv:
                toOtherActivity();
                break;
        }
    }
}
