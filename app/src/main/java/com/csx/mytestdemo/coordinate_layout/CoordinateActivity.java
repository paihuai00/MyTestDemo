package com.csx.mytestdemo.coordinate_layout;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Create by cuishuxiang
 *
 * @date: on 2018/8/29
 * @description:
 */
public class CoordinateActivity extends BaseActivity {

    @BindView(R.id.fab_btn)
    Button mFabBtn;
    @BindView(R.id.behavior_btn)
    Button mBehavior_btnBtn;
    @BindView(R.id.collapsing_btn)
    Button mCollapsingBtn;

    @Override
    public int getLayoutId() {
        return R.layout.activity_coordinate;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.fab_btn, R.id.behavior_btn, R.id.collapsing_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_btn:
                open(FabActivity.class);
                break;
            case R.id.behavior_btn:
                open(SimpleBehaviorActivity.class);
                break;
            case R.id.collapsing_btn:
                break;
        }
    }


    private void open(Class c) {
        Intent mIntent = new Intent(CoordinateActivity.this, c);
        startActivity(mIntent);
    }
}
