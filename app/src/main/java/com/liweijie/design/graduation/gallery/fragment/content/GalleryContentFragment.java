package com.liweijie.design.graduation.gallery.fragment.content;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.liweijie.design.graduation.gallery.R;
import com.liweijie.design.graduation.gallery.adapter.CollectFragmentAdapter;
import com.liweijie.design.graduation.gallery.app.App;
import com.liweijie.design.graduation.gallery.app.GalleryConstants;
import com.liweijie.design.graduation.gallery.base.BaseFragment;
import com.liweijie.design.graduation.gallery.bean.ImageBean;
import com.liweijie.design.graduation.gallery.coer.ThreadPoolManager;
import com.liweijie.design.graduation.gallery.event.OnRecyclerViewItemClickListener;
import com.liweijie.design.graduation.gallery.event.OnRecyclerViewItemLongClickListener;
import com.liweijie.design.graduation.gallery.fragment.BaseGridFragment;
import com.liweijie.design.graduation.gallery.util.FilesUtil;
import com.liweijie.design.graduation.gallery.util.PhotoList;
import com.liweijie.design.graduation.gallery.util.ResourceUtil;
import com.liweijie.design.graduation.gallery.util.ToastUtil;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by liweijie on 2016/5/19.
 */
public class GalleryContentFragment extends BaseGridFragment {
    private List<ImageBean> mDatas;
    private ProgressDialog mDialog;
    private ScanPhotoHelper mHelper;
    private CollectFragmentAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.gallery_main_fragment;
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
        mHelper = new ScanPhotoHelper();
        scanPhoto();

    }

    private void scanPhoto() {
        // 扫描图片
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            ToastUtil.showLong(R.string.bad_environment);
            return;
        }
        // 开始扫描
        mDatas = new ArrayList<>();
        mDialog = ProgressDialog.show(getActivity(), null, "正在扫描图片中...");
        mHelper.scaningSDcard(mDatas, mHandler);
    }


    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    // 自定义的方法
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (mDialog.isShowing())
                mDialog.dismiss();
            if (msg.what == GalleryConstants.SCAN_PHOTO_ERROR) {
                ToastUtil.showLong(R.string.system_no_pic);
            } else if (msg.what == GalleryConstants.SCAN_PHOTO_FINISH) {
                if (mDatas.size() == 0) {
                    ToastUtil.showLong(R.string.system_no_pic);
                    return;
                }
                data2RecyclerView();
                initRecyclerEvent();
            }
        }


    };

    private void initRecyclerEvent() {
        mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener<ImageBean>() {
            @Override
            public void onItemClick(View view, ImageBean bean, int position) {

            }
        });

        mAdapter.setOnItemLongClickListener(new OnRecyclerViewItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, Object o, int position) {

            }
        });
    }

    /**
     * 绑定数据到recyclerview
     */
    private void data2RecyclerView() {
        mAdapter = new CollectFragmentAdapter(mDatas);
        setRecycler();
        core_recycler_view.addItemDecoration(new HorizontalDividerItemDecoration.Builder(App.me())
                .color(Color.parseColor("#dddddd"))
                .margin((int)ResourceUtil.getDimen(R.dimen.margin_left),0)
                .size((int)ResourceUtil.getDimen(R.dimen.item_decoration_size)).build());
        core_recycler_view.setAdapter(mAdapter);

    }


}
