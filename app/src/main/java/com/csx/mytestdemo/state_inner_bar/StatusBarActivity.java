package com.csx.mytestdemo.state_inner_bar;

import android.graphics.Color;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.R;

/**
 * Created by cuishuxiang on 2018/3/24.
 *
 * 沉浸式 状态栏
 */

public class StatusBarActivity extends BaseActivity {
    private static final String TAG = "StatusBarActivity";

    @Override
    public int getLayoutId() {
        return R.layout.activity_state_inner;
    }

    @Override
    public void initView() {
        StatusBarUtils.setStatusBarColor(this, Color.RED);

    }

    @Override
    public void initData() {

    }
}
