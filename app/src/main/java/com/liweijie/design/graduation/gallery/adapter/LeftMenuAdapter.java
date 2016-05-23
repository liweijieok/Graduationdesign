package com.liweijie.design.graduation.gallery.adapter;

import android.view.View;

import com.liweijie.design.graduation.gallery.R;
import com.liweijie.design.graduation.gallery.bean.LeftmenuBean;

import java.util.List;

/**
 * Created by liweijie on 2016/5/21.
 */
public class LeftMenuAdapter extends BaseRecyclerAdapter<LeftmenuBean> {

    public LeftMenuAdapter(List<LeftmenuBean> mDatas) {
        super(mDatas);
    }

    @Override
    public BaseRecyclerHolder getHolder(View itemtView, int viewType) {
        return new LeftMenuHolder(itemtView);
    }

    @Override
    public int getItemLayout() {
        return R.layout.item_left_menu;
    }

    @Override
    public void converHolderView(BaseRecyclerHolder holder, LeftmenuBean leftmenuBean, int position) {

    }

    class LeftMenuHolder extends BaseRecyclerHolder {
        public LeftMenuHolder(View itemView) {
            super(itemView);
        }
    }

}
