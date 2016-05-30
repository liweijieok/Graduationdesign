package com.liweijie.design.graduation.gallery.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.liweijie.design.graduation.gallery.bean.SecretInfo;
import com.liweijie.design.graduation.gallery.util.L;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liweijie on 2016/5/28.
 *
 */
public class SecretService {
    private GalleryDBHelper helper;

    public SecretService() {
        helper = GalleryDBHelper.getInstace();
    }

    public void saveSecretBean(SecretInfo info) {
        if (info == null) {
            return;
        }
        L.i("SecretSave",info.toString());
        SQLiteDatabase writableDatabase = helper.getWritableDatabase();
        writableDatabase.execSQL("insert into secret (image,format) values(?,?)",
                new Object[]{info.getFileName(),info.getFormat()});
    }

    public void saveSecretBeanList(List<SecretInfo> images) {
        if (images == null || images.size() <= 0) {
            return;
        }
        for (SecretInfo s : images) {
            // 避免重复
            if (getSecret(s.getFileName()) == null) {
                saveSecretBean(s);
            }

        }
    }

    public String getSecret(String fullpath) {
        SQLiteDatabase readableDatabase = helper.getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery("select image from secret where image=?", new String[]{fullpath});
        String str = null;
        if (cursor.moveToFirst()) {
            str= cursor.getString(0);
        }
        cursor.close();
        return str;
    }

    public List<SecretInfo> getSecretBeanList() {
        L.i("getSecretBeanList", "getSecretBeanList");
        SQLiteDatabase readableDatabase = helper.getWritableDatabase();
        Cursor cursor = readableDatabase.rawQuery("select image,format from secret", null);
        List<SecretInfo> beans = new ArrayList<>();
        while (cursor.moveToNext()) {
            SecretInfo info = new SecretInfo();
            info.setFileName(cursor.getString(0));
            info.setFormat(cursor.getString(1));
            beans.add(info);
            L.i("collectGet", info.toString() + "");
        }
        cursor.close();
        return beans;
    }

    public void deleteCollect(String fullPatn) {
        if (fullPatn == null) {
            return;
        }
        SQLiteDatabase readableDatabase = helper.getReadableDatabase();
        readableDatabase.execSQL("delete from secret where image=?", new String[]{fullPatn});
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
