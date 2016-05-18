package com.iweijie.design.graduation.gallery.Base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.iweijie.design.graduation.gallery.impl.ComponentsImpl;

import butterknife.ButterKnife;

/**
 * Created by liweijie on 2016/5/18.
 */
public abstract class BaseActivity  extends AppCompatActivity implements ComponentsImpl {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recoverBundle(savedInstanceState);
        initView();
        ButterKnife.bind(this);
        initEvent();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
