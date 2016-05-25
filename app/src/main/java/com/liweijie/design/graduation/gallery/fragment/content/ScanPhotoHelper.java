package com.liweijie.design.graduation.gallery.fragment.content;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;

import com.liweijie.design.graduation.gallery.app.App;
import com.liweijie.design.graduation.gallery.app.GalleryConstants;
import com.liweijie.design.graduation.gallery.bean.ImageBean;
import com.liweijie.design.graduation.gallery.coer.ThreadPoolManager;
import com.liweijie.design.graduation.gallery.db.GalleryDBService;
import com.liweijie.design.graduation.gallery.util.FilesUtil;
import com.liweijie.design.graduation.gallery.util.L;
import com.liweijie.design.graduation.gallery.util.PhotoList;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by liweijie on 2016/5/24.
 * 扫描图片帮助类，主要是两种，一个是系统的，一种是自己数据库的
 */
public class ScanPhotoHelper {

    private GalleryDBService service;

    public ScanPhotoHelper() {
        service = new GalleryDBService();
    }

    /**
     * 扫描SD卡图片，同时更新本地数据库
     *
     * @param mDatas
     * @param mHandler
     */
    public void scaningSDcard(final List<ImageBean> mDatas, final Handler mHandler) {
        mDatas.clear();
        ThreadPoolManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver rc = App.me().getContentResolver();
                Cursor cursor = rc.query(uri, null, MediaStore.Images.Media.MIME_TYPE + "=? or "
                        + MediaStore.Images.Media.MIME_TYPE + "=?", new String[]
                        {"image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_MODIFIED);
                Set<String> mSets = new HashSet<>();
                boolean is_first = true;
                if (cursor == null) {
                    mHandler.sendEmptyMessage(GalleryConstants.SCAN_PHOTO_ERROR);
                    return;
                }
                while (cursor.moveToNext()) {
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    if (path == null) {
                        continue;
                    }
                    if (path.length() <= 0) {
                        continue;
                    }
                    File parentFile = new File(path).getParentFile();
                    if (parentFile == null || !parentFile.exists() || parentFile.list().length <= 0) {
                        continue;
                    }
                    String dirPath = parentFile.getAbsolutePath();
                    if (is_first) {
                        is_first = false;
                        ImageBean all = new ImageBean();
                        all.setCount(0);
                        all.setDir("/所有图片");
                        all.setFirstImage(path);
                        mDatas.add(all);
                    }

                    ImageBean bean;
                    if (mSets.contains(dirPath)) {
                        continue;
                    } else {
                        mSets.add(dirPath);
                        bean = new ImageBean();
                        bean.setDir(dirPath);
                        bean.setFirstImage(path);
                    }
                    int picSize = parentFile.list(FilesUtil.getFilenameFilter()).length;
                    bean.setCount(picSize);
                    mDatas.add(bean);
                }

                // 计算所有图片
                File file;
                for (int i = 1; i < mDatas.size(); i++) {
                    file = new File(mDatas.get(i).getDir());
                    mDatas.get(0).setCount(mDatas.get(0).getCount() + mDatas.get(i).getCount());
                    File[] listFile = file.listFiles(FilesUtil.getFilenameFilter());
                    for (int j = 0; j < listFile.length; j++) {
                        PhotoList.allPhoto.add(listFile[j].getAbsolutePath());
                    }
                }
                cursor.close();
                // 插入数据库
                service.deleteTabeData();
                service.saveImageBeanList(mDatas.subList(1, mDatas.size()));
                mHandler.sendEmptyMessage(GalleryConstants.SCAN_PHOTO_FINISH);
            }
        });
    }

    public void scaningDB(List<ImageBean> mDBDatas) {
        mDBDatas.clear();
        ImageBean bean = new ImageBean();
        bean.setCount(0);
        bean.setDir("/所有图片");
        mDBDatas.add(bean);
        List<ImageBean> mDBImage = service.getImageBeanList();
        if (mDBDatas.size() == 0 || mDBDatas == null) {
            return;
        }
        for (ImageBean b : service.getImageBeanList()) {
            L.i("DBDATA", b.toString());
        }
        // 检测合法性
        for (int i = 0; i < mDBImage.size(); i++) {
            File file = new File(mDBImage.get(i).getDir());
            File first = new File(mDBImage.get(i).getFirstImage());
            // 文件夹和首页文件存在，则添加显示
            if (file.exists() && first.exists()) {
                mDBDatas.add(mDBImage.get(i));
            }
        }

        if (mDBDatas.size() >= 2) {
            bean.setFirstImage(mDBDatas.get(1).getFirstImage());
        }
        // 计算所有图片
        File file;
        for (int i = 1; i < mDBDatas.size(); i++) {
            file = new File(mDBDatas.get(i).getDir());
            mDBDatas.get(0).setCount(mDBDatas.get(0).getCount() + mDBDatas.get(i).getCount());
            File[] listFile = file.listFiles(FilesUtil.getFilenameFilter());
            for (int j = 0; j < listFile.length; j++) {
                PhotoList.allPhoto.add(listFile[j].getAbsolutePath());
            }
        }


    }

    public void updateDB(List<String> newData) {

    }
}
