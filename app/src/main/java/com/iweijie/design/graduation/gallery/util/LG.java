package com.iweijie.design.graduation.gallery.util;

import android.util.Log;

import com.iweijie.design.graduation.gallery.app.GalleryConstants;

/**
 * Created by liweijie on 2016/5/18.
 */
public class LG {
    private  String TAG;

    public LG(Class clazz) {
        TAG = clazz.getSimpleName();
    }

    public  void i(String msg) {
        if (!GalleryConstants.IS_DEBUG) {
            return;
        }
        Log.i(TAG, msg);
    }

    public  void d(String msg) {
        if (!GalleryConstants.IS_DEBUG) {
            return;
        }
        Log.d(TAG, msg);
    }

    public void e(String msg) {
        if (!GalleryConstants.IS_DEBUG) {
            return;
        }
        Log.e(TAG, msg);
    }

    public void w(String msg) {
        if (!GalleryConstants.IS_DEBUG) {
            return;
        }
        Log.w(TAG, msg);
    }

    public  void v(String msg) {
        if (!GalleryConstants.IS_DEBUG) {
            return;
        }
        Log.v(TAG, msg);
    }
}
