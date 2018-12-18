package com.example.sharemood;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;

import com.example.sharemood.network.NetChangeObserver;
import com.example.sharemood.network.NetStateReceiver;
import com.example.sharemood.network.NetUtils;
import com.example.sharemood.utils.ToastUtil;

import org.litepal.LitePal;

import java.nio.channels.NetworkChannel;

import cn.bmob.v3.Bmob;

public class App extends Application {
    private static App instance;
    public static String BMOBAPPID = "4f2d772df1039efd1baffc5f72764832";
    /**
     * 网络观察者
     */
    protected NetChangeObserver mNetChangeObserver = null;

    public static synchronized App getInstance() {
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //第一：默认初始化
        LitePal.initialize(this);//数据库框架初始化
        Bmob.initialize(this,BMOBAPPID);
        /*开启网络广播监听*/
        NetStateReceiver.registerNetworkStateReceiver(instance);
       // LitePal.getDatabase();

        // 网络改变的一个回掉类
        mNetChangeObserver = new NetChangeObserver() {
            @Override
            public void onNetConnected(NetUtils.NetType type) {
             //   ToastUtil.showShort("网络连接正常\n" + type.name());
            }

            @Override
            public void onNetDisConnect() {
                ToastUtil.showShort("网络连接失败");
            }
        };

        //开启广播去监听 网络 改变事件
        NetStateReceiver.registerObserver(mNetChangeObserver);

    }
    @Override
    public void onLowMemory() {
    if (instance != null) {
        NetStateReceiver.unRegisterNetworkStateReceiver(instance);
        android.os.Process.killProcess(android.os.Process.myPid());
     }
    super.onLowMemory();
    }
}
