package com.liweijie.design.graduation.gallery.impl;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by liweijie on 2016/5/18.
 */
public interface ComponentsImpl {
    /**
     * 获取布局id
     *
     * @return layout id
     */
    int getLayoutId();

    /**
     * 恢复数据
     *
     * @param savedInstanceState
     */
    void recoverAndBeforeInfalter(@Nullable Bundle savedInstanceState);

    /**
     * initview，data等东西之前处理,比如全屏之类
     */
    void beforeInit();

    /**
     * 初始化事件监听
     */
    void initEvent();

    /**
     * 初始化基本数据
     */
    void initData();

}
