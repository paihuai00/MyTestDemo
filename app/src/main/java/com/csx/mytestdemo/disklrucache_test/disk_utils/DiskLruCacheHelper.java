package com.csx.mytestdemo.disklrucache_test.disk_utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;

/**
 * Date: 2019/6/25
 * create by cuishuxiang
 * description: DiskLruCache  帮助类
 */
public class DiskLruCacheHelper {
    private static final String TAG = DiskLruCacheHelper.class.getName();
    private String CACHE_DIR = "disk_lru_cache";//磁盘缓存路径

    private DiskLruCache mDiskLruCache;
    private int appVersion = 1;// APP版本号，当版本号改变时，缓存数据会被清除
    private int valueCount = 1;// 同一个key可以对应多少文件
    private static long CacheSize = 1024 * 1024 * 20;//缓存大小20M.

    private DiskLruCache.Editor mEditor;

    public DiskLruCacheHelper(Context context) {
        try {
            File diskLruCacheFile = getDiskCacheDir(context);

            if (!diskLruCacheFile.exists()) {
                diskLruCacheFile.mkdirs();
            }

            mDiskLruCache = DiskLruCache.open(diskLruCacheFile, appVersion, valueCount, CacheSize);

        } catch (Exception e) {
            Log.e(TAG, "DiskLruCacheHelper(): ", e);
        }
    }

    public DiskLruCache getDiskLruCache() {
        if (mDiskLruCache == null)
            throw new IllegalArgumentException("mDiskLruCache==null ,Please call DiskLruCacheHelper() first! ");

        return mDiskLruCache;
    }

    public DiskLruCache.Editor getEdit(String key) {
        if (mDiskLruCache == null)
            throw new IllegalArgumentException("mDiskLruCache==null ,Please call DiskLruCacheHelper() first! ");

        try {
            mEditor = mDiskLruCache.edit(key);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return mEditor;
    }


    private File getDiskCacheDir(Context context) {
        //判断外部存储是否可用
        boolean externalStorageAvailable = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        final String cachePath;
        if (externalStorageAvailable) {
            //路径为：/storage/emulated/0/Android/data/自己的包名/cache
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            //路径为： /data/user/0/自己的包名/cache
            cachePath = context.getCacheDir().getPath();
        }

        return new File(cachePath + File.separator + CACHE_DIR);
    }

    public void cleanBuffer() {
        try {
            if (mDiskLruCache != null) {
                mDiskLruCache.delete();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
