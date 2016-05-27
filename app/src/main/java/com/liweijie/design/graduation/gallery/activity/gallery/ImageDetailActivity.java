package com.liweijie.design.graduation.gallery.activity.gallery;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.liweijie.design.graduation.gallery.R;
import com.liweijie.design.graduation.gallery.adapter.ImageDetailAdapter;
import com.liweijie.design.graduation.gallery.app.GalleryConstants;
import com.liweijie.design.graduation.gallery.base.BaseActivity;
import com.liweijie.design.graduation.gallery.coer.ImageUtil;
import com.liweijie.design.graduation.gallery.event.OnRecyclerViewItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by liweijie on 2016/5/25.
 * 单张图片处理
 */
public class ImageDetailActivity extends BaseActivity {

    @Bind(R.id.image_detail_iv)
    ImageView image_detail_iv;
    @Bind(R.id.core_recycler_view)
    RecyclerView core_recycler_view;

    private ImageDetailAdapter mAdapter;
    private List<String> mDatas;

    private String mImagepath;

    @Override
    public int getLayoutId() {
        return R.layout.image_detail_activity;
    }

    @Override
    public void recoverAndBeforeInfalter(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void beforeInit() {

    }

    @Override
    public void initEvent() {
        mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener<String>() {
            @Override
            public void onItemClick(View view, String o, int position) {
                doImage(o);
            }
        });
    }

    private void doImage(String str) {
        Bitmap bitmap = ImageUtil.drawableToBitmap(image_detail_iv.getDrawable());
        image_detail_iv.setImageBitmap(ImageFunctioin.getBitmap(str, bitmap));
    }

    @Override
    public void initData() {
        mImagepath = getIntent().getStringExtra(GalleryConstants.ACTIVITY_FOLDER_IMAGE_PATH);
        mDatas = new ArrayList<>();
        mDatas.add("圆角");
        mDatas.add("倒影");
        mDatas.add("灰度");
        mDatas.add("浮雕");
        mDatas.add("模糊");
        mDatas.add("黑白");
        mDatas.add("油画");
        mDatas.add("底片");
        mDatas.add("光照");
        mDatas.add("泛黄");
        mDatas.add("放大镜");
        mDatas.add("哈哈镜");
        mAdapter = new ImageDetailAdapter(mDatas);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.HORIZONTAL);
        core_recycler_view.setLayoutManager(manager);
        core_recycler_view.setAdapter(mAdapter);
    }
}
