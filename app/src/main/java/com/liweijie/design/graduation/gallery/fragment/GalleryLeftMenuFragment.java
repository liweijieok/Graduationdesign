package com.liweijie.design.graduation.gallery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.liweijie.design.graduation.gallery.R;
import com.liweijie.design.graduation.gallery.base.BaseFragment;
import com.liweijie.design.graduation.gallery.bean.LeftmenuBean;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by liweijie on 2016/5/19.
 *
 */
public class GalleryLeftMenuFragment extends BaseFragment {

    @Bind(R.id.core_recycler_view)
    private ArrayList<LeftmenuBean> mLeftMenu;

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
        icons = new int[]{R.drawable.main_gallery, R.drawable.main_collect, R.drawable.main_setting, R.drawable.main_about,
                R.drawable.main_about};
        mCurrentIndex = getArguments().getInt(KEY_INDEX);
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {
        mLeftMenu = new ArrayList<>();
        for (int i=0;i<titles.length;i++) {
            LeftmenuBean bean = new LeftmenuBean();
            bean.setIcon(icons[i]);
            bean.setTitle(titles[i]);
            mLeftMenu.add(bean);
        }

    }

}
