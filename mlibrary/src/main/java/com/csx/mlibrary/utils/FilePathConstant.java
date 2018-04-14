package com.csx.mlibrary.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

import static android.os.Environment.MEDIA_MOUNTED;

/**
 * @Created by cuishuxiang
 * @date 2018/4/13.
 * @description: 存储常量
 */

public class FilePathConstant {
    /**
     * 获取 下面的外部存储的路径
     * Android->data-> 应用包名
     * 例如：dir = log的时候   路径为：/Android/data/包名/files/log
     * @param context
     * @param dir
     * @return
     */
    private String getExternalFileDirPath(Context context, String dir) {
        String directoryPath = "";
        if (MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {//判断外部存储是否可用
            directoryPath = context.getExternalFilesDir(dir).getAbsolutePath();
        } else {//没外部存储就使用内部存储
            directoryPath = context.getFilesDir() + File.separator + dir;
        }
        File file = new File(directoryPath);
        if (!file.exists()) {//判断文件目录是否存在
            file.mkdirs();
        }
        return directoryPath;
    }
}
