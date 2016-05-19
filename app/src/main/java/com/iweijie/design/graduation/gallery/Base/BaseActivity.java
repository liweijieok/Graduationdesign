package com.iweijie.design.graduation.gallery.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.iweijie.design.graduation.gallery.impl.ComponentsImpl;
import com.iweijie.design.graduation.gallery.poxy.ComponentsImplProxy;
import com.iweijie.design.graduation.gallery.util.LG;

import butterknife.ButterKnife;

/**
 * Created by liweijie on 2016/5/18.
 */
public abstract class BaseActivity extends AppCompatActivity implements ComponentsImpl {
    private ComponentsImplProxy componentsImplProxy;
    protected LG BL;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        componentsImplProxy = new ComponentsImplProxy(this);
        int layoutId = componentsImplProxy.getLayoutId();
        setContentView(layoutId);
        ButterKnife.bind(this);
        BL = new LG(this.getClass());
        componentsImplProxy.init(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        BL.d("AppCompatActivity onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        BL.d("AppCompatActivity onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        BL.d("AppCompatActivity onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        BL.d("AppCompatActivity onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        BL.d("AppCompatActivity onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BL.d("AppCompatActivity onDestroy");
        ButterKnife.unbind(this);
        componentsImplProxy.destroyComponents();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        BL.d("AppCompatActivity onLowMemory");
    }
}
