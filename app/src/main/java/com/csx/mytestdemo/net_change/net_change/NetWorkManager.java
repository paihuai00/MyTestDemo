package com.csx.mytestdemo.net_change.net_change;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;
import android.os.Build;

/**
 * date: 2019/4/12
 * create by cuishuxiang
 * description:
 */
public class NetWorkManager {
    //需要注册的广播
    public static final String ACTION_CONNECTIVITY_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE";

    private Application application;
    private NetStateReceiver receiver;
    private ConnectivityManager cmgr;
    private NetWorkCallBackImpl networkCallback;

    private NetWorkManager() {
        receiver = new NetStateReceiver();
    }

    public static NetWorkManager getInstance() {
        return SingletonHolder.instance;
    }

    public void init(Application application) {
        this.application = application;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(ACTION_CONNECTIVITY_CHANGE);
            application.registerReceiver(receiver, filter);
        } else {
            networkCallback = new NetWorkCallBackImpl();
            NetworkRequest request = new NetworkRequest.Builder().build();
            cmgr = (ConnectivityManager) application
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cmgr != null) {
                cmgr.registerNetworkCallback(request,networkCallback);
            }
        }
    }

    public Application getApplication() {
        return application;
    }

    public ConnectivityManager getConnectivityManager(){
        return cmgr;
    }

    public void registerObserver(Object object) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            receiver.registerObserver(object);
        } else {
            networkCallback.registerObserver(object);
        }
    }

    public void unRegisterObserver(Object object) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            receiver.unRegisterObserver(object);
        }else{
            networkCallback.unRegisterObserver(object);
        }

    }

    public void unRegisterAllObserver() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            receiver.unRegisterAllObserver();
        }else {
            networkCallback.unRegisterAllObserver();
        }
    }

    private static class SingletonHolder {
        private static NetWorkManager instance = new NetWorkManager();
    }
}
