package com.csx.mytestdemo.butterknife_test;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mlibrary.utils.ToastUtils;
import com.csx.mytestdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import butterknife.OnTextChanged;

/**
 * @Created by cuishuxiang
 * @date 2018/3/20.
 * @description: 黄油刀的高级用法
 */

public class ButterKnifeActivity extends BaseActivity {
    private static final String TAG = "ButterKnifeActivity";
    @BindView(R.id.bf_et)
    EditText mBfEt;
    @BindView(R.id.bf_btn)
    Button mBfBtn;

    @Override
    public int getLayoutId() {
        return R.layout.activity_butterknife;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @OnTextChanged({R.id.bf_et})
    public void onTextChanged(View view, CharSequence s, int start, int before, int count) {
        Log.d(TAG, "onTextChange: butterknife = " + s + "  start = " + start + " before = " + before + " count = " + count);

        ToastUtils.showShortToast(s);
    }

    @OnClick(R.id.bf_btn)
    public void onViewClicked(View view) {
        ToastUtils.showShortToast("点击了按钮 onViewClicked() ");
    }

    @OnLongClick(R.id.bf_btn)
    public boolean onLongClick(View view) {
        Log.d(TAG, "onLongClick: ");
        ToastUtils.showShortToast("长按按钮 bf_btn");
        return true;
    }
}
