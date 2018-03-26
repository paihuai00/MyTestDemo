package com.csx.mytestdemo.immerse_state_bar;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.view.View;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.R;

/**
 * Created by cuishuxiang on 2018/3/24.
 *
 * 沉浸式 状态栏
 */

public class StatusBarActivity extends BaseActivity {
    private static final String TAG = "StatusBarActivity";

    @Override
    public int getLayoutId() {
        return R.layout.activity_state_inner;
    }

    @Override
    public void initView() {
//        StatusBarUtils.setStatusBarColor(this, Color.RED);
//        StatusBarUtils.setActivityTranslucent(this);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            //flag 隐藏navigation_bar，全屏
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //设置status navigation 为透明
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        //隐藏ActionBar
        if (getSupportActionBar() != null && getSupportActionBar().isShowing()) {
            getSupportActionBar().hide();
        }

    }

    @Override
    public void initData() {

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

}
