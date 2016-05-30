package com.liweijie.design.graduation.gallery.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.liweijie.design.graduation.gallery.util.L;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liweijie on 2016/5/27.
 */
public class CollectService {
    private GalleryDBHelper helper;

    public CollectService() {
        helper = GalleryDBHelper.getInstace();
    }

    /**
     * 插入图片文件夹相关
     *
     * @param image
     */
    public void saveCollectBean(String image) {
        if (image == null) {
            return;
        }
        SQLiteDatabase writableDatabase = helper.getWritableDatabase();
        writableDatabase.execSQL("insert into collect (image) values(?)",
                new Object[]{image});
    }

    public void saveCollectBeanList(List<String> images) {
        if (images == null || images.size() <= 0) {
            return;
        }
        for (String s : images) {
            // 避免重复
            if (getCollect(s) == null) {
                saveCollectBean(s);
            }

        }
    }

    public String getCollect(String fullpath) {
        SQLiteDatabase readableDatabase = helper.getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery("select image from collect where image=?", new String[]{fullpath});
        if (cursor.moveToFirst()) {
            return cursor.getString(0);
        }
        cursor.close();
        return null;
    }

    public List<String> getCollectBeanList() {
        L.i("collectGet", "getSecretBeanList");
        SQLiteDatabase readableDatabase = helper.getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery("select image from collect", null);
        List<String> beans = new ArrayList<>();
        while (cursor.moveToNext()) {
            String str = cursor.getString(0);
            beans.add(str);
            L.i("collectGet", str + "");
        }
        cursor.close();
        return beans;
    }

    public void deleteCollect(String fullPatn) {
        if (fullPatn == null) {
            return;
        }
        SQLiteDatabase readableDatabase = helper.getReadableDatabase();
        readableDatabase.execSQL("delete from collect where image=?", new String[]{fullPatn});
        readableDatabase.close();
    }

    public void deleteCollectionList(List<String> deleteList) {
        L.i("collectDetale", deleteList.size() + "");
        if (deleteList == null || deleteList.size() <= 0) {
            return;
        }
        for (String s : deleteList) {
            L.i("collectDetale", s + "");
            deleteCollect(s);
        }
    }

}
