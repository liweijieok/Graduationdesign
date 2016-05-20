package com.liweijie.design.graduation.gallery.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by liweijie on 2016/5/18.
 * 获取唯一int
 */
public class AtomicIntegerUtil {
    private static AtomicInteger NEW_INT = new AtomicInteger(1);

    public static int getNewInt(){
        return NEW_INT.getAndIncrement();
    }
}
