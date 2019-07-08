package com.csx.mytestdemo.app;

import android.app.Application;
import android.content.pm.PackageManager;
import android.util.Log;

import com.alipay.euler.andfix.patch.PatchManager;
import com.csx.mlibrary.crash.CrashAppHelper;
import com.csx.mlibrary.http.HttpUtils;
import com.csx.mlibrary.http.OkHttpEngine;
import com.csx.mlibrary.skin.SkinManager;
import com.csx.mlibrary.utils.Utils;
import com.csx.mytestdemo.multiple_state.EmptyCallBack;
import com.csx.mytestdemo.multiple_state.ErrorCallBack;
import com.csx.mytestdemo.multiple_state.LoadingCallBack;
import com.kingja.loadsir.core.LoadSir;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import cafe.adriel.androidaudioconverter.AndroidAudioConverter;
import cafe.adriel.androidaudioconverter.callback.ILoadCallback;

/**
 * @Created by cuishuxiang
 * @date 2018/2/5.
 */

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";

    public static PatchManager mPathManager;
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);


        initAudio();

        //初始化崩溃日志打印
        CrashAppHelper.getInstance().init(this);


        //多状态布局配置
        initMultipleState();

        //阿里热修复
        mPathManager = new PatchManager(this);
        mPathManager.init("1");//当前应用版本

        //加载之前的patch包
        mPathManager.loadPatch();


        //初始化 网络引擎
        HttpUtils.init(OkHttpEngine.getInstance(getApplicationContext()));

        //初始化换肤
        SkinManager.getInstance().init(this);

        //zxing
        ZXingLibrary.initDisplayOpinion(this);

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
