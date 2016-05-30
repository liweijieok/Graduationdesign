package com.liweijie.design.graduation.gallery.fragment.secret;

import com.liweijie.design.graduation.gallery.app.GalleryConstants;
import com.liweijie.design.graduation.gallery.bean.SecretInfo;
import com.liweijie.design.graduation.gallery.db.SecretService;
import com.liweijie.design.graduation.gallery.util.FilesUtil;
import com.liweijie.design.graduation.gallery.util.L;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liweijie on 2016/5/28.
 */
public class SecretHelper {
    private SecretService service;

    public SecretHelper() {
        service = new SecretService();
    }

    public List<String> readFromDB(){
        List<String> strings = new ArrayList<>();
        File parent =  FilesUtil.createFileDir(GalleryConstants.SECRET_FILE_NAME);

        List<SecretInfo> infoListZ = service.getSecretBeanList();
        L.i("SecretGET", infoListZ.size()+"da");
        for (SecretInfo info : infoListZ) {
            String fileName = info.getFileName()+info.getFormat();
            L.i("SecretGET", info.toString());

            File file = new File(parent,fileName);
            L.i("SecretGET", file.getAbsolutePath());
            if (!FilesUtil.isExists(file)) {
                service.deleteCollect( info.getFileName());
            }else {
                strings.add(file.getAbsolutePath());
            }
        }
        return strings;
    }
}
