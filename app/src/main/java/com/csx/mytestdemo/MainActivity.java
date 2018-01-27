package com.csx.mytestdemo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.csx.mlibrary.BaseActivity;
import com.csx.mytestdemo.rxjava_test.RxJavaActivity;
import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    @BindView(R.id.rxjava_btn)
    Button mRxjavaBtn;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.rxjava_btn)
    public void onViewClicked() {
        Intent intent = new Intent(MainActivity.this, RxJavaActivity.class);
        startActivity(intent);
    }
}
