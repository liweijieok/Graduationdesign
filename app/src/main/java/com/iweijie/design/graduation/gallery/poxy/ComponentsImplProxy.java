package com.iweijie.design.graduation.gallery.poxy;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.iweijie.design.graduation.gallery.impl.ComponentsImpl;

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
        recoverBundle(savedInstanceState);
        initView();
        initEvent();
        initData();
    }

    @Override
    public void initView() {
        components.initView();
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
    public void recoverBundle(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return;
        }
        components.recoverBundle(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        int layoutId=  components.getLayoutId();
        if (layoutId == 0) {
            throw new IllegalStateException("Activity或者是Fragment或者View不能没有layout id");
        }
        return layoutId;
    }

    /**
     * 释放
     */
    @Override
    public void destroyComponents() {
        if (components == null) {
            return;
        }
        components.destroyComponents();
        if (components instanceof Activity) {
            if (!((Activity) components).isFinishing()) {
                ((Activity) components).finish();
            }
        }

        components = null;
    }
}
