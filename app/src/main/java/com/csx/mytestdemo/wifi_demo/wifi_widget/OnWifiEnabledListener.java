package com.csx.mytestdemo.wifi_demo.wifi_widget;

/**
 * @Created by cuishuxiang
 * @date 2018/3/27.
 * @description: WIFI打开关闭的回调接口
 */
public interface OnWifiEnabledListener {

    /**
     * WIFI开关的回调
     *
     * @param enabled true 可用 false 不可用
     */
    void onWifiEnabled(boolean enabled);
}
