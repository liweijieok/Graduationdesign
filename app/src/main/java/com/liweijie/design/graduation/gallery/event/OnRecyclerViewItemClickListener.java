package com.liweijie.design.graduation.gallery.event;

import android.view.View;

/**
 * Created by chencangui on 2016/3/30 0030.
 */
public interface OnRecyclerViewItemClickListener<T> {
    void onItemClick(View view, T t, int position);
}
