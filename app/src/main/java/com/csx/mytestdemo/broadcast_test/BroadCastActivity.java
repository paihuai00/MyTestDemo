package com.csx.mytestdemo.broadcast_test;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Created by cuishuxiang
 * @date 2018/2/27.
 * <p>
 * 8.0+ 静态注册广播，会接收不到 ； 建议使用动态注册，记得在onDestroy中取消
 */

public class BroadCastActivity extends BaseActivity {
    private static final String TAG = "BroadCastActivity";
    @BindView(R.id.send_broadcast)
    Button mSendBroadcast;
    @BindView(R.id.send_local_broadcast)
    Button mSendLocalBroadcast;

    private IntentFilter mIntentFilter;
    private StandardReceiver mStandardReceiver;


    //------------本地广播-------------
    private LocalBroadcastManager mLocalBroadcastManager;
    private LocalReceiver mLocalReceiver;

    @Override
    public int getLayoutId() {
        return R.layout.activity_broadcast;
    }

    @Override
    public void initView() {
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(TAG);

        mStandardReceiver = new StandardReceiver();

        registerReceiver(mStandardReceiver, mIntentFilter);
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.send_broadcast, R.id.send_local_broadcast})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.send_broadcast:
                Intent standardCastIntent = new Intent();
                standardCastIntent.setAction(TAG);//此处添加的action必须一致
                standardCastIntent.putExtra("info", "我是： " + TAG);
                //发送标准广播
                sendBroadcast(standardCastIntent);
                break;
            case R.id.send_local_broadcast:
                sentLocalBroadCast();
                break;
        }
    }


    private void sentLocalBroadCast() {
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);

        mLocalReceiver = new LocalReceiver();

        //此处的action，需于intent的一致
        IntentFilter localIntentFilter = new IntentFilter("local");

        //注册广播
        mLocalBroadcastManager.registerReceiver(mLocalReceiver, localIntentFilter);


        //发送广播
        Intent localIntent = new Intent("local");
        localIntent.putExtra("location", TAG + "本地广播");
        mLocalBroadcastManager.sendBroadcast(localIntent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mStandardReceiver!=null)
            unregisterReceiver(mStandardReceiver);

        if (mLocalReceiver!=null)
            mLocalBroadcastManager.unregisterReceiver(mLocalReceiver);
    }

}
