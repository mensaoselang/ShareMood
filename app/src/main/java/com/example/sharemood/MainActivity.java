package com.example.sharemood;
import android.app.Activity;
import android.os.Bundle;

import com.example.sharemood.base.BaseActivity;

import cn.droidlover.xdroidmvp.router.Router;

public class MainActivity extends BaseActivity {
    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public Object newP() {
        return null;
    }
    public static void toMainActivity(Activity activity){
        Router.newIntent(activity).to(MainActivity.class).launch();
    }
}
