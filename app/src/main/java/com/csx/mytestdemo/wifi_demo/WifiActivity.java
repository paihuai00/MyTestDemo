package com.csx.mytestdemo.wifi_demo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mlibrary.dialog.CommonDialog;
import com.csx.mytestdemo.R;
import com.csx.mytestdemo.wifi_demo.wifi_widget.OnWifiConnectListener;
import com.csx.mytestdemo.wifi_demo.wifi_widget.OnWifiEnabledListener;
import com.csx.mytestdemo.wifi_demo.wifi_widget.OnWifiScanResultsListener;
import com.csx.mytestdemo.wifi_demo.wifi_widget.SecurityModeEnum;
import com.csx.mytestdemo.wifi_demo.wifi_widget.WiFiManager;
import com.csx.mytestdemo.wifi_demo.wifi_widget.WifiMessageBean;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;

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
 * @description: android 代码 链接/修改 Wifi (需要在Manifest中注册接收wifi状态变化的广播)
 */
@RuntimePermissions
public class WifiActivity extends BaseActivity implements OnWifiScanResultsListener, OnWifiConnectListener,
        OnWifiEnabledListener, WifiRvListAdapter.OnRvItemClickListener {
    private static final String TAG = "WifiActivity";

    @BindView(R.id.show_wifi_btn)
    Button mShowWifiBtn;

    //wifi弹框相关
    private DialogPlus wifiDialogPlus;
    private View wifiDialogView;
    private TextView mWifiEnableTv;//wifi关闭显示界面
    private SwipeRefreshLayout swipe_refresh;//wifi弹框，下拉刷新
    private RecyclerView dialog_rv;//wifi列表的rv
    private Switch wifiSwitch;//wifi 开关
    private ProgressBar load_pb;//wifi loading 圆圈
    private List<WifiMessageBean> mWifiMessageBeanList;
    private WifiRvListAdapter wifiRvListAdapter;

    private WiFiManager mWiFiManager;

    //点击wifi列表 item，输入密码的弹框
    private CommonDialog mWifiConncetDialog;
    private View wifiConnectView;
    private TextView mSsidTv;
    private EditText mWifiPwdEt;
    private Button mWifiConnectEnsureBtn;


    @Override
    public int getLayoutId() {
        return R.layout.activity_wifi;
    }

    @Override
    public void initView() {

        initWifiDialog();


    }
    @Override
    protected void onResume() {
        super.onResume();
        // 添加监听
        if (mWiFiManager != null) {
            mWiFiManager.setOnWifiEnabledListener(this);
            mWiFiManager.setOnWifiScanResultsListener(this);
            mWiFiManager.setOnWifiConnectListener(this);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        // 移除监听
        if (mWiFiManager != null) {
            mWiFiManager.removeOnWifiEnabledListener();
            mWiFiManager.removeOnWifiScanResultsListener();
            mWiFiManager.removeOnWifiConnectListener();
        }

    }

    private void initWifiDialog() {
        if (mWifiMessageBeanList == null)
            mWifiMessageBeanList = new ArrayList<>();

        if (wifiDialogView == null) {
            wifiDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_wifi_connect, null);
            mWifiEnableTv = wifiDialogView.findViewById(R.id.wifi_enable_tv);
            //wifi 列表
            dialog_rv = wifiDialogView.findViewById(R.id.wif_rv);
            //下拉刷新
            swipe_refresh = wifiDialogView.findViewById(R.id.swipe_refresh);
            //wifi 开关
            wifiSwitch = wifiDialogView.findViewById(R.id.wifi_switch);
            //loading 转圈
            load_pb = wifiDialogView.findViewById(R.id.load_pb);

            wifiRvListAdapter = new WifiRvListAdapter(this, mWifiMessageBeanList);

            dialog_rv.setLayoutManager(new LinearLayoutManager(this));

            dialog_rv.setAdapter(wifiRvListAdapter);

            dialog_rv.addItemDecoration(new MyRvDecoration(this, LinearLayoutManager.HORIZONTAL));
        }

        // WIFI管理器
        if (mWiFiManager == null)
            mWiFiManager = WiFiManager.getInstance(getApplicationContext());

        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe_refresh.setRefreshing(false);

                mWifiMessageBeanList.clear();

                mWifiMessageBeanList.addAll(mWiFiManager.getWifiInfoLists());

                wifiRvListAdapter.notifyDataSetChanged();
                Log.d(TAG, "Wifi 下拉刷---------新");
            }
        });

        //初始时：设置wifi开关的状态
        wifiSwitch.setChecked(mWiFiManager.isWifiEnabled());

        if (wifiDialogPlus == null) {
            wifiDialogPlus = DialogPlus.newDialog(this)
                    .setContentHolder(new ViewHolder(wifiDialogView))
                    .setGravity(Gravity.CENTER)
                    .setContentWidth(800)
                    .setContentBackgroundResource(R.drawable.bg_wifi_dialog)
                    .setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(DialogPlus dialog, View view) {
                            switch (view.getId()) {
                                case R.id.close_dialog://取消按钮
                                    if (wifiDialogPlus != null && wifiDialogPlus.isShowing()) {
                                        wifiDialogPlus.dismiss();
                                    }
                                    break;
                            }
                        }
                    })
                    .create();
        }

        //wifi 列表，点击/长按 事件
        wifiRvListAdapter.setOnRvItemClickListener(this);

        wifiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheck) {
                Toast.makeText(getBaseContext(), "Wifi  状态：" + isCheck, Toast.LENGTH_SHORT).show();
                if (isCheck) {
                    mWiFiManager.openWiFi();
                } else {
                    mWiFiManager.closeWiFi();
                }
                wifiRvListAdapter.notifyDataSetChanged();
            }
        });

        wifiRvListAdapter.setConnectWifiInfo(mWiFiManager.getConnectionInfo());

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

        mWifiMessageBeanList.clear();
        mWifiMessageBeanList.addAll(mWiFiManager.getWifiInfoLists());

        //刷新列表
        wifiRvListAdapter.notifyDataSetChanged();

        wifiDialogPlus.show();//显示弹框
    }

    /**
     * 点击item，输入wifi密码的弹框
     *
     * @param ssid             wifi名称
     * @param securityModeEnum wifi加密方式
     */
    private void createInputWifiPwdDialog(final String ssid, final SecurityModeEnum securityModeEnum, final ScanResult scanResult) {

        if (mWifiConncetDialog == null || wifiConnectView == null) {
            wifiConnectView = LayoutInflater.from(this).inflate(R.layout.dialog_connect_wifi, null);
            mSsidTv = wifiConnectView.findViewById(R.id.ssid_tv);
            mWifiPwdEt = wifiConnectView.findViewById(R.id.wifi_pwd_et);
            mWifiConnectEnsureBtn = wifiConnectView.findViewById(R.id.btn_connect);
            mWifiConncetDialog = new CommonDialog.Builder(this)
                    .setContentView(wifiConnectView)
                    .setWidthAndHeight((int) getResources().getDimension(R.dimen.y700), ViewGroup.LayoutParams.WRAP_CONTENT)
                    .setOnClickListener(R.id.btn_cancel, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mWifiConncetDialog.isShowing())
                                mWifiConncetDialog.dismiss();
                        }
                    })
                    .create();
        }
        mSsidTv.setText(ssid);
        mWifiConnectEnsureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = mWifiPwdEt.getText().toString().trim();
                Log.d(TAG, "当前wifi为：" + ssid + " 输入的密码为：" + password);
                switch (securityModeEnum) {
                    case WPA:
                    case WPA2:
                        mWiFiManager.connectWPA2Network(scanResult, password);
                        break;
                    case WEP:
                        mWiFiManager.connectWEPNetwork(scanResult, password);
                        break;
                    case OPEN: // 开放网络
                        mWiFiManager.connectOpenNetwork(scanResult.SSID);
                        break;
                }

                if (mWifiConncetDialog.isShowing())
                    mWifiConncetDialog.dismiss();
            }
        });
        mWifiConncetDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        WifiActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @SuppressLint("NoCorrespondingNeedsPermission")
    @OnShowRationale({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void showRationalForWifi(final PermissionRequest request) {
        new android.support.v7.app.AlertDialog.Builder(this)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage("需要位置权限，应用将要申请该权限")
                .show();
    }

    @OnPermissionDenied({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void onWifiPermissionDenied() {
        Toast.makeText(this, getString(R.string.permission_deny), Toast.LENGTH_LONG).show();

    }

    @OnNeverAskAgain({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void onWifiPermissionNeverAsk() {
        new android.support.v7.app.AlertDialog.Builder(this)
                .setPositiveButton(getString(R.string.ensure), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage(getString(R.string.permission_ask))
                .show();
    }

    /**
     * WIFI连接的Log得回调
     *
     * @param log log
     */
    @Override
    public void onWiFiConnectLog(String log) {
        Log.d(TAG, "onWiFiConnectLog: " + log);
    }

    @Override
    public void onWiFiConnectSuccess(String SSID) {
        Log.d(TAG, "onWiFiConnectSuccess: " + SSID);
        Toast.makeText(getApplicationContext(), SSID + "  连接成功", Toast.LENGTH_SHORT).show();
        wifiRvListAdapter.setConnectWifiInfo(mWiFiManager.getConnectionInfo());
    }

    @Override
    public void onWiFiConnectFailure(String SSID) {
        Log.d(TAG, "onWiFiConnectFailure: " + SSID);
    }
    /**
     * WIFI开关状态的回调
     *
     * @param enabled true 可用 false 不可用
     */
    @Override
    public void onWifiEnabled(boolean enabled) {
        Log.d(TAG, "onWifiEnabled: " + enabled);
        wifiSwitch.setChecked(enabled);
        mWifiEnableTv.setVisibility(enabled == true ? View.GONE : View.VISIBLE);
    }

    /**
     * WIFI列表刷新后的回调
     *
     * @param scanResults 扫描结果
     */
    @Override
    public void onScanResults(List<ScanResult> scanResults) {
        Log.d(TAG, "onScanResults = scanResults.size() = " + scanResults.size());
        mWifiMessageBeanList.clear();

        for (ScanResult scanResult : scanResults) {
            WifiMessageBean wifiMessageBean = new WifiMessageBean();
            wifiMessageBean.setSecurity_type(mWiFiManager.getSecurityMode(scanResult));
            wifiMessageBean.setScanResult(scanResult);
            wifiMessageBean.setLevel(WifiManager.calculateSignalLevel(scanResult.level, 5));
            mWifiMessageBeanList.add(wifiMessageBean);
        }

        wifiRvListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClickListener(View view, int position) {
        final ScanResult scanResult = mWifiMessageBeanList.get(position).getScanResult();

        if (mWiFiManager.isWifiConfig(scanResult) != -1) {
            return;
        }

        createInputWifiPwdDialog(scanResult.SSID, mWifiMessageBeanList.get(position).getSecurity_type(), scanResult);

    }

    @Override
    public void onLongItemClickListener(View view, int position) {
        ScanResult scanResult = (ScanResult) mWifiMessageBeanList.get(position).getScanResult();
        final String ssid = scanResult.SSID;
        new android.support.v7.app.AlertDialog.Builder(this)
                .setTitle(ssid)
                .setItems(new String[]{"断开连接", "删除网络配置"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0: // 断开连接
                                WifiInfo connectionInfo = mWiFiManager.getConnectionInfo();
                                Log.d(TAG, "onClick: connectionInfo :" + connectionInfo.getSSID());
                                if (mWiFiManager.addDoubleQuotation(ssid).equals(connectionInfo.getSSID())) {
                                    mWiFiManager.disconnectWifi(connectionInfo.getNetworkId());
                                    Log.d(TAG, "当前连接的：" + mWiFiManager.getConnectionInfo());
                                } else {
                                    Toast.makeText(getApplicationContext(), "当前没有连接 [ " + ssid + " ]", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case 1: // 删除网络配置
                                WifiConfiguration wifiConfiguration = mWiFiManager.getConfigFromConfiguredNetworksBySsid(ssid);
                                if (null != wifiConfiguration) {
                                    boolean isDelete = mWiFiManager.deleteConfig(wifiConfiguration.networkId);
                                    Toast.makeText(getApplicationContext(), isDelete ? "删除成功！" : "其他应用配置的网络没有ROOT权限不能删除！", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "没有保存该网络！", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            default:
                                break;
                        }
                    }
                })
                .show();
    }
}
