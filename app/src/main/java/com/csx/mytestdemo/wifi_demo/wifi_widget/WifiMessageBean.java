package com.csx.mytestdemo.wifi_demo.wifi_widget;

import android.net.wifi.ScanResult;

/**
 * Created by cuishuxiang on 2017/12/26.
 *
 * wifi 信息 Bean
 */

public class WifiMessageBean {

    public SecurityModeEnum security_type;//加密类型
    public ScanResult scanResult;
    public int level;//信号强度

    public SecurityModeEnum getSecurity_type() {
        return security_type;
    }

    public void setSecurity_type(SecurityModeEnum security_type) {
        this.security_type = security_type;
    }

    public ScanResult getScanResult() {
        return scanResult;
    }

    public void setScanResult(ScanResult scanResult) {
        this.scanResult = scanResult;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    @Override
    public String toString() {
        return "WifiMessageBean{" +
                "security_type=" + security_type +
                ", scanResult=" + scanResult +
                ", level=" + level +
                '}';
    }
}
