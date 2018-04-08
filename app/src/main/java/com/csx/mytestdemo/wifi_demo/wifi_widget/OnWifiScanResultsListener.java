package com.csx.mytestdemo.wifi_demo.wifi_widget;

import android.net.wifi.ScanResult;

import java.util.List;

/**
 * @Created by cuishuxiang
 * @date 2018/3/27.
 * @description: WIFI扫描结果的回调接口
 */
public interface OnWifiScanResultsListener {

    /**
     * 扫描结果的回调
     *
     * @param scanResults 扫描结果
     */
    void onScanResults(List<ScanResult> scanResults);
}
