package com.liweijie.design.graduation.gallery.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.liweijie.design.graduation.gallery.app.App;

/**
 * Created by liweijie on 2016/5/18.
 * 单例，用于建表升级
 */
public class GalleryDBHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "gallery.db";
    private static int VERSION = 1;

    // 首页表
    private String FOLDER_DB = "folder";
    private String CREATE_FOLDER_DB = "id integer primary key autoincrement ,count integer ,firstImage varchar(60),name varchar(20), dir varchar(40)";


    private static GalleryDBHelper helper;

    public static GalleryDBHelper getInstace(){
        if (helper == null) {
            synchronized (GalleryDBHelper.class) {
                if (helper == null) {
                    helper = new GalleryDBHelper();
                }
            }
        }
        return helper;
    }

    private GalleryDBHelper() {
        super(App.me(), DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(getDBHeader(FOLDER_DB, CREATE_FOLDER_DB));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(getDropTable(FOLDER_DB));
        onCreate(db);
    }

    private String getDBHeader(String dbName, String row) {
        return "create table if not exists " + dbName + "( " + row + " )";
    }

    private String getDropTable(String dbName){
        return "drop table if exists "+dbName;
    }
}
