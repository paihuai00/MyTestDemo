package com.csx.mytestdemo.keyboard_test;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cuishuxiang on 2018/3/26.
 * <p>
 * 键盘处理
 * 1，windowSoftInputMode 的测试
 * 2，监听软键盘是否弹出。
 * 3，添加  EditText（包含 隐藏or清除 功能）
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
    @BindView(R.id.current_mode_tv)
    TextView mCurrentModeTv;

    @Override
    public int getLayoutId() {
        return R.layout.activity_keyboard;
    }

    @Override
    public void initView() {
//        rootLl.addOnLayoutChangeListener(this);

        mCurrentModeTv.setText("当前mode：adjustPan ");

        mCurrentModeTv.addOnLayoutChangeListener(this);

    }

    @Override
    public void initData() {

    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        Log.d(TAG, "onLayoutChange: oldBottom = " + oldBottom + "  bottom = " +
                bottom + "  top= " + top + "  oldTop = " + oldTop + "\nisSoftShowing() = " + isSoftShowing());
    }


    private boolean isSoftShowing() {
        //获取当前屏幕内容的高度
        int screenHeight = getWindow().getDecorView().getHeight();
        //获取View可见区域的bottom
        Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);

        return screenHeight - rect.bottom != 0;
    }

}
