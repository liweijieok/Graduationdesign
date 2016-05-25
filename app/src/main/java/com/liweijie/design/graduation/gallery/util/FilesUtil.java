package com.liweijie.design.graduation.gallery.util;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Created by liweijie on 2016/5/24.
 */
public class FilesUtil {

    /**
     * 获取真正路径
     * @param dir
     * @param path
     * @return
     */
    public static String getRealPath(String dir, String path) {
        return dir == null ? path : dir + File.separatorChar + path;
    }
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


    public static File[] getPicFiles(String dir) {
        File file = new File(dir);
        if (file == null || !file.exists() || file.isFile()) {
            return null;
        }
        return file.listFiles(getFilenameFilter());
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
