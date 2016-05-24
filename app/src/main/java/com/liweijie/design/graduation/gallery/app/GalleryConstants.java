package com.liweijie.design.graduation.gallery.app;

import com.liweijie.design.graduation.gallery.util.AtomicIntegerUtil;

/**
 * Created by liweijie on 2016/5/18.
 * 全局常量
 */
public interface GalleryConstants {
    boolean IS_DEBUG = true;

    //文件夹相关
    String BASE_FILE = "gallery";
    String CRESH_FILE_NAME = "grallery_file";

    // handler
    int SCAN_PHOTO_FINISH = AtomicIntegerUtil.getNewInt();
    int SCAN_PHOTO_ERROR = AtomicIntegerUtil.getNewInt();


}
