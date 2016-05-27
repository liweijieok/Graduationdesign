package com.liweijie.design.graduation.gallery.fragment.collect;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liweijie.design.graduation.gallery.R;
import com.liweijie.design.graduation.gallery.activity.gallery.ImageDetailActivity;
import com.liweijie.design.graduation.gallery.adapter.FolderImageAdapter;
import com.liweijie.design.graduation.gallery.app.GalleryConstants;
import com.liweijie.design.graduation.gallery.base.BaseFragment;
import com.liweijie.design.graduation.gallery.bean.FolderImageBean;
import com.liweijie.design.graduation.gallery.db.CollectService;
import com.liweijie.design.graduation.gallery.event.OnRecyclerViewItemClickListener;
import com.liweijie.design.graduation.gallery.event.OnRecyclerViewItemLongClickListener;
import com.liweijie.design.graduation.gallery.util.FilesUtil;
import com.liweijie.design.graduation.gallery.util.ToastUtil;
import com.liweijie.design.graduation.gallery.view.DividerGridItemDecoration;
import com.liweijie.design.graduation.gallery.view.dialog.RecyclerDialogFragment;
import com.liweijie.design.graduation.gallery.view.dialog.SimpleDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by liweijie on 2016/5/19.
 */
public class GalleryCollectFragment extends BaseFragment implements RecyclerDialogFragment.OnMenuDialogListener {

    private List<FolderImageBean> mFolderImagePath;
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
    @Bind(R.id.folder_tv_collect)
    TextView folder_tv_collect;
    @Bind(R.id.folder_iv_collect)
    ImageView folder_iv_collect;
    private CollectHelper helper;

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
        Intent newIntent = new Intent(getActivity(), ImageDetailActivity.class);
        newIntent.putExtra(GalleryConstants.ACTIVITY_FOLDER_IMAGE_PATH, FilesUtil.getRealPath(null, o));
        startActivity(newIntent);
    }



    @Override
    public void onResume() {
        super.onResume();
        refreshCollect();
    }

    private void refreshCollect() {
        mAdapter.getDatas().clear();
        for (String s : helper.readFromDB()) {
            FolderImageBean bean = new FolderImageBean(s, false);
            mAdapter.getDatas().add(bean);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void initData() {
        folder_iv_collect.setImageResource(R.drawable.cancel_collect_image);
        folder_tv_collect.setText("取消收藏");
        folder_tv_title.setVisibility(View.GONE);
        helper = new CollectHelper();
        mFolderImagePath = new ArrayList<>();

//        for (String s : helper.readFromDB()) {
//            FolderImageBean bean = new FolderImageBean(s, false);
//            mFolderImagePath.add(bean);
//        }

        mAdapter = new FolderImageAdapter(mFolderImagePath, null);
        core_recycler_view.setLayoutManager(new StaggeredGridLayoutManager(4,
                StaggeredGridLayoutManager.VERTICAL));
        core_recycler_view.addItemDecoration(new DividerGridItemDecoration(getActivity()));
        core_recycler_view.setAdapter(mAdapter);
        core_recycler_view.setItemAnimator(new DefaultItemAnimator());

        collectService = new CollectService();
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
        if (mAdapter.getChonsen().size() <= 0) {
            return;
        }
        collectService.deleteCollectionList(mAdapter.getChonsen());
        refreshCollect();
        ToastUtil.showLong("取消成功");
    }


    @OnClick(R.id.folder_tv_lly3)
    public void delete() {
        if (mAdapter.getChonsen().size() <= 0) {
            return;
        }
        SimpleDialog.getDialog(getActivity(), "是否删除", "不删除", null, "删除",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteChosen();
                        ToastUtil.showLong("删除成功");
                    }
                });


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
                    FilesUtil.deleteFile(FilesUtil.getRealPath(null, deleteList.get(i)));
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
        mDatas.add("详细信息");
        menuDialog = (RecyclerDialogFragment) getFragmentManager().findFragmentByTag("menuDialog");
        if (menuDialog == null) {
            menuDialog = RecyclerDialogFragment.getInstance(mDatas);
            menuDialog.setOnMenuDialogListener(this);
        }
        menuDialog.show(getFragmentManager(), "menuDialog");
    }

    @Override
    public void menuDialogClick(String str, int position) {
        ToastUtil.showShort(str);
        if (menuDialog != null) {
            menuDialog.dismiss();
        }
        if (position == 0) {

        } else if (position == 1) {
            if (mAdapter.getChonsen().size() > 1)
                if (mAdapter.getChonsen().size() >= 2) {
                    ToastUtil.showShort("图片多于2张，只对第一张就行编辑");
                }
            gotoImageDetail(mAdapter.getChonsen().get(0));
        }
    }

    public boolean isSelecting() {
        return mAdapter.isSHowCheckBox();
    }

    public void showCheckBox(boolean isShow) {
        mAdapter.showCheckBox(isShow);
        folder_menu_root.setVisibility(View.GONE);
    }

}
