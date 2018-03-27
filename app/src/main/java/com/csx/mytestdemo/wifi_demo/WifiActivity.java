package com.csx.mytestdemo.wifi_demo;

import android.Manifest;
import android.content.DialogInterface;
import android.net.wifi.ScanResult;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mlibrary.dialog.CommonDialog;
import com.csx.mytestdemo.R;
import com.csx.mytestdemo.wifi_demo.wifi_utils.MyWifiUtils;
import com.csx.mytestdemo.wifi_demo.wifi_utils.OnWifiStateListener;
import com.csx.mytestdemo.wifi_demo.wifi_utils.WifiInfoBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * @Created by cuishuxiang
 * @date 2018/3/27.
 * @description: android 代码 链接/修改 Wifi
 */
@RuntimePermissions
public class WifiActivity extends BaseActivity {
    private static final String TAG = "WifiActivity";

    @BindView(R.id.show_wifi_btn)
    Button mShowWifiBtn;

    private MyWifiUtils mWifiUtils;


    private List<WifiInfoBean> mWifiInfoList;

    CommonDialog mWifiDialog;
    View wifiView = null;
    private SwipeRefreshLayout swipe_refresh;
    private RecyclerView dialog_rv;//wifi列表的rv
    private Switch wifiSwitch;//wifi 开关
    private ProgressBar load_pb;//wifi loading 圆圈
    private WifiRvListAdapter wifiRvListAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_wifi;
    }

    @Override
    public void initView() {
        mWifiInfoList = new ArrayList<>();

        initWifiDialog();

        mWifiUtils = MyWifiUtils.getInstance(this);

        mWifiUtils.setOnWifiStateListener(new OnWifiStateListener() {
            @Override
            public void onWifiStateDisable() {
                Log.d(TAG, "onWifiStateDisable: ");
            }

            @Override
            public void onWifiStateDisabling() {
                Log.d(TAG, "onWifiStateDisabling: ");
            }

            @Override
            public void onWifiStateEnable() {
                Log.d(TAG, "onWifiStateEnable: ");
            }

            @Override
            public void onWifiStateEnabling() {
                Log.d(TAG, "onWifiStateEnabling: ");
            }

            @Override
            public void onWifiStateUnKnown() {
                Log.d(TAG, "onWifiStateUnKnown: ");
            }

            @Override
            public void onWifiConnecting() {
                Log.d(TAG, "onWifiConnecting: ");
            }

            @Override
            public void onWifiGettingIp() {
                Log.d(TAG, "onWifiGettingIp: ");
            }

            @Override
            public void onWifiPwdError() {
                Log.d(TAG, "onWifiPwdError: ");
            }

            @Override
            public void onWifiConnectedSuccess(String wifiName) {
                Log.d(TAG, "onWifiConnectedSuccess: ");
            }

            @Override
            public void onWifiStrengthLevelChange(int level) {
                Log.d(TAG, "onWifiStrengthLevelChange:");
            }

            @Override
            public void onWifiDisconnecting() {
                Log.d(TAG, "onWifiDisconnecting: ");
            }

            @Override
            public void onWifiDisconnected() {
                Log.d(TAG, "onWifiDisconnected: ");
            }

            @Override
            public void onWifiConnectFail(String wifiName) {
                Log.d(TAG, "onWifiConnectFail: ");
            }
        });

    }

    private void initWifiDialog() {

        if (wifiView == null) {
            wifiView = LayoutInflater.from(this).inflate(R.layout.dialog_wifi_connect, null);
            //wifi 列表
            dialog_rv = wifiView.findViewById(R.id.wif_rv);
            //下拉刷新
            swipe_refresh = wifiView.findViewById(R.id.swipe_refresh);
            //wifi 开关
            wifiSwitch = wifiView.findViewById(R.id.wifi_switch);
            //loading 转圈
            load_pb = wifiView.findViewById(R.id.load_pb);

            dialog_rv.setLayoutManager(new LinearLayoutManager(this));

            wifiRvListAdapter = new WifiRvListAdapter(this, mWifiInfoList);

            dialog_rv.setAdapter(wifiRvListAdapter);
        }

        wifiRvListAdapter.setOnRvItemClickListener(new WifiRvListAdapter.OnRvItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {

                //1,判断点击的是否是当前已经连接的wifi
                WifiInfoBean wifiInfoBean = mWifiInfoList.get(position);

                if (mWifiUtils.getConnectWifiInfo().getSSID().equals(("\"" + wifiInfoBean.getScanResult().SSID) + "\"")) {
                    Log.d(TAG, "onItemClickListener: 两个Wifi相同");
                    return;
                }

                final ScanResult result = mWifiInfoList.get(position).getScanResult();
                Log.d(TAG, position + " ： " + result.SSID);

                //获取wifi的加密方式
                final int security = wifiInfoBean.getSecurityType();
                //判断wifi是否保存，netId=-1 则未保存
                int netid = mWifiUtils.isWifiConfig(result.SSID);
                if (netid == -1) {
                    //2，wifi 未保存
                    configUnSaveWifi(security, result);
                } else {
                    //3，wifi保存，则直接连接
                    if (load_pb != null && load_pb.getVisibility() == View.INVISIBLE) {
                        //loading状态显示
                        load_pb.setVisibility(View.VISIBLE);
                    }
                    boolean isConnectSucceed = mWifiUtils.connectWifi(netid);
                    Log.d(TAG, "onItemClickListener: wifi连接成功？ " + isConnectSucceed);
                }
            }

            @Override
            public void onLongItemClickListener(View view, int position) {

            }
        });


        if (mWifiDialog == null)
            mWifiDialog = new CommonDialog.Builder(this)
                    .setContentView(wifiView)
                    .setWidthAndHeight(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    .create();
    }

    /**
     * 连接 / 配置 没有保存的wifi
     * @param security
     * @param result
     */
    private void configUnSaveWifi(final int security, final ScanResult result) {
        //有密码的情况，弹窗(0为无密码)
        if (security != 0) {
            final EditText pwdEt = new EditText(WifiActivity.this);
            //弹出输入密码对话框
            new AlertDialog.Builder(WifiActivity.this)
                    .setView(pwdEt)
                    .setTitle("请输入密码")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mWifiUtils.configWifi(result, pwdEt.getText().toString().trim(), security);
                            mWifiUtils.connectWifi(mWifiUtils.isWifiConfig(result.SSID));
                            if (load_pb != null && load_pb.getVisibility() == View.INVISIBLE) {
                                //loading状态显示
                                load_pb.setVisibility(View.VISIBLE);
                            }
                        }
                    })
                    .setNegativeButton("取消", null)
                    .create().show();
        } else {
            int saveWifi = mWifiUtils.configWifi(result, "", security);
            Log.d(TAG, "configUnSaveWifi: 保存wifi(-1则为保存失败) = " + saveWifi);
            boolean isConnectSucceed = mWifiUtils.connectWifi(mWifiUtils.isWifiConfig(result.SSID));
            Log.d(TAG, "configUnSaveWifi: 连接成功？" + isConnectSucceed);

            if (load_pb != null && load_pb.getVisibility() == View.INVISIBLE) {
                //loading状态显示
                load_pb.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.show_wifi_btn)
    public void onViewClicked() {
        WifiActivityPermissionsDispatcher.openWifiSetWithPermissionCheck(this);
    }


    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.CHANGE_WIFI_STATE})
    void openWifiSet() {


        mWifiUtils.startWiFiScan();
        mWifiInfoList.clear();
        mWifiInfoList.addAll(mWifiUtils.getWifiInfoBeanList());

        Log.d(TAG, "openWifiSet: mWifiInfoList.size() = " + mWifiInfoList.size());

        wifiRvListAdapter.notifyDataSetChanged();
        mWifiDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        WifiActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void onWifiShowRationale(final PermissionRequest request) {
    }

    @OnPermissionDenied({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void onWifiPermissionDenied() {
    }

    @OnNeverAskAgain({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void onWifiPermissionNeverAsk() {
    }
}
