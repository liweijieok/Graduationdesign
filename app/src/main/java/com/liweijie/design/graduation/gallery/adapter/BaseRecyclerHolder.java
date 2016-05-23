package com.liweijie.design.graduation.gallery.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by liweijie on 2016/3/28.
 */
public abstract class BaseRecyclerHolder extends RecyclerView.ViewHolder {
    public View itemView;
    public BaseRecyclerHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        ButterKnife.bind(this, itemView);
    }
}
