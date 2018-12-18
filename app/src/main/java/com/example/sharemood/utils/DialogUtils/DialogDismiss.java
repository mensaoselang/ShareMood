package com.example.sharemood.utils.DialogUtils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by acer on 2018/10/11.
 */

public class DialogDismiss {
    Handler handler;
    private Context mContext;

    public DialogDismiss(Context context) {
        this.mContext = context;
    }

    /**
     * 显示菊花以及菊花下方的文字提示，点击外部不可取消，点击返回可以取消
     * 不接收回调接收回调
     */
    public void dismiss(final DialogUtils dialogUtils) {
        //显示超过两秒关闭
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message message= new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        }, 2000);
          handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {      //判断标志位
                    case 1:
                        dialogUtils.dismissLoading();
//                    ToastUtil.showShort("登陆失败");
                        /**
                         获取数据，更新UI
                         */
                        break;
                }
            }
        };
        //执行完handle后移除
        handler.removeCallbacks(null);
    }
}
