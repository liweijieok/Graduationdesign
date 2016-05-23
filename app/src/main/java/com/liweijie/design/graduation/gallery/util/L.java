package com.liweijie.design.graduation.gallery.util;

import android.util.Log;

import com.liweijie.design.graduation.gallery.app.GalleryConstants;

/**
 * Created by liweijie on 2016/5/18.
 */
public class L {


    public static void i(String tag, String msg) {
        Log.i("gallery-->info" + tag, msg);
    }

    public static void i(String msg) {
        if (!GalleryConstants.IS_DEBUG) {
            return;
        }
        i(null, msg);
    }

    public static void d(String tag, String msg) {
        if (!GalleryConstants.IS_DEBUG) {
            return;
        }
        Log.d("gallery-->debug" + tag, msg);
    }

    public static void d(String msg) {
        if (!GalleryConstants.IS_DEBUG) {
            return;
        }
        d(null, msg);
    }

    public static void e(String tag, String msg) {
        if (!GalleryConstants.IS_DEBUG) {
            return;
        }
        Log.e("gallery--error>" + tag, msg);
    }


    public static void e(String msg) {
        if (!GalleryConstants.IS_DEBUG) {
            return;
        }
        e(null, msg);
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (!GalleryConstants.IS_DEBUG) {
            return;
        }
        Log.e(tag, "error : ", tr);
    }

    public static void w(String tag, String msg) {
        if (!GalleryConstants.IS_DEBUG) {
            return;
        }
        Log.w("gallery-->warn" + tag, msg);
    }

    public static void w(String msg) {
        if (!GalleryConstants.IS_DEBUG) {
            return;
        }
        w(null, msg);
    }

    public static void v(String tag, String msg) {
        if (!GalleryConstants.IS_DEBUG) {
            return;
        }
        Log.v("gallery-->verbose" + tag, msg);
    }

    public static void v(String msg) {
        if (!GalleryConstants.IS_DEBUG) {
            return;
        }
        v(null, msg);
    }
}
