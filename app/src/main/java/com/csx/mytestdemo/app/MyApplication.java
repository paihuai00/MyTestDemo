package com.csx.mytestdemo.app;

import android.app.Application;

import com.csx.mlibrary.utils.Utils;

/**
 * @Created by cuishuxiang
 * @date 2018/2/5.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
