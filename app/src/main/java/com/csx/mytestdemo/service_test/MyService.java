package com.csx.mytestdemo.service_test;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.csx.mytestdemo.MainActivity;
import com.csx.mytestdemo.R;

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

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        Notification notification = new Notification.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.ic_qq)
                .setContentText("这是通知,点击跳转MainActivity.class")
                .setContentIntent(pendingIntent)
                .build();

        //调用startForeground()方法就可以让MyService变成一个前台Service
        startForeground(1, notification);
        Log.d(TAG, "onCreate() executed");
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
            //这里只是测试方法
            Log.d(TAG, "MyBind ---> doSomeThing: ----");

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                }
            }, 3000);
        }
    }
}

