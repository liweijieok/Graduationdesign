package com.liweijie.design.graduation.gallery.util;


import com.liweijie.design.graduation.gallery.R;
import com.liweijie.design.graduation.gallery.app.App;

/**
 * Created by liweijie on 2016/4/9.
 * 获取资源
 */
public class ResourceUtil {
    public static String getString(int id) {
        return App.me().getResources().getString(id);
    }

    public static int getColor(int id) {
        return App.me().getResources().getColor(id);
    }

    public static String[] getStringArray(int id) {
        return App.me().getResources().getStringArray(id);
    }

    public static float getDimen(int id) {
        return App.me().getResources().getDimension(id);
    }

}
