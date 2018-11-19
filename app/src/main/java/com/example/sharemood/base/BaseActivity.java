package com.example.sharemood.base;

import android.content.res.Configuration;

import com.gyf.barlibrary.ImmersionBar;

import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.mvp.XPresent;

public abstract class BaseActivity<P extends XPresent> extends XActivity<P> {
    //设置状态栏
    public void setUpTitleBar(){
        // 所有子类都将继承这些相同的属性,请在设置界面之后设置
        ImmersionBar.with(this)
                .statusBarDarkFont(true)//状态栏字体是深色，不写默认为亮色
                .init();

    };
    //销毁状态栏，防止内存泄漏
    public void destroyTitleBar(){
    // 必须调用该方法，防止内存泄漏
        ImmersionBar.with(this).destroy();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        // 如果你的app可以横竖屏切换，并且适配4.4或者emui3手机请务必在onConfigurationChanged方法里添加这句话
        ImmersionBar.with(this).init();
    }
}