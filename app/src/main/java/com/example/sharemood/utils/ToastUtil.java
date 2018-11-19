package com.example.sharemood.utils;

import android.view.Gravity;
import android.widget.Toast;

import com.example.sharemood.App;


/**
 * Created by Orz013 on 2018/4/14.
 */

public class ToastUtil {
    //居中显示吐司
    public static void showShort(String TsMessage) {
        Toast toast= Toast.makeText(App.getInstance(),TsMessage,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    public static void showLong(String TsMessage) {
        Toast toast= Toast.makeText(App.getInstance(),TsMessage,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
}
