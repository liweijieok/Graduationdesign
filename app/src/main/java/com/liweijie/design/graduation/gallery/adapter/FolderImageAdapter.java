package com.liweijie.design.graduation.gallery.adapter;

import android.os.Handler;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.liweijie.design.graduation.gallery.R;
import com.liweijie.design.graduation.gallery.bean.FolderImageBean;
import com.liweijie.design.graduation.gallery.coer.MyImageLoader;
import com.liweijie.design.graduation.gallery.util.FilesUtil;
import com.liweijie.design.graduation.gallery.util.L;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by liweijie on 2016/5/24.
 */
public class FolderImageAdapter extends BaseRecyclerAdapter<FolderImageBean> {
    private String mDir;
    private List<String> selected;

    public FolderImageAdapter(List<FolderImageBean> mDatas, String dir) {
        super(mDatas, 1, 1);
        this.mDir = dir;
        selected = new ArrayList<>();
    }

    private boolean isShowCheckBox;

    public List<String> getChonsen() {
        return selected;
    }

    public boolean isSHowCheckBox() {
        return isShowCheckBox;
    }


    public void choosenAll(boolean choosenAll) {
        if (choosenAll) {
            selected.clear();
            for (int i = 0; i < getDatas().size(); i++) {
                selected.add(getDatas().get(i).getPath());
                getDatas().get(i).setSelected(true);

            }
        } else {
            selected.clear();
            for (int i = 0; i < getDatas().size(); i++) {
                getDatas().get(i).setSelected(false);
            }
        }

        notifyDataSetChanged();
    }

    public void showCheckBox(boolean isShow) {
        isShowCheckBox = isShow;
        if (isShowCheckBox) {

        } else {
            selected.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public BaseRecyclerHolder getHolder(View itemtView) {
        return new FolderHolder(itemtView);
    }

    @Override
    public int getItemLayout() {
        return R.layout.item_foldr_image;
    }

    @Override
    public void converHolderView(BaseRecyclerHolder holder, final FolderImageBean s, final int position) {
        final FolderHolder localHolder = (FolderHolder) holder;
        String imaePath = FilesUtil.getRealPath(mDir, s.getPath());
        // 第一步复原
        localHolder.item_folder_iv.setImageResource(R.drawable.no_picture);
        if (isShowCheckBox) {
            localHolder.item_folder_cv.setVisibility(View.VISIBLE);

        } else {
            localHolder.item_folder_cv.setVisibility(View.GONE);
        }

        if (s.isSelected()) {
            localHolder.item_folder_cv.setChecked(true);
        } else {
            localHolder.item_folder_cv.setChecked(false);
        }
        localHolder.item_folder_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = localHolder.item_folder_cv.isChecked();
                s.setSelected(isChecked);
                notifyItemChanged(position);
                if (isChecked) {
                    selected.add(s.getPath());
                } else {
                    selected.remove(s.getPath());
                }

            }
        });
        MyImageLoader.getInstance(2, MyImageLoader.Type.LIFO).loadImage(imaePath, localHolder.item_folder_iv);
    }

    @Override
    public void converBottomHolderView(BaseRecyclerHolder holder) {
        super.converBottomHolderView(holder);
//        //设置占满全格
        StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 20);
        layoutParams.setFullSpan(true);
        holder.itemView.setLayoutParams(layoutParams);

    }

    @Override
    public void converHeaderHolderView(BaseRecyclerHolder holder) {
        super.converHeaderHolderView(holder);
//        //设置占满全格
        StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 20);
        layoutParams.setFullSpan(true);
        holder.itemView.setLayoutParams(layoutParams);
    }

    @Override
    public int getBottomLayout() {
        return R.layout.core_blank_view;
    }

    @Override
    public int getHeaderLayout() {
        return R.layout.core_blank_view;
    }

    @Override
    public BaseRecyclerHolder getBottomHolder(View itemtView) {

        return new FolderBottomHolder(itemtView);
    }

    @Override
    public BaseRecyclerHolder getHeaderHolder(View itemtView) {
        return new FolderHeaderHolder(itemtView);
    }

    class FolderHolder extends BaseRecyclerHolder {

        @Bind(R.id.item_folder_iv)
        ImageView item_folder_iv;
        @Bind(R.id.item_folder_cv)
        CheckBox item_folder_cv;

        public FolderHolder(View itemView) {
            super(itemView);
        }
    }

    class FolderBottomHolder extends BaseRecyclerHolder {

        public FolderBottomHolder(View itemView) {
            super(itemView);
        }
    }

    class FolderHeaderHolder extends BaseRecyclerHolder {

        public FolderHeaderHolder(View itemView) {
            super(itemView);
        }
    }


}
