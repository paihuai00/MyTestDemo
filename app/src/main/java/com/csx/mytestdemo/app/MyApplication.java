package com.csx.mytestdemo.app;

import android.app.Application;
import android.util.Log;

import com.csx.mlibrary.crash.CrashAppHelper;
import com.csx.mlibrary.utils.Utils;
import com.csx.mytestdemo.carsh_handle.CrashHandle;
import com.csx.mytestdemo.multiple_state.EmptyCallBack;
import com.csx.mytestdemo.multiple_state.ErrorCallBack;
import com.csx.mytestdemo.multiple_state.LoadingCallBack;
import com.kingja.loadsir.core.LoadSir;

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


        //多状态布局配置
        initMultipleState();

    }

    private void initMultipleState() {
        LoadSir.beginBuilder()
                .addCallback(new LoadingCallBack())
                .addCallback(new EmptyCallBack())
                .addCallback(new ErrorCallBack())
                .setDefaultCallback(LoadingCallBack.class)
                .commit();
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
