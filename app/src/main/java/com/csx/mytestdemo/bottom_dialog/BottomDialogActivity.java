package com.csx.mytestdemo.bottom_dialog;

import android.widget.Button;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Create by cuishuxiang
 *
 * @date: on 2018/7/26
 * @description:
 */

public class BottomDialogActivity extends BaseActivity {
    private static final String TAG = "BottomDialogActivity";
    @BindView(R.id.show_dialog_btn)
    Button mShowDialogBtn;



    @Override
    public int getLayoutId() {
        return R.layout.activity_bottom_dialog;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.show_dialog_btn)
    public void onClick() {
    }
}
