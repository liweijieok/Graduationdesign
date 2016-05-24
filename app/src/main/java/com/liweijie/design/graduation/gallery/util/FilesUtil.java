package com.liweijie.design.graduation.gallery.util;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Created by liweijie on 2016/5/24.
 */
public class FilesUtil {
    public static FilenameFilter getFilenameFilter(){
       return new FilenameFilter() {

            @Override
            public boolean accept(File dir, String filename) {
                if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")
                        || filename.endsWith(".png"))
                    return true;
                return false;
            }
        };
    }

    public static boolean isExists(String absolutePath){
        return isExists(new File(absolutePath));
    }

    public static boolean isExists(File file){
        if (file == null) {
            return false;
        }
        return file.exists();
    }
}
