package com.liweijie.design.graduation.gallery.coer;


import com.litesuits.go.OverloadPolicy;
import com.litesuits.go.SchedulePolicy;
import com.litesuits.go.SmartExecutor;
import com.litesuits.go.utils.GoUtil;
import com.liweijie.design.graduation.gallery.app.GalleryConstants;

/**
 * Created by chencangui on 2016/3/31 0031.
 * 线程池管理类
 */
public class ThreadPoolManager {

    private static SmartExecutor smartExecutor;
    private static final int DEFAULT_CORE_SIZE = GoUtil.getCoresNumbers();
    private static final int DEFAULT_QUEUE_SIZE = 3;


    public static SmartExecutor getInstance() {
        if (null == smartExecutor) {
            smartExecutor = getInstance(DEFAULT_CORE_SIZE, DEFAULT_QUEUE_SIZE);
        }
        return smartExecutor;
    }


    public static SmartExecutor getInstance(int coreSize, int queueSize) {
        if (null == smartExecutor) {
            synchronized (ThreadPoolManager.class) {
                if (null == smartExecutor) {
                    smartExecutor = new SmartExecutor(coreSize, queueSize);
                    smartExecutor.setSchedulePolicy(SchedulePolicy.LastInFirstRun);
                    smartExecutor.setOverloadPolicy(OverloadPolicy.DiscardOldTaskInQueue);
                    smartExecutor.setDebug(!GalleryConstants.IS_DEBUG);
                }
            }
        }
        return smartExecutor;
    }

}
