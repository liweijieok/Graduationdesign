package com.liweijie.design.graduation.gallery.coer;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;

public class ImageUtil {

    // 放大缩小图片
    public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidht = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidht, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height,
                matrix, true);
        return newbmp;
    }

    // Drawable to Bitmap
    public static Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
                .getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;

    }

    // 圆角化
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    // 倒影
    public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
        final int reflectionGap = 4;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2,
                width, height / 2, matrix, false);

        Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
                (height + height / 2), Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(bitmap, 0, 0, null);
        Paint deafalutPaint = new Paint();
        canvas.drawRect(0, height, width, height + reflectionGap, deafalutPaint);

        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
                bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
                0x00ffffff, TileMode.CLAMP);
        paint.setShader(shader);
        // Set the Transfer mode to be porter duff and destination in
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        // Draw a rectangle using the paint with our linear gradient
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
                + reflectionGap, paint);

        return bitmapWithReflection;
    }

    public static Bitmap createShadowBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);

        Canvas canvas = new Canvas(output);
        canvas.drawColor(Color.WHITE);
        // canvas.save(Canvas.MATRIX_SAVE_FLAG);

        int PicWidth = bitmap.getWidth();
        int PicHegiht = bitmap.getHeight();
        int posX = 20;
        int posY = 50;

        Rect rect = new Rect(0, 0, PicWidth, PicHegiht);

        Paint paint = new Paint();//
        paint.setAntiAlias(true);//
        // paint.setColor(Color.CYAN);
        paint.setShadowLayer(10f, 10.0f, 10.0f, Color.BLACK);//
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));

        RectF rectF = new RectF(rect);
        // canvas.drawRoundRect(rectF,20,20, paint);
        // canvas.drawRect(rectF, paint);
        // canvas.drawBitmap(bitmap, 2, 2, null);
        return output;
    }

    // 灰度
    public static Bitmap toGrayscale(Bitmap bmpOriginal) {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height,
                Bitmap.Config.RGB_565);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }

    // 浮雕
    public static Bitmap toFuDiao(Bitmap mBitmap) {
        Paint mPaint;

        int mBitmapWidth = 0;
        int mBitmapHeight = 0;

        int mArrayColor[] = null;
        int mArrayColorLengh = 0;
        long startTime = 0;
        int mBackVolume = 0;

        mBitmapWidth = mBitmap.getWidth();
        mBitmapHeight = mBitmap.getHeight();

        Bitmap bmpReturn = Bitmap.createBitmap(mBitmapWidth, mBitmapHeight,
                Bitmap.Config.RGB_565);
        mArrayColorLengh = mBitmapWidth * mBitmapHeight;
        int count = 0;
        int preColor = 0;
        int prepreColor = 0;
        int color = 0;
        preColor = mBitmap.getPixel(0, 0);

        for (int i = 0; i < mBitmapWidth; i++) {
            for (int j = 0; j < mBitmapHeight; j++) {
                int curr_color = mBitmap.getPixel(i, j);
                int r = Color.red(curr_color) - Color.red(prepreColor) + 128;
                int g = Color.green(curr_color) - Color.red(prepreColor) + 128;
                int b = Color.green(curr_color) - Color.blue(prepreColor) + 128;
                int a = Color.alpha(curr_color);
                int modif_color = Color.argb(a, r, g, b);
                bmpReturn.setPixel(i, j, modif_color);
                prepreColor = preColor;
                preColor = curr_color;
            }
        }

        Canvas c = new Canvas(bmpReturn);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpReturn, 0, 0, paint);

        return bmpReturn;
    }

    /* 模糊 */
    public static Bitmap toMohu(Bitmap bmpSource, int Blur) {
        Bitmap bmpReturn = Bitmap.createBitmap(bmpSource.getWidth(),
                bmpSource.getHeight(), Bitmap.Config.ARGB_8888);
        int pixels[] = new int[bmpSource.getWidth() * bmpSource.getHeight()];
        int pixelsRawSource[] = new int[bmpSource.getWidth()
                * bmpSource.getHeight() * 3];
        int pixelsRawNew[] = new int[bmpSource.getWidth()
                * bmpSource.getHeight() * 3];

        bmpSource.getPixels(pixels, 0, bmpSource.getWidth(), 0, 0,
                bmpSource.getWidth(), bmpSource.getHeight());

        for (int k = 1; k <= Blur; k++) {
            for (int i = 0; i < pixels.length; i++) {
                pixelsRawSource[i * 3 + 0] = Color.red(pixels[i]);
                pixelsRawSource[i * 3 + 1] = Color.green(pixels[i]);
                pixelsRawSource[i * 3 + 2] = Color.blue(pixels[i]);
            }
            int CurrentPixel = bmpSource.getWidth() * 3 + 3;
            for (int i = 0; i < bmpSource.getHeight() - 3; i++) {
                for (int j = 0; j < bmpSource.getWidth() * 3; j++) {
                    CurrentPixel += 1;
                    int sumColor = 0;
                    sumColor = pixelsRawSource[CurrentPixel
                            - bmpSource.getWidth() * 3];
                    sumColor = sumColor + pixelsRawSource[CurrentPixel - 3];
                    sumColor = sumColor + pixelsRawSource[CurrentPixel + 3];
                    sumColor = sumColor
                            + pixelsRawSource[CurrentPixel
                            + bmpSource.getWidth() * 3];
                    pixelsRawNew[CurrentPixel] = Math.round(sumColor / 4);
                }
            }

            for (int i = 0; i < pixels.length; i++) {
                pixels[i] = Color.rgb(pixelsRawNew[i * 3 + 0],
                        pixelsRawNew[i * 3 + 1], pixelsRawNew[i * 3 + 2]);
            }
        }

        bmpReturn.setPixels(pixels, 0, bmpSource.getWidth(), 0, 0,
                bmpSource.getWidth(), bmpSource.getHeight());
        return bmpReturn;
    }

    /* 黑白 */
    public static Bitmap toHeibai(Bitmap mBitmap) {
        Paint mPaint;
        int mBitmapWidth = 0;
        int mBitmapHeight = 0;
        int mArrayColor[] = null;
        int mArrayColorLengh = 0;
        long startTime = 0;
        int mBackVolume = 0;

        mBitmapWidth = mBitmap.getWidth();
        mBitmapHeight = mBitmap.getHeight();
        Bitmap bmpReturn = Bitmap.createBitmap(mBitmapWidth, mBitmapHeight,
                Bitmap.Config.ARGB_8888);
        mArrayColorLengh = mBitmapWidth * mBitmapHeight;
        int count = 0;
        int preColor = 0;
        int color = 0;

        int iPixel = 0;
        for (int i = 0; i < mBitmapWidth; i++) {
            for (int j = 0; j < mBitmapHeight; j++) {
                int curr_color = mBitmap.getPixel(i, j);

                int avg = (Color.red(curr_color) + Color.green(curr_color) + Color
                        .blue(curr_color)) / 3;
                if (avg >= 100) {
                    iPixel = 255;
                } else {
                    iPixel = 0;
                }
                int modif_color = Color.argb(255, iPixel, iPixel, iPixel);

                bmpReturn.setPixel(i, j, modif_color);
            }
        }
        return bmpReturn;
    }

    /* 油画*/
    public static Bitmap toYouHua(Bitmap bmpSource) {
        Bitmap bmpReturn = Bitmap.createBitmap(bmpSource.getWidth(),
                bmpSource.getHeight(), Bitmap.Config.RGB_565);
        int color = 0;
        int Radio = 0;
        int width = bmpSource.getWidth();
        int height = bmpSource.getHeight();

        Random rnd = new Random();
        int iModel = 10;
        int i = width - iModel;
        while (i > 1) {
            int j = height - iModel;
            while (j > 1) {
                int iPos = rnd.nextInt(100000) % iModel;
                color = bmpSource.getPixel(i + iPos, j + iPos);
                bmpReturn.setPixel(i, j, color);
                j = j - 1;
            }
            i = i - 1;
        }
        return bmpReturn;
    }

    /**
     * 底片效果
     * 注意图片分辨率不能太高防止oom
     */
    public static Bitmap func(Bitmap bmpSource)
    {
        //原图
        int width = bmpSource.getWidth();
        int height = bmpSource.getHeight();
        //创建可写的bitmap
        Bitmap base = Bitmap.createBitmap(width,height,bmpSource.getConfig());
        Paint paint = new Paint();//新建画笔
//        paint.setStrokeWidth(1);

//        新建画布
        Canvas canvas = new Canvas(base);
        canvas.drawBitmap(bmpSource, new Matrix(), paint);
        for(int i = 0; i < width; i++)
        {
            for(int j = 0; j < height; j++)
            {
                int color = bmpSource.getPixel(i,j);
                int r = Color.red(color);
                int g = Color.green(color);
                int b = Color.blue(color);
                int a = Color.alpha(color);
                //修改图片每个点的像素的rgb值
                base.setPixel(i,j, Color.argb(a, 255-r, 255-g, 255-b));
            }
        }
        bmpSource.recycle();
        return base;
    }

    /**
     * 泛黄
     *
     * @param bitmap
     * @return
     */
    public static Bitmap testBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.RGB_565);

        Canvas canvas = new Canvas(output);

        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        float[] array = {1, 0, 0, 0, 50,
                0, 1, 0, 0, 50,
                0, 0, 1, 0, 0,
                0, 0, 0, 1, 0};
        cm.set(array);
        paint.setColorFilter(new ColorMatrixColorFilter(cm));

        canvas.drawBitmap(bitmap, 0, 0, paint);
        return output;
    }


    //图片光照效果
    public static Bitmap sunshineImage(Bitmap bmpSource)
    {
  /*
   * 算法原理：(前一个像素点RGB-当前像素点RGB+127)作为当前像素点RGB值
   * 在ABC中计算B点浮雕效果(RGB值在0~255)
   * B.r = C.r - B.r + 127
   * B.g = C.g - B.g + 127
   * B.b = C.b - B.b + 127
   * 光照中心取长宽较小值为半径,也可以自定义从左上角射过来
   */
        int width = bmpSource.getWidth();
        int height = bmpSource.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        int pixColor = 0;
        int pixR = 0;
        int pixG = 0;
        int pixB = 0;
        int newR = 0;
        int newG = 0;
        int newB = 0;
        //围绕圆形光照
        int centerX = width / 2;
        int centerY = height / 2;
        int radius = Math.min(centerX, centerY);
        float strength = 150F;  //光照强度100-150
        int[] pixels = new int[width * height];
        bmpSource.getPixels(pixels, 0, width, 0, 0, width, height);
        for (int i = 1; i < height-1; i++)
        {
            for (int k = 1; k < width-1; k++)
            {
                //获取前一个像素颜色
                pixColor = pixels[width * i + k];
                pixR = Color.red(pixColor);
                pixG = Color.green(pixColor);
                pixB = Color.blue(pixColor);
                newR = pixR;
                newG = pixG;
                newB = pixB;
                //计算当前点到光照中心的距离,平面坐标系中两点之间的距离
                int distance = (int) (Math.pow((centerY-i), 2) + Math.pow((centerX-k), 2));
                if(distance < radius*radius)
                {
                    //按照距离大小计算增强的光照值
                    int result = (int)(strength*( 1.0-Math.sqrt(distance) / radius ));
                    newR = pixR + result;
                    newG = newG + result;
                    newB = pixB + result;
                }
                newR = Math.min(255, Math.max(0, newR));
                newG = Math.min(255, Math.max(0, newG));
                newB = Math.min(255, Math.max(0, newB));
                pixels[width * i + k] = Color.argb(255, newR, newG, newB);
            }
        }
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
       return bitmap;
    }

    public static Bitmap scaleBitmap(Bitmap bitmap, float scale) {
        Bitmap output = Bitmap.createBitmap((int) (bitmap.getWidth() * scale),
                (int) (bitmap.getHeight() * scale), Config.RGB_565);

        Canvas canvas = new Canvas(output);

        Paint paint = new Paint();
        Matrix cm = new Matrix();

        float[] array = {1 * scale, 0, 0,
                0, 1 * scale, 0,
                0, 0, 1};
        cm.setValues(array);
        canvas.drawBitmap(bitmap, cm, paint);
        bitmap.recycle();
        return output;
    }
}