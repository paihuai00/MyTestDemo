package com.csx.mytestdemo.service_test;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * @Created by cuishuxiang
 * @date 2018/4/10.
 * @description:
 */

public class MyService extends Service {
    private static final String TAG = "MyService";

    MyBind mMyBind;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: 只会在Service创建的时候，调用");

        mMyBind = new MyBind();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMyBind;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }


    class MyBind extends Binder {

        public void doSomeThing() {
            Log.d(TAG, "MyBind ---> doSomeThing: ----");
        }
    }
}

