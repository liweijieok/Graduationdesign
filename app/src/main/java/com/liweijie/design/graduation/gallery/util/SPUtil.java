package com.liweijie.design.graduation.gallery.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.liweijie.design.graduation.gallery.app.App;

/**
 * Created by liweijie on 2016/5/18.
 */
public class SPUtil {
    private static final String AP_NAME = "gallery";

    public static boolean get(String key, boolean defaultValue) {
        SharedPreferences sp = App.me().getSharedPreferences(AP_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }

    public static boolean getBoolean(String key) {
        return get(key, false);
    }

    public static void set(String key, boolean value) {
        SharedPreferences sp = App.me().getSharedPreferences(AP_NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).apply();
    }


    public static String get(String key, String defaultValue) {
        SharedPreferences sp = App.me().getSharedPreferences(AP_NAME, Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

    public static String getString(String key) {
        return get(key,  null);
    }

    public static void set(String key, String value) {
        SharedPreferences sp = App.me().getSharedPreferences(AP_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).apply();
    }

    public static int get(String key, int defaultValue) {
        SharedPreferences sp = App.me().getSharedPreferences(AP_NAME, Context.MODE_PRIVATE);
        return sp.getInt(key, defaultValue);
    }

    public static int getInt(String key) {
        return get(key, -1);
    }

    public static void set(String key, int value) {
        SharedPreferences sp = App.me().getSharedPreferences(AP_NAME, Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).apply();
    }


}
