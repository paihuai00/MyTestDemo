package com.csx.mytestdemo.app;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.csx.mytestdemo.BuildConfig;
import com.github.moduth.blockcanary.BlockCanaryContext;
import com.github.moduth.blockcanary.internal.BlockInfo;

import java.util.List;

/**
 * Date: 2019/7/9
 * create by cuishuxiang
 * description:监控卡顿
 *
 * 实现各种上下文，包括应用标示符，用户 uid，网络类型，卡慢判断阙值，Log 保存位置等
 */
public class BlockCanaryUI extends BlockCanaryContext {
    private static final String TAG = "BlockCanaryUI";

    /**
     * 限定符
     * @return
     */
    @Override
    public String provideQualifier() {
        String qualifier = "";
        try {
            PackageInfo info = MyApplication.getContext().getPackageManager().getPackageInfo(MyApplication.getContext().getPackageName(), 0);
            qualifier += info.versionCode + "_" + info.versionName + "_UI";
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "provideQualifier exception", e);
        }
        return qualifier;
    }

    /**
     * Implement in your project.
     *
     * @return user id ,可以实现自己的 id
     */
    @Override
    public String provideUid() {
        return "87224330";
    }
    /**
     * Network type
     *
     * @return {@link String} like 2G, 3G, 4G, wifi, etc.
     */
//    @Override
//    public String provideNetworkType() {
//        return "4G";
//    }

    /**
     * Config monitor duration, after this time BlockCanary will stop, use
     * with {@code BlockCanary}'s isMonitorDurationEnd
     *
     * 监控时长
     *
     * @return monitor last duration (in hour)
     */
    @Override
    public int provideMonitorDuration() {
        return 9999;
    }

    /**
     * 阈值
     * @return
     */
    @Override
    public int provideBlockThreshold() {
        return 500;
    }

    @Override
    public boolean displayNotification() {
        return BuildConfig.DEBUG;
    }

    /**
     * Thread stack dump interval, use when block happens, BlockCanary will dump on main thread
     * stack according to current sample cycle.
     * <p>
     * Because the implementation mechanism of Looper, real dump interval would be longer than
     * the period specified here (especially when cpu is busier).
     * </p>
     *
     * @return dump interval (in millis)
     */
    public int provideDumpInterval() {
        return provideBlockThreshold();
    }

    /**
     * log 文件路径
     * @return
     */
    @Override
    public String providePath() {
        return super.providePath();
    }

    @Override
    public List<String> concernPackages() {
        List<String> list = super.provideWhiteList();
        list.add("com.example");
        return list;
    }

    @Override
    public List<String> provideWhiteList() {
        List<String> list = super.provideWhiteList();
        list.add("com.whitelist");
        return list;
    }

    @Override
    public boolean stopWhenDebugging() {
        return false;
    }

    /**
     * Block interceptor, developer may provide their own actions.
     */
    public void onBlock(Context context, BlockInfo blockInfo) {

    }

    //是否删除白名单下的files
    @Override
    public boolean deleteFilesInWhiteList() {
        return super.deleteFilesInWhiteList();
    }
}
