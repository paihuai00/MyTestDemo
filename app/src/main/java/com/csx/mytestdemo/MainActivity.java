package com.csx.mytestdemo;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.csx.mlibrary.BaseActivity;

import com.csx.mytestdemo.mvp.MvpActivity;
import com.csx.mytestdemo.rxjava_test.RxJavaActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    @BindView(R.id.rxjava_btn)
    Button mRxjavaBtn;
    @BindView(R.id.mvp_btn)
    Button mMvpBtn;
    @BindView(R.id.dialog_btn)
    Button mDialogBtn;

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
    
    @OnClick({R.id.rxjava_btn, R.id.mvp_btn, R.id.dialog_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rxjava_btn:
                Intent intent = new Intent(MainActivity.this, RxJavaActivity.class);
                startActivity(intent);
                break;
            case R.id.mvp_btn:
                startActivity(new Intent(MainActivity.this, MvpActivity.class));
                break;
            case R.id.dialog_btn:
                break;
        }
    }

}
