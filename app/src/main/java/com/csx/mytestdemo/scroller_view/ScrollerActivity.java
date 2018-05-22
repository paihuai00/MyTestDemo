package com.csx.mytestdemo.scroller_view;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Created by cuishuxiang
 * @date 2018/3/12.
 * @description: Scroller使用
 */

public class ScrollerActivity extends BaseActivity {
    private static final String TAG = "ScrollerActivity";

    @BindView(R.id.touch_by_btn)
    Button mTouchByBtn;
    @BindView(R.id.touch_to_btn)
    Button mTouchToBtn;
    @BindView(R.id.root_scroller_l)
    LinearLayout mRootScrollerL;
    @BindView(R.id.toggle_btn)
    ToggleButton mToggleBtn;

    @Override
    public int getLayoutId() {
        return R.layout.activity_scroller;
    }

    @Override
    public void initView() {
        mToggleBtn.setOnToggledListener(new ToggleListener() {
            @Override
            public void onToggled(boolean isOpen) {
                Log.d(TAG, "onToggled: " + isOpen);
            }
        });
    }

    @Override
    public void initData() {

    }


    @OnClick({R.id.touch_by_btn, R.id.touch_to_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.touch_by_btn:
                mRootScrollerL.scrollBy(-50, -50);
                break;
            case R.id.touch_to_btn:
                mRootScrollerL.scrollTo(-50, -50);
                break;
        }
    }

}
