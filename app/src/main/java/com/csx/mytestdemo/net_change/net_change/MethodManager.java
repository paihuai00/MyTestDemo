package com.csx.mytestdemo.net_change.net_change;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;
import android.os.Build;

import java.lang.reflect.Method;

/**
 * date: 2019/4/12
 * create by cuishuxiang
 * description:
 */
public class MethodManager {

    //被注解方法的参数类型 NetType netType
    private Class<?> type;

    //需要监听的网络类型
    private NetType netType;

    //需要执行的方法
    private Method method;

    public MethodManager(Class<?> type, NetType netType, Method method) {
        this.type = type;
        this.netType = netType;
        this.method = method;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public NetType getNetType() {
        return netType;
    }

    public void setNetType(NetType netType) {
        this.netType = netType;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}