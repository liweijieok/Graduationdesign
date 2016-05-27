package com.liweijie.design.graduation.gallery.activity.gallery;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.liweijie.design.graduation.gallery.R;
import com.liweijie.design.graduation.gallery.adapter.ImageDetailAdapter;
import com.liweijie.design.graduation.gallery.app.GalleryConstants;
import com.liweijie.design.graduation.gallery.base.BaseActivity;
import com.liweijie.design.graduation.gallery.coer.ImageUtil;
import com.liweijie.design.graduation.gallery.coer.MyImageLoader;
import com.liweijie.design.graduation.gallery.event.OnRecyclerViewItemClickListener;
import com.liweijie.design.graduation.gallery.util.FilesUtil;
import com.liweijie.design.graduation.gallery.util.L;
import com.liweijie.design.graduation.gallery.util.ToastUtil;
import com.liweijie.design.graduation.gallery.view.dialog.SimpleDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    private String mImagePath;
    private Drawable originDrawable;

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        originDrawable = null;
    }

    private void doImage(String str) {
        if (originDrawable == null) {
            originDrawable = getResources().getDrawable(R.drawable.user_icon);
        }
        Bitmap bitmap = ImageUtil.drawableToBitmap(originDrawable);
        image_detail_iv.setImageBitmap(ImageFunctioin.getBitmap(str, bitmap));
    }

    @Override
    public void initData() {
        mImagePath = getIntent().getStringExtra(GalleryConstants.ACTIVITY_FOLDER_IMAGE_PATH);
        MyImageLoader.getInstance(2, MyImageLoader.Type.LIFO).loadImage(mImagePath, image_detail_iv, new MyImageLoader.LoadImageListener() {
            @Override
            public void loadSuccess(ImageView iv, String path) {
                L.i("mImagePath", mImagePath + "=mImagePath");
                originDrawable = image_detail_iv.getDrawable();
            }

            @Override
            public void loadFail(ImageView iv, String path) {
                L.i("mImagePath", path);
                originDrawable = getResources().getDrawable(R.drawable.user_icon);
            }
        });
        L.i("mImagePath", mImagePath + "=mImagePath");
        mDatas = new ArrayList<>();
        mDatas.add("原图");
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

        mAdapter = new ImageDetailAdapter(mDatas);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.HORIZONTAL);
        core_recycler_view.setLayoutManager(manager);
        core_recycler_view.setAdapter(mAdapter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            SimpleDialog.getDialog(this, "是否保存新照片", "不保存",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }, "保存",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            File file = new File(mImagePath);
                            Bitmap bitmap = ((BitmapDrawable) image_detail_iv.getDrawable()).getBitmap();
                            FilesUtil.saveBitmap(bitmap, mImagePath, file.getParentFile());
                            ToastUtil.showLong("保存成功");
                            finish();
                        }
                    });
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
