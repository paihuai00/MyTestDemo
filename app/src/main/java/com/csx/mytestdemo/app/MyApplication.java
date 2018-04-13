package com.csx.mytestdemo.app;

import android.app.Application;
import android.util.Log;

import com.csx.mlibrary.crash.CrashAppHelper;
import com.csx.mlibrary.utils.Utils;

import cafe.adriel.androidaudioconverter.AndroidAudioConverter;
import cafe.adriel.androidaudioconverter.callback.ILoadCallback;

/**
 * @Created by cuishuxiang
 * @date 2018/2/5.
 */

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);


        initAudio();

        //初始化崩溃日志打印
        CrashAppHelper.getInstance().init(this);

    }

    /**
     * 为了解决android录音无法转换成mp3
     */
    private void initAudio() {
        //初始化，
        AndroidAudioConverter.load(this, new ILoadCallback() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "AndroidAudioConverter -> onSuccess: ");
                // Great!
            }
            @Override
            public void onFailure(Exception error) {
                // FFmpeg is not supported by device
                Log.d(TAG, "AndroidAudioConverter -> error: ");
            }
        });
        
    }
}
