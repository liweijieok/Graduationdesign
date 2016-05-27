package com.liweijie.design.graduation.gallery.activity.gallery;

import android.graphics.Bitmap;

import com.liweijie.design.graduation.gallery.coer.ImageUtil;

/**
 * Created by liweijie on 2016/5/26.
 */
public class ImageFunctioin {

    public static Bitmap getBitmap(String filter_name, Bitmap bitmap) {
        if (filter_name.equals("原图")) {
            return bitmap;
        } else if (filter_name == "圆角") {
            bitmap = ImageUtil.getRoundedCornerBitmap(bitmap, 30);
        } else if (filter_name == "倒影") {
            bitmap = ImageUtil.createReflectionImageWithOrigin(bitmap);
        } else if (filter_name == "灰度") {
            bitmap = ImageUtil.toGrayscale(bitmap);
        } else if (filter_name == "浮雕") {
            bitmap = ImageUtil.toFuDiao(bitmap);
        } else if (filter_name == "模糊") {
            bitmap = ImageUtil.toMohu(bitmap, 20);
        } else if (filter_name == "黑白") {
            bitmap = ImageUtil.toHeibai(bitmap);
        } else if (filter_name == "油画") {
            bitmap = ImageUtil.toYouHua(bitmap);
        } else if (filter_name == "底片") {
            bitmap = ImageUtil.func(bitmap);
        } else if (filter_name == "光照") {
            bitmap = ImageUtil.sunshineImage(bitmap);
        } else if (filter_name == "泛黄") {
            bitmap = ImageUtil.testBitmap(bitmap);
        }

        return bitmap;
    }


}
