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
    String CRESH_FILE_NAME = "gralleryfile";
    String SECRET_FILE_NAME = "secretphoto";

    // handler
    int SCAN_PHOTO_FINISH = AtomicIntegerUtil.getNewInt();
    int SCAN_PHOTO_ERROR = AtomicIntegerUtil.getNewInt();

    // Activity
    String ACTIVITY_GALLERY_FODER_DATA = "activity_gallery_folder_data";
    String ACTIVITY_GALLERY_FODER_TITLE = "activity_gallery_folder_title";
    String ACTIVITY_GALLERY_FODER_DIR = "activity_gallery_folder_dir";

    String ACTIVITY_FOLDER_IMAGE_PATH = "activity_folder_image_path";

    //    Preference
    String IS_FIRST = "is_first";
    String SECRET_PASSWORD = "secret_password";

}
