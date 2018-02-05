package com.csx.mlibrary.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Created by cuishuxiang
 * @date 2018/1/25.
 * base
 */

public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getSimpleName().toString();
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mUnbinder = ButterKnife.bind(this);
        //初始化View
        initView();

        //加载数据
        initData();
    }


    //加载布局文件
    public abstract int getLayoutId();

    //初始化View
    public abstract void initView();

    //初始化数据
    public abstract void initData();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mUnbinder) {
            mUnbinder.unbind();
        }

    }
}
