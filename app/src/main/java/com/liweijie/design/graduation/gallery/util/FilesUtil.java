package com.liweijie.design.graduation.gallery.util;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by liweijie on 2016/5/24.
 */
public class FilesUtil {

    /**
     * 获取真正路径
     *
     * @param dir
     * @param path
     * @return
     */
    public static String getRealPath(String dir, String path) {
        return dir == null ? path : dir + File.separatorChar + path;
    }

    public static FilenameFilter getFilenameFilter() {
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

    public static boolean isExists(String absolutePath) {
        return isExists(new File(absolutePath));
    }

    public static void createFile(File file) {
        if (!isExists(file)) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void deleteFile(String realpath) {
        File file = new File(realpath);
        if (file.exists()) {
            file.delete();
        }
    }

    public static boolean isExists(File file) {
        if (file == null) {
            return false;
        }
        return file.exists();
    }

    public static void saveBitmap(Bitmap bitmap, String endWith, File parent) {
        File file;
        if (endWith.contains("jpg") || endWith.contains("jpeg")) {
            file = new File(parent, UUID.randomUUID() + ".jpg");

        } else {
            file = new File(parent, UUID.randomUUID() + ".png");
        }
        save(bitmap, file, endWith);
    }

    public static void save(Bitmap bitmap, File file, String str) {
        FileOutputStream fos = null;
        try {
            fos  =new FileOutputStream(file);
            if (str.endsWith("jpg") || str.endsWith("jpeg")) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            } else {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (bitmap != null && bitmap.isRecycled()) {
                bitmap.recycle();
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
