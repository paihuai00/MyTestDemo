package com.csx.mlibrary.crash;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;

import static android.os.Environment.MEDIA_MOUNTED;

/**
 * @Created by cuishuxiang
 * @date 2018/4/13.
 * @description: log文件生成辅助类
 *
 * 当前生成路径：/Android/data/包名/files/log
 */

public class CrashAppHelper extends CrashAppLog {

    public static CrashAppHelper mCrashAppHelper = null;


    private CrashAppHelper() {
    }

    public static CrashAppHelper getInstance() {

        if (mCrashAppHelper == null)
            mCrashAppHelper = new CrashAppHelper();

        return mCrashAppHelper;

    }

    /**
     * 可以在这个方法中修改存储位置，以及限制文件个数
     * @param crashAppLog
     */
    @Override
    public void initParams(CrashAppLog crashAppLog) {

        if (crashAppLog != null) {
            //修改log存储 位置
            String logDir = getExternalFileDirPath(crashAppLog.getContext(), "log");
            crashAppLog.setCAHCE_CRASH_LOG(logDir);
            //最大log数量为 5
            crashAppLog.setLIMIT_LOG_COUNT(5);
        }
    }

    @Override
    public void sendCrashLogToServer(File folder, File file) {

        Log.e("*********", "文件夹:" + folder.getAbsolutePath() + " - " + file.getAbsolutePath() + "");
    }


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
