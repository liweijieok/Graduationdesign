package com.liweijie.design.graduation.gallery.event;

import android.view.View;

/**
 * Created by chencangui on 2016/3/30 0030.
 */
public interface OnRecyclerViewItemLongClickListener<T> {
    void onItemLongClick(View view, T t, int position);
}
