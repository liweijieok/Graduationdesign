package com.liweijie.design.graduation.gallery;

import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.liweijie.design.graduation.gallery.base.BaseActivity;
import com.liweijie.design.graduation.gallery.fragment.GalleryAboutFragment;
import com.liweijie.design.graduation.gallery.fragment.GalleryCollectFragment;
import com.liweijie.design.graduation.gallery.fragment.content.GalleryContentFragment;
import com.liweijie.design.graduation.gallery.fragment.GalleryLeftMenuFragment;
import com.liweijie.design.graduation.gallery.fragment.GallerySecretFragment;
import com.liweijie.design.graduation.gallery.fragment.GallerySettingFragment;

import butterknife.Bind;

public class GalleryMainActivity extends BaseActivity implements GalleryLeftMenuFragment.OnMenuItemSelectedListener{
    @Bind(R.id.main_toolbar)
    Toolbar main_toolbar;
    @Bind(R.id.main_drawerlayout)
    DrawerLayout main_drawerlayout;
    private ActionBarDrawerToggle mDrawerToogler;

    private String mTitle;//标题
    private int mCurrentIndex;// 目前的Fragment下标
    private final String KEY_TITLE = "ket_title";
    private final String KEY_INDEX = "ket_index";
    private boolean isLeftMenuOpen = false;

    //内容页面
    private Class[] mFragments = {GalleryContentFragment.class, GalleryCollectFragment.class,
            GallerySecretFragment.class, GallerySettingFragment.class, GalleryAboutFragment.class};
    private FragmentManager fm;
    private GalleryLeftMenuFragment mLeftMenu;

    @Override
    public void beforeInit() {
        mTitle = getResources().getStringArray(R.array.left_menu_title)[0];
        mCurrentIndex = 0;
    }

    @Override
    public void initData() {
        main_toolbar.setTitle(mTitle);
        setSupportActionBar(main_toolbar);
        fm = getSupportFragmentManager();
        mLeftMenu = (GalleryLeftMenuFragment) fm.findFragmentById(R.id.main_framelayout_menu);
        if (mLeftMenu == null) {
            mLeftMenu = GalleryLeftMenuFragment.getInstance(mCurrentIndex);
            fm.beginTransaction().add(R.id.main_framelayout_menu, mLeftMenu).commit();
        }

        mDrawerToogler = new ActionBarDrawerToggle(this, main_drawerlayout, main_toolbar,
                R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                isLeftMenuOpen = true;
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                isLeftMenuOpen = false;
            }
        };

        main_drawerlayout.addDrawerListener(mDrawerToogler);
        mDrawerToogler.syncState();
        showFragment(0);

    }

    private void showFragment(int i) {
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        hide(fragmentTransaction);
        showFragment(i, fragmentTransaction,mFragments[i]);
        fragmentTransaction.commit();

    }

    private <T extends Fragment> void showFragment(int index, FragmentTransaction fragmentTransaction,Class<T> clazz) {
        Fragment fg = fm.findFragmentByTag(String.valueOf(index));
        if (fg == null) {
            try {
                fg = clazz.newInstance();
                fragmentTransaction.add(R.id.main_framelayout_content, fg, String.valueOf(index));
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }else {
            fragmentTransaction.show(fg);
        }
    }

    private void hide(FragmentTransaction fragmentTransaction) {
        for (int i=0;i<mFragments.length;i++) {
            Fragment fg = fm.findFragmentByTag(String.valueOf(i));
            if (fg != null) {
                fragmentTransaction.hide(fg);
            }
        }
    }

    @Override
    public void initEvent() {
        mLeftMenu.setOnMenuItemSelectedListener(this);
    }

    @Override
    public void recoverAndBeforeInfalter(@Nullable Bundle savedInstanceState) {
        mTitle = savedInstanceState.getString(KEY_TITLE,null);
        mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);
        if (TextUtils.isEmpty(mTitle)) {
            mTitle = getResources().getStringArray(R.array.left_menu_title)[0];
            mCurrentIndex = 0;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.gallery_main_activity;
    }


    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        mDrawerToogler.syncState();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt(KEY_INDEX, mCurrentIndex);
        outState.putString(KEY_TITLE, mTitle);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && isLeftMenuOpen) {
            main_drawerlayout.closeDrawers();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void menuItemSelected(String title, int position) {
        main_drawerlayout.closeDrawers();
        if (position != mCurrentIndex) {
            mCurrentIndex = position;
            showFragment(position);
            mTitle = title;
            main_toolbar.setTitle(mTitle);
        }
    }
}
