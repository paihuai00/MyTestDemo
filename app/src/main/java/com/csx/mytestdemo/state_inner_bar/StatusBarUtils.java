package com.csx.mytestdemo.state_inner_bar;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * Created by cuishuxiang on 2018/3/24.
 *
 * 沉浸式状态栏 utils
 */

public class StatusBarUtils {

    public static void setStatusBarColor(Activity activity, int color) {
        //5.0以上 直接调用系统的方法
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(color);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            /**
             * 4.4-5.0之间 特殊处理
             *
             * 1，设置全屏
             * 2，在状态栏的位置，添加一个view布局
             */
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            View view = new View(activity);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    getStatusBarHeight(activity)));
            view.setBackgroundColor(color);

            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            decorView.addView(view);

            //获取activity中 setContentView布局的根布局
            ViewGroup contentView = activity.findViewById(android.R.id.content);
            View activityView = contentView.getChildAt(0);
            //添加该属性，为了避免布局上移的bug
            activityView.setFitsSystemWindows(true);
        }

    }

    /**
     * 设置全屏
     * @param activity
     */
    public static void setActivityTranslucent(Activity activity) {
        //5.0 以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);

        }else if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT)
        //4.4 -- 5.0
        {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 状态栏高度
     *
     * 单位：px
     */
    public static int getStatusBarHeight(Activity activity) {
        Resources resources = activity.getResources();
        int statusBarId = resources.getIdentifier("status_bar_height", "dimen", "android");

        return resources.getDimensionPixelSize(statusBarId);
    }
}
