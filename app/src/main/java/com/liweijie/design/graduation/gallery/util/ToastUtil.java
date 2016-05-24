package com.liweijie.design.graduation.gallery.util;

import android.widget.Toast;

import com.liweijie.design.graduation.gallery.app.App;

/**
 * Created by liweijie on 2016/5/24.
 */
public class ToastUtil {
    public static void showLong(String msg) {
        if (msg == null) {
            return;
        }
        Toast.makeText(App.me(),msg,Toast.LENGTH_LONG).show();
    }
    public static void showLong(int strId) {
        if (strId == 0) {
            return;
        }
        showLong(ResourceUtil.getString(strId));
    }
    public static void showShort(String msg) {
        if (msg == null) {
            return;
        }
        Toast.makeText(App.me(),msg,Toast.LENGTH_SHORT).show();
    }
    public static void showShort(int  strId) {
        if (strId == 0) {
            return;
        }
        showShort(ResourceUtil.getString(strId));
    }
}
