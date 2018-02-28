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
 * 广播接收器
 */

public class StandardReceiver extends BroadcastReceiver {
    private static final String TAG = "StandardReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        String info = intent.getStringExtra("info");
        ToastUtils.showShortToast(info);
        Log.d(TAG, "onReceive: " + info);
    }
}
