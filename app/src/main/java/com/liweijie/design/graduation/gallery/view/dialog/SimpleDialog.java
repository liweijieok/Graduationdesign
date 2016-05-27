package com.liweijie.design.graduation.gallery.view.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by liweijie on 2016/5/27.
 */
public class SimpleDialog {
    public static void getDialog(Activity a, String s1, String s2, DialogInterface.OnClickListener li, String s3, DialogInterface.OnClickListener li2) {
        new AlertDialog.Builder(a)
                .setMessage(s1)
                .setNegativeButton(s2, li)
                .setPositiveButton(s3, li2)
                .create().show();
    }
}
