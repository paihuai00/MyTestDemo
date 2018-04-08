package com.csx.mytestdemo.wifi_demo.wifi_widget;

/**
 * @Created by cuishuxiang
 * @date 2018/3/27.
 * @description: WIFI连接的回调接口
 */
public interface OnWifiConnectListener {

    /**
     * WIFI连接信息的回调
     *
     * @param log log
     */
    void onWiFiConnectLog(String log);

    /**
     * WIFI连接成功的回调
     *
     * @param SSID 热点名
     */
    void onWiFiConnectSuccess(String SSID);

    /**
     * WIFI连接失败的回调
     *
     * @param SSID 热点名
     */
    void onWiFiConnectFailure(String SSID);
}
