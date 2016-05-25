package com.liweijie.design.graduation.gallery.adapter;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.liweijie.design.graduation.gallery.R;
import com.liweijie.design.graduation.gallery.coer.MyImageLoader;
import com.liweijie.design.graduation.gallery.util.FilesUtil;

import java.util.List;

import butterknife.Bind;

/**
 * Created by liweijie on 2016/5/24.
 */
public class FolderImageAdapter extends BaseRecyclerAdapter<String> {
    private String mDir;

    public FolderImageAdapter(List<String> mDatas, String dir) {
        super(mDatas, 1, 1);
        this.mDir = dir;
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
    public void converHolderView(BaseRecyclerHolder holder, String s, int position) {
        FolderHolder localHolder = (FolderHolder) holder;
        String imaePath = FilesUtil.getRealPath(mDir, s);
        // 第一步复原
        localHolder.item_folder_iv.setImageResource(R.drawable.no_picture);
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
        StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,20);
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
