package com.liweijie.design.graduation.gallery.activity.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.liweijie.design.graduation.gallery.R;
import com.liweijie.design.graduation.gallery.adapter.FolderImageAdapter;
import com.liweijie.design.graduation.gallery.app.GalleryConstants;
import com.liweijie.design.graduation.gallery.base.BaseActivity;
import com.liweijie.design.graduation.gallery.event.OnRecyclerViewItemClickListener;
import com.liweijie.design.graduation.gallery.util.PhotoList;
import com.liweijie.design.graduation.gallery.view.DividerGridItemDecoration;

import java.util.List;

import butterknife.Bind;

public class FolderImageActivity extends BaseActivity {

    private List<String> mFolderImagePath;
    private String mDir;
    private FolderImageAdapter mAdapter;

    @Bind(R.id.core_recycler_view)
    RecyclerView core_recycler_view;
    @Bind(R.id.folder_tv_title)
    TextView folder_tv_title;

    @Override
    public int getLayoutId() {
        return R.layout.activity_folder_image;
    }

    @Override
    public void recoverAndBeforeInfalter(Bundle savedInstanceState) {

    }

    @Override
    public void beforeInit() {

    }

    @Override
    public void initEvent() {
        mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object o, int position) {
                Intent newIntent = new Intent(FolderImageActivity.this, ImageDetailActivity.class);
                startActivity(newIntent);
            }
        });
    }

    @Override
    public void initData() {
        // getIntent
        folder_tv_title.setText(getIntent().getStringExtra(GalleryConstants.ACTIVITY_GALLERY_FODER_TITLE));
        mDir = getIntent().getStringExtra(GalleryConstants.ACTIVITY_GALLERY_FODER_DIR);
        if (mDir == null) {
            mFolderImagePath = PhotoList.allPhoto;
        }else {
            mFolderImagePath = PhotoList.mInfo;
        }

        mAdapter = new FolderImageAdapter(mFolderImagePath,mDir);
        core_recycler_view.setLayoutManager(new StaggeredGridLayoutManager(4,
                StaggeredGridLayoutManager.VERTICAL));
        core_recycler_view.addItemDecoration(new DividerGridItemDecoration(this));
        core_recycler_view.setAdapter(mAdapter);
        core_recycler_view.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
//        ou
    }
}
