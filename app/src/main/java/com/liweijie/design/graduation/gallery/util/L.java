package com.liweijie.design.graduation.gallery.util;

import android.util.Log;

import com.liweijie.design.graduation.gallery.app.GalleryConstants;

/**
 * Created by liweijie on 2016/5/18.
 */
public class L {

    public static void i(String msg) {
        if (!GalleryConstants.IS_DEBUG) {
            return;
        }
        Log.i("gallery-->info", msg);
    }

    public static void d(String msg) {
        if (!GalleryConstants.IS_DEBUG) {
            return;
        }
        Log.d("gallery-->debug", msg);
    }

    public static void e(String msg) {
        if (!GalleryConstants.IS_DEBUG) {
            return;
        }
        Log.e("gallery--error>", msg);
    }

    public static void w(String msg) {
        if (!GalleryConstants.IS_DEBUG) {
            return;
        }
        Log.w("gallery-->warn", msg);
    }

    public static void v(String msg) {
        if (!GalleryConstants.IS_DEBUG) {
            return;
        }
        Log.v("gallery-->verbose", msg);
    }
}
