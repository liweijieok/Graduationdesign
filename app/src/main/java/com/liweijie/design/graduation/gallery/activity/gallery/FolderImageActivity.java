package com.liweijie.design.graduation.gallery.activity.gallery;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liweijie.design.graduation.gallery.R;
import com.liweijie.design.graduation.gallery.adapter.FolderImageAdapter;
import com.liweijie.design.graduation.gallery.app.GalleryConstants;
import com.liweijie.design.graduation.gallery.base.BaseActivity;
import com.liweijie.design.graduation.gallery.bean.FolderImageBean;
import com.liweijie.design.graduation.gallery.bean.SecretInfo;
import com.liweijie.design.graduation.gallery.coer.MyImageLoader;
import com.liweijie.design.graduation.gallery.coer.ThreadPoolManager;
import com.liweijie.design.graduation.gallery.db.CollectService;
import com.liweijie.design.graduation.gallery.db.SecretService;
import com.liweijie.design.graduation.gallery.event.OnRecyclerViewItemClickListener;
import com.liweijie.design.graduation.gallery.event.OnRecyclerViewItemLongClickListener;
import com.liweijie.design.graduation.gallery.util.FilesUtil;
import com.liweijie.design.graduation.gallery.util.L;
import com.liweijie.design.graduation.gallery.util.PhotoList;
import com.liweijie.design.graduation.gallery.util.ToastUtil;
import com.liweijie.design.graduation.gallery.view.DividerGridItemDecoration;
import com.liweijie.design.graduation.gallery.view.dialog.RecyclerDialogFragment;
import com.liweijie.design.graduation.gallery.view.dialog.SimpleDialog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class FolderImageActivity extends BaseActivity implements RecyclerDialogFragment.OnMenuDialogListener {

    private List<FolderImageBean> mFolderImagePath;
    private String mDir;
    private FolderImageAdapter mAdapter;
    private RecyclerDialogFragment menuDialog;

    @Bind(R.id.core_recycler_view)
    RecyclerView core_recycler_view;
    @Bind(R.id.folder_tv_title)
    TextView folder_tv_title;
    @Bind(R.id.folder_tv_selected)
    TextView folder_tv_selected;
    @Bind(R.id.folder_menu_root)
    LinearLayout folder_menu_root;

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
        mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener<FolderImageBean>() {
            @Override
            public void onItemClick(View view, FolderImageBean o, int position) {
                gotoImageDetail(o.getPath());
            }
        });

        mAdapter.setOnItemLongClickListener(new OnRecyclerViewItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, Object o, int position) {
                mAdapter.showCheckBox(true);
                folder_menu_root.setVisibility(View.VISIBLE);
            }
        });
    }

    private void gotoImageDetail(String o) {
        Intent newIntent = new Intent(FolderImageActivity.this, ImageDetailActivity.class);
        newIntent.putExtra(GalleryConstants.ACTIVITY_FOLDER_IMAGE_PATH, FilesUtil.getRealPath(mDir, o));
        MyImageLoader.getInstance(2, MyImageLoader.Type.LIFO).clearCache();
        startActivity(newIntent);
    }

    @Override
    public void initData() {
        // getIntent
        folder_tv_title.setText(getIntent().getStringExtra(GalleryConstants.ACTIVITY_GALLERY_FODER_TITLE));
        mDir = getIntent().getStringExtra(GalleryConstants.ACTIVITY_GALLERY_FODER_DIR);
        mFolderImagePath = new ArrayList<>();
        if (mDir == null) {
            for (String s : PhotoList.allPhoto) {
                FolderImageBean bean = new FolderImageBean(s, false);
                mFolderImagePath.add(bean);
            }
        } else {
//            mFolderImagePath = new ArrayList<>(PhotoList.mInfo);
            for (String s : PhotoList.mInfo) {
                FolderImageBean bean = new FolderImageBean(s, false);
                mFolderImagePath.add(bean);
            }
        }

        mAdapter = new FolderImageAdapter(mFolderImagePath, mDir);
        core_recycler_view.setLayoutManager(new StaggeredGridLayoutManager(4,
                StaggeredGridLayoutManager.VERTICAL));
        core_recycler_view.addItemDecoration(new DividerGridItemDecoration(this));
        core_recycler_view.setAdapter(mAdapter);
        core_recycler_view.setItemAnimator(new DefaultItemAnimator());

        collectService = new CollectService();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mAdapter.isSHowCheckBox()) {
            mAdapter.showCheckBox(false);
            folder_menu_root.setVisibility(View.GONE);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
//        ou
    }

    // 相关点击事件
    @OnClick(R.id.folder_tv_lly1)
    public void share() {

    }

    @OnClick(R.id.folder_tv_lly2)
    public void move() {

    }

    CollectService collectService;

    @OnClick(R.id.folder_tv_lly6)
    public void collect() {
        if (mAdapter.getChonsen().size() > 0) {
            if (mDir != null) {
                List<String> strs = new ArrayList<>();
                for (String s : mAdapter.getChonsen()) {
                    strs.add(FilesUtil.getRealPath(mDir, s));
                }
                collectService.saveCollectBeanList(strs);
            } else {
                collectService.saveCollectBeanList(mAdapter.getChonsen());
            }

            ToastUtil.showShort("收藏成功");
        }

    }


    @OnClick(R.id.folder_tv_lly3)
    public void delete() {
        if (mAdapter.getChonsen().size() <= 0) {
            return;
        }
        SimpleDialog.getDialog(this, "是否删除", "不删除", null, "删除",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteChosen();
                        ToastUtil.showLong("删除成功");
                    }
                });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mAdapter.isSHowCheckBox()) {

        }
    }

    /**
     * 删除选择的图片
     */
    private void deleteChosen() {
        List<String> deleteList = mAdapter.getChonsen();
        if (deleteList.size() == 0) {
            return;
        }
        //第一步，清除数据
        for (int i = 0; i < deleteList.size(); i++) {
            for (int j = 0; j < mAdapter.getDatas().size(); j++) {
                if (deleteList.get(i).equals(mAdapter.getDatas().get(j))) {
                    Log.i("Selected", "删除的图片" + mAdapter.getDatas().get(j));
                    mAdapter.getDatas().remove(mAdapter.getDatas().get(j));
                    //第二步删除本地文件
                    FilesUtil.deleteFile(FilesUtil.getRealPath(mDir, deleteList.get(i)));
                }
            }
        }
        mAdapter.showCheckBox(false);
    }

    @OnClick(R.id.folder_tv_lly4)
    public void chosenAll() {
        if (folder_tv_selected.getText().toString().equals("全选")) {
            mAdapter.choosenAll(true);
            folder_tv_selected.setText("取消全选");
        } else {
            folder_tv_selected.setText("全选");
            mAdapter.choosenAll(false);
        }

    }

    @OnClick(R.id.folder_tv_lly5)
    public void menu() {
        // 显示菜单
        ArrayList<String> mDatas = new ArrayList<>();
        mDatas.add("复制");
        mDatas.add("编辑");
        mDatas.add("打印");
        mDatas.add("重命名");
        mDatas.add("设置为");
        mDatas.add("旋转");
        mDatas.add("保密");
        mDatas.add("详细信息");
        menuDialog = (RecyclerDialogFragment) getSupportFragmentManager().findFragmentByTag("menuDialog");
        if (menuDialog == null) {
            menuDialog = RecyclerDialogFragment.getInstance(mDatas);
            menuDialog.setOnMenuDialogListener(this);
        }
        menuDialog.show(getSupportFragmentManager(), "menuDialog");
    }

    @Override
    public void menuDialogClick(final String str, int position) {
        ToastUtil.showShort(str);
        if (menuDialog != null) {
            menuDialog.dismiss();
        }
        if (position == 0) {

        } else if (position == 1) {
            if (mAdapter.getChonsen().size() > 0) {
                if (mAdapter.getChonsen().size() >= 2) {
                    ToastUtil.showShort("图片多于2张，只对第一张就行编辑");
                }
                gotoImageDetail(mAdapter.getChonsen().get(0));
            }
        } else if (str.equals(6)) {

            if (mAdapter.getChonsen().size() > 0) {
                final List<String> strs = new ArrayList<>();
                // 得到全路径
                for (String s : mAdapter.getChonsen()) {
                    strs.add(FilesUtil.getRealPath(mDir, s));
                }

                progressDialog = ProgressDialog.show(this, "", "正在保存到私密文件中...");
                final File parent = FilesUtil.createFileDir(GalleryConstants.SECRET_FILE_NAME);
                ThreadPoolManager.getInstance().execute(new Runnable() {
                    @Override
                    public void run() {
                        final List<SecretInfo> mSecret = new ArrayList<>();

                        for (int i = 0; i < strs.size(); i++) {
                            String s = strs.get(i);
                            File file = new File(strs.get(i));
                            if (FilesUtil.isExists(file)) {
                                File newFile = new File(parent, s.substring(s.lastIndexOf(File.separator) + 1));
                                L.i("SecretFile", newFile.getName());
                                if (!newFile.exists()) {
                                    try {
                                        if (!newFile.createNewFile()) {
                                            return;
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                                FilesUtil.copyImage(s, newFile);
                                SecretInfo info = new SecretInfo();
                                info.setFileName(newFile.getName().substring(0, newFile.getName().indexOf(".") + 1));
                                info.setFormat(newFile.getName().substring(newFile.getName().indexOf(".") + 1));
                                mSecret.add(info);
                            }

                        }
                        Message msg = Message.obtain();
                        msg.what = 1;
                        msg.obj = mSecret;
                        mHandler.sendMessage(msg);
                    }
                });

            }
        }
    }

    SecretService secretService = new SecretService();
    private ProgressDialog progressDialog;

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            List<SecretInfo> mSecret = (List<SecretInfo>) msg.obj;
            secretService.saveSecretBeanList(mSecret);

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyImageLoader.getInstance(2, MyImageLoader.Type.LIFO).clearCache();
    }
}
