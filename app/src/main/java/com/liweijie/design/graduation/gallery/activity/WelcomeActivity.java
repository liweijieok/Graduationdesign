package com.liweijie.design.graduation.gallery.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

import com.liweijie.design.graduation.gallery.GalleryMainActivity;
import com.liweijie.design.graduation.gallery.R;
import com.liweijie.design.graduation.gallery.base.BaseActivity;

/**
 * Created by liweijie on 2016/5/18.
 */
public class WelcomeActivity extends BaseActivity {
    @Override
    public void beforeInit() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
    }

    @Override
    public void initData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this,
                        GalleryMainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1500);
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void recoverAndBeforeInfalter(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }


}
