package com.iweijie.design.graduation.gallery.impl;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by liweijie on 2016/5/18.
 */
public interface ComponentsImpl {
    void initView();
    void initData();
    void initEvent();
    void recoverBundle(@Nullable  Bundle savedInstanceState);
}
