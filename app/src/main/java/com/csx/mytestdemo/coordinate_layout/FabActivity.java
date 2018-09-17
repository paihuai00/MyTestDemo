package com.csx.mytestdemo.coordinate_layout;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.widget.Button;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Create by cuishuxiang
 *
 * @date: on 2018/8/29
 * @description: 协调者布局 + FAB + snakerbar
 */
public class FabActivity extends BaseActivity {

//    @BindView(R.id.snake_btn)
//    Button mSnakeBtn;
    @BindView(R.id.fab)
    FloatingActionButton mFab;

    @Override
    public int getLayoutId() {
        return R.layout.activity_fab;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.fab})
    public void onClick() {
        Snackbar.make(mFab, "这是SnackBar", Snackbar.LENGTH_SHORT).show();
    }

}
