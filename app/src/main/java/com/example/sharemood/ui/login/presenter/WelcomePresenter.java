package com.example.sharemood.ui.login.presenter;

import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.example.sharemood.base.BasePresenter;
import com.example.sharemood.ui.login.activity.WelcomeActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by acer on 2018/11/16.
 */

public class WelcomePresenter extends BasePresenter<WelcomeActivity> {
    private int countdown = 5;//跳过倒计时提示5秒
    Timer timer = new Timer();
    public void welcomeCountDown(){
        timer.schedule(task,1000,1000);
    }
    TimerTask task=new TimerTask() {
        @Override
        public void run() {
            getV().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    countdown--;
                    getV().setText(countdown);
                    if (countdown<0){
                        getV().toOtherActivity();
                    }
                }
            });
        }
    };
    public void cancelCountDown(){
        timer.cancel();
    }
}
