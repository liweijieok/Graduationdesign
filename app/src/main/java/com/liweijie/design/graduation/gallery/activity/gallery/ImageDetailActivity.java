package com.liweijie.design.graduation.gallery.activity.gallery;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.liweijie.design.graduation.gallery.app.GalleryConstants;
import com.liweijie.design.graduation.gallery.base.BaseActivity;

/**
 * Created by liweijie on 2016/5/25.
 * 单张图片处理
 */
public class ImageDetailActivity extends BaseActivity {

    private String mImagepath;

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void recoverAndBeforeInfalter(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void beforeInit() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {
        mImagepath = getIntent().getStringExtra(GalleryConstants.ACTIVITY_FOLDER_IMAGE_PATH);
    }
}
