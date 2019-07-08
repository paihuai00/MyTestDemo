package com.csx.mytestdemo.net_change;

import android.util.Log;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.R;
import com.csx.mytestdemo.net_change.net_change.NetType;
import com.csx.mytestdemo.net_change.net_change.NetWork;
import com.csx.mytestdemo.net_change.net_change.NetWorkManager;
import com.csx.mytestdemo.service_test.Person2;

/**
 * date: 2019/4/12
 * create by cuishuxiang
 * description:
 */
public class NetChangeActivity extends BaseActivity {
    private static final String TAG = "NetChangeActivity";


    Person2 person2;

    @Override
    public int getLayoutId() {
        return R.layout.activity_net_change;
    }

    @Override
    public void initView() {

        NetWorkManager.getInstance().init(this.getApplication());
        NetWorkManager.getInstance().registerObserver(this);

        person2 = getIntent().getParcelableExtra("person");

        Log.d(TAG, "initView: person2 : " + person2);

    }

    @Override
    public void initData() {

    }

    @NetWork(netType = NetType.AUTO)
    public void onNetChanged(NetType netType) {
        switch (netType) {
            case WIFI:
                Log.e(TAG,"AUTO监控：WIFI CONNECT");
                break;
            case MOBILE:
                Log.e(TAG,"AUTO监控：MOBILE CONNECT");
                break;
            case AUTO:
                Log.e(TAG,"AUTO监控：AUTO CONNECT");
                break;
            case NONE:
                Log.e(TAG,"AUTO监控：NONE CONNECT");
                break;
            default:
                break;
        }
    }

    @NetWork(netType = NetType.WIFI)
    public void onWifiChanged(NetType netType){
        switch (netType){
            case WIFI:
                Log.e(TAG,"wifi监控：WIFI CONNECT");
                break;
            case NONE:
                Log.e(TAG,"wifi监控：NONE CONNECT");
                break;
        }
    }

    @NetWork(netType = NetType.MOBILE)
    public void onMobileChanged(NetType netType){
        switch (netType){
            case MOBILE:
                Log.e(TAG,"Mobile监控：MOBILE CONNECT");
                break;
            case NONE:
                Log.e(TAG,"Mobile监控：NONE CONNECT");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetWorkManager.getInstance().unRegisterObserver(this);
    }
}
