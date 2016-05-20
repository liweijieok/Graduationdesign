package com.liweijie.design.graduation.gallery.poxy;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.liweijie.design.graduation.gallery.impl.ComponentsImpl;
import com.liweijie.design.graduation.gallery.util.L;

/**
 * Created by liweijie on 2016/5/19.
 * 代理模式，代理组件初始化
 */
public class ComponentsImplProxy implements ComponentsImpl {

    private ComponentsImpl components;

    public ComponentsImplProxy(ComponentsImpl components) {
        this.components = components;
    }

    public void init(Bundle savedInstanceState) {
        recoverAndBeforeInfalter(savedInstanceState);
        initEvent();
        initData();
    }

    @Override
    public void beforeInit() {
        components.beforeInit();
    }

    @Override
    public void initData() {
        components.initData();
    }

    @Override
    public void initEvent() {
        components.initEvent();
    }

    @Override
    public void recoverAndBeforeInfalter(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return;
        }
        components.recoverAndBeforeInfalter(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        int layoutId = components.getLayoutId();
        if (layoutId == 0) {
            L.e("Activity或者是Fragment或者View不能没有layout id");
            throw new IllegalStateException("Activity或者是Fragment或者View不能没有layout id");
        }
        return layoutId;
    }

    /**
     * 释放
     */

    public void destroyComponents() {
        if (components == null) {
            return;
        }
        if (components instanceof Activity) {
            if (!((Activity) components).isFinishing()) {
                ((Activity) components).finish();
            }
        }

        components = null;
    }
}
