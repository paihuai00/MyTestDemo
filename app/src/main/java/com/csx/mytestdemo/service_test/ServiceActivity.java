package com.csx.mytestdemo.service_test;

import android.app.Activity;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mlibrary.utils.permission_utils.AskAgainCallback;
import com.csx.mlibrary.utils.permission_utils.FullCallback;
import com.csx.mlibrary.utils.permission_utils.PermissionEnum;
import com.csx.mlibrary.utils.permission_utils.PermissionManager;
import com.csx.mytestdemo.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;
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
    @BindView(R.id.permission_btn)
    Button mPermissionBtn;
    @BindView(R.id.service_conn)
    Button mServiceConn;
    @BindView(R.id.socket_stop)
    Button mSocketStop;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
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

    private SocketService.MySocketBinder mSocketBinder;
    private Handler inHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    private Handler outHanler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    private ServiceConnection mSocketConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

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

    @OnClick({R.id.start_service_btn, R.id.stop_service_btn,
            R.id.bind_service_btn, R.id.unbind_service_btn,
            R.id.permission_btn, R.id.service_conn,R.id.socket_stop})
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

            case R.id.permission_btn:
                PermissionManager.Builder()
                        .key(0x01)
                        .permission(PermissionEnum.CAMERA, PermissionEnum.WRITE_EXTERNAL_STORAGE)
                        .askAgain(true)
                        .callback(new FullCallback() {
                            @Override
                            public void result(ArrayList<PermissionEnum> permissionsGranted, ArrayList<PermissionEnum> permissionsDenied, ArrayList<PermissionEnum> permissionsDeniedForever, ArrayList<PermissionEnum> permissionsAsked) {
                                Log.d(TAG, "result: ");
                            }
                        })
                        .askAgainCallback(new AskAgainCallback() {
                            @Override
                            public void showRequestPermission(UserResponse response) {
                                // response 中的boolean值，可以用于判断是否再次弹框
                                showDialog(response);
                                Log.d(TAG, "showRequestPermission: ");
                            }
                        })
                        .ask(this);

                break;

            case R.id.service_conn:
                Intent connectService = new Intent(this, SocketService.class);
                bindService(connectService, mSocketConnection, BIND_AUTO_CREATE);
                startService(connectService);
                break;
            case R.id.socket_stop:
                Intent stopService = new Intent(this, SocketService.class);
                unbindService(mSocketConnection);
                stopService(stopService);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionManager.handleResult(this, requestCode, permissions, grantResults);
    }

    private void showDialog(final AskAgainCallback.UserResponse response) {
        new AlertDialog.Builder(ServiceActivity.this)
                .setTitle("Permission needed")
                .setMessage("本应用需要使用该权限，是否授予?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        response.result(true);
                    }
                })
                .setNegativeButton("NOT NOW", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        response.result(false);
                    }
                })
                .setCancelable(false)
                .show();
    }


}
