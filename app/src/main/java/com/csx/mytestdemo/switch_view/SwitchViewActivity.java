package com.csx.mytestdemo.switch_view;

import android.widget.Toast;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.R;

import butterknife.BindView;

/**
 * Create by cuishuxiang
 *
 * @date: on 2018/9/27
 * @description:
 */
public class SwitchViewActivity extends BaseActivity {

    @BindView(R.id.switch_view)
    SwitchView mSwitchView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_switch_view;
    }

    @Override
    public void initView() {
        mSwitchView.setOnCheckListenter(new SwitchView.OnCheckListenter() {
            @Override
            public void onBoundCheck(boolean isLeft) {
                Toast.makeText(getBaseContext(), "左侧被选中？" + isLeft, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void initData() {

    }
}
