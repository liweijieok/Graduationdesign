package com.liweijie.design.graduation.gallery;

import android.app.Application;

import com.liweijie.design.graduation.gallery.app.App;

/**
 * Created by liweijie on 2016/5/8.
 * GalleryApplication
 */
public class GalleryApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initBase();


    }

    private void initBase() {
        App.init(this);
    }
}
