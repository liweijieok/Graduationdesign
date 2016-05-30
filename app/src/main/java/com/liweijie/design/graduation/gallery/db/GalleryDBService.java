package com.liweijie.design.graduation.gallery.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.liweijie.design.graduation.gallery.bean.ImageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liweijie on 2016/5/18.
 */
public class GalleryDBService {
    private GalleryDBHelper helper;

    public GalleryDBService() {
        helper = GalleryDBHelper.getInstace();
    }

    /**
     * 插入图片文件夹相关
     * @param bean
     */
    public void saveImageBean(ImageBean bean) {
        if (bean == null) {
            return;
        }
        SQLiteDatabase writableDatabase = helper.getWritableDatabase();
        writableDatabase.execSQL("insert into folder (count,firstImage,name, dir) values(?,?,?,?)",
                new Object[]{bean.getCount(), bean.getFirstImage(), bean.getName(), bean.getDir()});
    }

    public void saveImageBeanList(List<ImageBean> beans) {
        if (beans == null || beans.size() == 0) {
            return;
        }
        for (ImageBean bean : beans) {
            saveImageBean(bean);
        }
    }


    public List<ImageBean> getImageBeanList() {
        SQLiteDatabase readableDatabase = helper.getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery("select count,firstImage,name,dir from folder", null);
        List<ImageBean> beans = new ArrayList<>();
        while (cursor.moveToNext()) {
            ImageBean bean = new ImageBean();
            bean.setCount(cursor.getInt(0));
            bean.setFirstImage(cursor.getString(1));
            bean.setDir(cursor.getString(3));
            beans.add(bean);
        }
        cursor.close();
        return beans;
    }

       public void deleteTabeData() {
        SQLiteDatabase readableDatabase = helper.getReadableDatabase();
        readableDatabase.execSQL("delete from folder");
    }
}
