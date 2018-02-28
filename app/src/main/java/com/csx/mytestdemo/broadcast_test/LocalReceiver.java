package com.csx.mytestdemo.broadcast_test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.csx.mlibrary.utils.ToastUtils;

/**
 * @Created by cuishuxiang
 * @date 2018/2/27.
 *
 * 接收本地广播
 */

public class LocalReceiver extends BroadcastReceiver {
    private static final String TAG = "LocalReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        String location = intent.getStringExtra("location");
        Log.d(TAG, "onReceive: " + location);
        ToastUtils.showShortToast(location);

    }
}
