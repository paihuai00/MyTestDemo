package com.csx.mytestdemo.keyboard_test;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cuishuxiang on 2018/3/26.
 * <p>
 * 键盘处理
 */

public class KeyBoardActivity extends BaseActivity implements View.OnLayoutChangeListener {
    private static final String TAG = "KeyBoardActivity";
    @BindView(R.id.input_et_1)
    EditText inputEt1;
    @BindView(R.id.input_et_2)
    EditText inputEt2;
    @BindView(R.id.input_et_3)
    EditText inputEt3;
    @BindView(R.id.input_et_4)
    EditText inputEt4;
    @BindView(R.id.input_et_5)
    EditText inputEt5;
    @BindView(R.id.root_ll)
    LinearLayout rootLl;

    @Override
    public int getLayoutId() {
        return R.layout.activity_keyboard;
    }

    @Override
    public void initView() {
        rootLl.addOnLayoutChangeListener(this);

    }

    @Override
    public void initData() {

    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        Log.d(TAG, "onLayoutChange: oldBottom = " + oldBottom + "  bottom = " + bottom);
    }


}
