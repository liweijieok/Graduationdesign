package com.liweijie.design.graduation.gallery.fragment.collect;

import com.liweijie.design.graduation.gallery.util.FilesUtil;
import com.liweijie.design.graduation.gallery.util.L;

import java.util.List;

/**
 * Created by liweijie on 2016/5/27.
 */
public class CollectHelper {
    private com.liweijie.design.graduation.gallery.db.CollectService service;

    public CollectHelper() {
        service = new com.liweijie.design.graduation.gallery.db.CollectService();
    }

    public List<String> readFromDB() {
        List<String> data = service.getCollectBeanList();
        L.i("collectGet", data.size()+"");
        for (int i = 0; i < data.size(); i++) {
            if (!FilesUtil.isExists(data.get(i))) {
                service.deleteCollect(data.get(i));
                data.remove(i);
            }

        }
        return data;

    }

}
