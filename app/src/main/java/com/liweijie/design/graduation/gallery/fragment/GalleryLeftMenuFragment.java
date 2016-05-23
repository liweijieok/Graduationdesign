package com.liweijie.design.graduation.gallery.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.liweijie.design.graduation.gallery.R;
import com.liweijie.design.graduation.gallery.adapter.LeftMenuAdapter;
import com.liweijie.design.graduation.gallery.base.BaseFragment;
import com.liweijie.design.graduation.gallery.bean.LeftmenuBean;
import com.liweijie.design.graduation.gallery.event.OnRecyclerViewItemClickListener;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by liweijie on 2016/5/19.
 */
public class GalleryLeftMenuFragment extends BaseFragment {

    @Bind(R.id.core_recycler_view)
    RecyclerView core_recycler_view;
    private ArrayList<LeftmenuBean> mLeftMenuData;
    private LeftMenuAdapter mAdapter;

    private String[] titles;
    private int[] icons;

    private int mCurrentIndex;
    private static String KEY_INDEX = "key_index";

    public GalleryLeftMenuFragment() {
    }

    public static GalleryLeftMenuFragment getInstance(int mCurrentIndex) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_INDEX, mCurrentIndex);
        GalleryLeftMenuFragment leftMenuFragment = new GalleryLeftMenuFragment();
        leftMenuFragment.setArguments(bundle);
        return leftMenuFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.leftmenu_fragment;
    }

    @Override
    public void recoverAndBeforeInfalter(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void beforeInit() {
        titles = getActivity().getResources().getStringArray(R.array.left_menu_title);
        icons = new int[]{R.drawable.main_gallery, R.drawable.main_collect, R.drawable.main_secret, R.drawable.main_setting,
                R.drawable.main_about};
        mCurrentIndex = getArguments().getInt(KEY_INDEX);
    }

    @Override
    public void initEvent() {
        mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener<LeftmenuBean>() {
            @Override
            public void onItemClick(View view, LeftmenuBean bean, int position) {

                if (mListener != null) {
                    mAdapter.setSelected(position);
                    mListener.menuItemSelected(bean.getTitle(), position);

                }
            }
        });
    }

    @Override
    public void initData() {
        mLeftMenuData = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            LeftmenuBean bean = new LeftmenuBean();
            bean.setIcon(icons[i]);
            bean.setTitle(titles[i]);
            mLeftMenuData.add(bean);
        }

        mAdapter = new LeftMenuAdapter(mLeftMenuData);
        core_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        core_recycler_view.setAdapter(mAdapter);

    }

    // 回调
    public interface OnMenuItemSelectedListener {
        void menuItemSelected(String title, int position);
    }

    public OnMenuItemSelectedListener mListener;

    public void setOnMenuItemSelectedListener(OnMenuItemSelectedListener mListener) {
        this.mListener = mListener;
    }

}
