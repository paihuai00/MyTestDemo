package com.csx.mlibrary.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.csx.mlibrary.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

//import static android.security.KeyStore.getApplicationContext;

/**
 * @Created by cuishuxiang
 * @date 2018/1/25.
 * base
 */

public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getSimpleName().toString();
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() != 0) setContentView(getLayoutId());
        initImmerseBar();
        mUnbinder = ButterKnife.bind(this);
        //初始化View
        initView();

        //加载数据
        initData();
    }

    private View statusBarView;

    /**
     * 渐变
     * http://www.jb51.net/article/124110.htm
     */
    private void initImmerseBar() {
        if (getSupportActionBar() != null) {
            if (getSupportActionBar().isShowing()) {
                getSupportActionBar().hide();
            }
        }
        //延时加载数据.
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                if (isStatusBar()) {
                    initStatusBar();
                    getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                        @Override
                        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                            initStatusBar();
                        }
                    });
                }
                //只走一次
                return false;
            }
        });
    }

    private void initStatusBar() {
        if (statusBarView == null) {
            int identifier = getResources().getIdentifier("statusBarBackground", "id", "android");
            statusBarView = getWindow().findViewById(identifier);
        }
        //这里设置自己的文件
        if (statusBarView != null) {
            statusBarView.setBackgroundResource(R.drawable.status_bar_alpha);
        }
    }

    protected boolean isStatusBar() {
        return true;
    }


    //加载布局文件
    public abstract int getLayoutId();

    //初始化View
    public abstract void initView();

    //初始化数据
    public abstract void initData();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mUnbinder) {
            mUnbinder.unbind();
        }

    }

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//        }
//    }

    private int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public void showShortToast(@NonNull String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
