package com.csx.mlibrary.app;

import android.content.Context;
import android.os.Environment;

/**
 * @Created by cuishuxiang
 * @date 2018/2/7.
 *
 * 得到路径相关；私有目录。Android data  包名下面
 * (无需root就可以查看,应用删除也会随之删除)
 */

public class PathConstant {


    /**
     * 得到 下载 存储目录
     *
     * 可以更改下面的 type值，来得到不同的目录；
     *             {@link android.os.Environment#DIRECTORY_MUSIC},
     *            {@link android.os.Environment#DIRECTORY_PODCASTS},
     *            {@link android.os.Environment#DIRECTORY_RINGTONES},
     *            {@link android.os.Environment#DIRECTORY_ALARMS},
     *            {@link android.os.Environment#DIRECTORY_NOTIFICATIONS},
     *            {@link android.os.Environment#DIRECTORY_PICTURES}, or
     *            {@link android.os.Environment#DIRECTORY_MOVIES}.
     */
    public static String getDownLoadFilePath(Context context) {

        return context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
    }

    /**
     * 得到音频文件存储目录
     */
    public static String getMusicFilePath(Context context) {

        return context.getExternalFilesDir(Environment.DIRECTORY_MUSIC).getAbsolutePath();
    }

    /**
     * 得到 缓存 目录
     */
    public static String getCacheFilePath(Context context) {
        return context.getCacheDir().getAbsolutePath();
    }



}
