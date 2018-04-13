package com.csx.mytestdemo.service_test;

import android.app.AlertDialog;
import android.app.Notification;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Created by cuishuxiang
 * @date 2018/4/10.
 * @description: 四大组件 service 使用
 * 1，Service 需要在Manifest中声明
 */

public class ServiceActivity extends BaseActivity {
    private static final String TAG = "MyServiceActivity";

    @BindView(R.id.start_service_btn)
    Button mStartServiceBtn;
    @BindView(R.id.stop_service_btn)
    Button mStopServiceBtn;
    @BindView(R.id.bind_service_btn)
    Button mBindServiceBtn;
    @BindView(R.id.unbind_service_btn)
    Button mUnbindServiceBtn;

    MyService.MyBind mMyBind;

    private ServiceConnection mServiceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: ");
            mMyBind = (MyService.MyBind) service;
            mMyBind.doSomeThing();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: ");
        }
    };


    @Override
    public int getLayoutId() {
        return R.layout.activity_service;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.start_service_btn, R.id.stop_service_btn, R.id.bind_service_btn, R.id.unbind_service_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.start_service_btn:
                Intent startServiceIntent = new Intent(this, MyService.class);
                startService(startServiceIntent);
                break;
            case R.id.stop_service_btn:
                Intent stopServiceIntent = new Intent(this, MyService.class);
                stopService(stopServiceIntent);
                break;
            case R.id.bind_service_btn:
                Intent bindServiceIntent = new Intent(this, MyService.class);
                bindService(bindServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
                break;
            case R.id.unbind_service_btn:
                Intent unbindServiceIntent = new Intent(this, MyService.class);
                unbindService(mServiceConnection);
                break;
        }
    }
}
