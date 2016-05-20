package com.liweijie.design.graduation.gallery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.liweijie.design.graduation.gallery.R;
import com.liweijie.design.graduation.gallery.base.BaseFragment;

/**
 * Created by liweijie on 2016/5/19.
 */
public class GalleryLeftMenuFragment extends BaseFragment {

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
        return R.layout.other;
    }

    @Override
    public void recoverAndBeforeInfalter(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void beforeInit() {
        mCurrentIndex = getArguments().getInt(KEY_INDEX);
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {

    }

}
