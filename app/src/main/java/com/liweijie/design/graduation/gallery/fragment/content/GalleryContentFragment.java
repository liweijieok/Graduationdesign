package com.liweijie.design.graduation.gallery.fragment.content;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.liweijie.design.graduation.gallery.R;
import com.liweijie.design.graduation.gallery.activity.gallery.FolderImageActivity;
import com.liweijie.design.graduation.gallery.adapter.CollectFragmentAdapter;
import com.liweijie.design.graduation.gallery.app.App;
import com.liweijie.design.graduation.gallery.app.GalleryConstants;
import com.liweijie.design.graduation.gallery.bean.ImageBean;
import com.liweijie.design.graduation.gallery.coer.MyImageLoader;
import com.liweijie.design.graduation.gallery.event.OnRecyclerViewItemClickListener;
import com.liweijie.design.graduation.gallery.event.OnRecyclerViewItemLongClickListener;
import com.liweijie.design.graduation.gallery.fragment.BaseGridFragment;
import com.liweijie.design.graduation.gallery.util.FilesUtil;
import com.liweijie.design.graduation.gallery.util.L;
import com.liweijie.design.graduation.gallery.util.PhotoList;
import com.liweijie.design.graduation.gallery.util.ResourceUtil;
import com.liweijie.design.graduation.gallery.util.SPUtil;
import com.liweijie.design.graduation.gallery.util.ToastUtil;
import com.liweijie.design.graduation.gallery.view.dialog.BuildGalleryDialogFragment;
import com.liweijie.design.graduation.gallery.view.dialog.RecyclerDialogFragment;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;

/**
 * Created by liweijie on 2016/5/19.
 */
public class GalleryContentFragment extends BaseGridFragment implements View.OnClickListener, RecyclerDialogFragment.OnMenuDialogListener, BuildGalleryDialogFragment.OnBuildDialogListener {
    private List<ImageBean> mDatas;
    private ProgressDialog mDialog;
    private ScanPhotoHelper mHelper;
    private CollectFragmentAdapter mAdapter;
    @Bind(R.id.gallery_build_lly)
    LinearLayout gallery_build_lly;
    @Bind(R.id.gallery_menu_lly)
    LinearLayout gallery_menu_lly;
    @Bind(R.id.core_recycler_view)
    RecyclerView core_recycler_view;

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
        gallery_build_lly.setOnClickListener(this);
        gallery_menu_lly.setOnClickListener(this);

    }

    @Override
    public void initData() {
        mHelper = new ScanPhotoHelper();
        core_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        core_recycler_view.addItemDecoration(new HorizontalDividerItemDecoration.Builder(App.me())
                .color(Color.parseColor("#dddddd"))
                .margin((int) ResourceUtil.getDimen(R.dimen.margin_left), (int) ResourceUtil.getDimen(R.dimen.margin_left))
                .size((int) ResourceUtil.getDimen(R.dimen.item_decoration_size)).build());
        mDatas = new ArrayList<>();
        mAdapter = new CollectFragmentAdapter(mDatas);
        core_recycler_view.setAdapter(mAdapter);
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
        // 第一次需要显示对话框
        if (SPUtil.get(GalleryConstants.IS_FIRST, true)) {
            mDialog = ProgressDialog.show(getActivity(), null, "正在扫描图片中...");
            mHelper.scaningSDcard(mDatas, mHandler);
        } else {
            //第二次扫描本地数据库
            mHelper.scaningDB(mDatas);
            data2RecyclerView();
            initRecyclerEvent();
            mHelper.scaningSDcard(mDatas, mHandler);
        }


    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    // 自定义的方法
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (mDialog != null && mDialog.isShowing())
                mDialog.dismiss();
            if (msg.what == GalleryConstants.SCAN_PHOTO_ERROR) {
                ToastUtil.showLong(R.string.system_no_pic);
            } else if (msg.what == GalleryConstants.SCAN_PHOTO_FINISH) {
                data2RecyclerView();
                initRecyclerEvent();
            }
        }


    };

    private void initRecyclerEvent() {
        mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener<ImageBean>() {
            @Override
            public void onItemClick(View view, ImageBean bean, int position) {
                Intent newIntent = new Intent(getActivity(), FolderImageActivity.class);
                String title = bean.getName().substring(1, bean.getName().length());
                ;
                if (position != 0) {
                    newIntent.putExtra(GalleryConstants.ACTIVITY_GALLERY_FODER_DIR, bean.getDir());
                    // 获取文件夹下面的图片
                    PhotoList.mInfo = Arrays.asList(new File(bean.getDir())
                            .list(FilesUtil.getFilenameFilter()));
                }

                newIntent.putExtra(GalleryConstants.ACTIVITY_GALLERY_FODER_TITLE, title);
                MyImageLoader.getInstance(2, MyImageLoader.Type.LIFO).clearCache();
                startActivity(newIntent);
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
        if (mDatas.size() == 0) {
            ToastUtil.showLong(R.string.system_no_pic);
            return;
        }
        mAdapter.getDatas().addAll(mDatas);
        mAdapter.notifyDataSetChanged();


    }

    private RecyclerDialogFragment menuDialog;
    private BuildGalleryDialogFragment buildDialog;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gallery_menu_lly:
                // 显示菜单
                ArrayList<String> mDatas = new ArrayList<>();
                mDatas.add("设置");
                mDatas.add("隐藏菜单");
                menuDialog = (RecyclerDialogFragment) getFragmentManager().findFragmentByTag("menuDialog");
                if (menuDialog == null) {
                    menuDialog = RecyclerDialogFragment.getInstance(mDatas);
                    menuDialog.setOnMenuDialogListener(this);

                }
                menuDialog.show(getFragmentManager(), "menuDialog");
                break;
            case R.id.gallery_build_lly:
                buildDialog = (BuildGalleryDialogFragment) getFragmentManager().findFragmentByTag("buildDialog");
                if (buildDialog == null) {
                    buildDialog = BuildGalleryDialogFragment.getInstance();
                    buildDialog.setOnBuildDialogListener(this);
                }
                buildDialog.show(getFragmentManager(), "buildDialog");
                break;

        }
    }

    @Override
    public void menuDialogClick(String str, int position) {
        ToastUtil.showLong(position + "");
        if (menuDialog != null) {
            menuDialog.dismiss();
        }
        if (position == 0) {

        } else if (position == 1) {

        }
    }


    @Override
    public void buildDialog(String file, File dir) {
        L.i("BuildPath", file + " == " + dir.getName());
        File newGallery = new File(dir, file);
        FilesUtil.createFile(newGallery);
        // 保存新建类型数据库

    }
}
