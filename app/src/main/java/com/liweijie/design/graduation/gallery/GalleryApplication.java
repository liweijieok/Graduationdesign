package com.liweijie.design.graduation.gallery;

import android.app.Application;

import com.liweijie.design.graduation.gallery.app.App;
import com.liweijie.design.graduation.gallery.app.GalleryConstants;
import com.liweijie.design.graduation.gallery.coer.GalleryGlobalException;
import com.liweijie.design.graduation.gallery.util.L;

import java.io.File;
import java.io.IOException;

/**
 * Created by liweijie on 2016/5/8.
 * GalleryApplication
 */
public class GalleryApplication extends Application {

    public static File baseFile;


    @Override
    public void onCreate() {
        super.onCreate();
        initBase();


    }

    private void initBase() {
        App.init(this);
//        GalleryGlobalException.getInstance().init();
        baseFile= new File(getFilesDir(), GalleryConstants.BASE_FILE);
        if (!baseFile.exists()) {
            try {
                baseFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        L.i("File",baseFile.exists()+"");
    }
}
