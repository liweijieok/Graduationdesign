package com.liweijie.design.graduation.gallery.app;

import android.content.Context;

import com.liweijie.design.graduation.gallery.bean.app.GalleryUser;

/**
 * Created by liweijie on 2016/5/18.
 * 全局app变量控制
 */
public class App {
    private static Context mContext;
    private final static GalleryUser user = new GalleryUser();

    public static void init(Context context) {
        if (mContext == null) {
            mContext = context;
        }
    }

    public static Context me(){
        if (mContext == null) {
            throw new IllegalStateException("context not allow null");
        }
        return mContext;
    }

    public static void setUser(GalleryUser user) {

    }

    public static GalleryUser user(){
        return user;
    }
}
