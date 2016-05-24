package com.liweijie.design.graduation.gallery.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;

import com.liweijie.design.graduation.gallery.R;
import com.liweijie.design.graduation.gallery.base.BaseFragment;

import butterknife.Bind;

/**
 * Created by liweijie on 2016/5/24.
 */
public abstract class BaseGridFragment extends BaseFragment{
    @Bind(R.id.core_recycler_view)
    protected RecyclerView core_recycler_view;

    protected void setRecycler(){
        RecyclerView.LayoutManager manager =  new GridLayoutManager(getContext(), 2);
        core_recycler_view.setLayoutManager(getLayoutManager());
    }

    public abstract RecyclerView.LayoutManager getLayoutManager();
}
