package com.csx.mytestdemo.loading_view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Created by cuishuxiang
 * @date 2018/4/27.
 * @description: 加载动画
 */

public class LoadingActivity extends BaseActivity {
    private static final String TAG = "LoadingActivity";
    @BindView(R.id.weibo_loading)
    Button mWeiboLoading;
    @BindView(R.id.show_loading_btn)
    Button mShowLoadingBtn;

    @Override
    public int getLayoutId() {
        return R.layout.activity_loading;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.weibo_loading, R.id.show_loading_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.weibo_loading:
                LoadingUtils.showLoading(this,"");
                break;
            case R.id.show_loading_btn:
                break;
        }
    }
}
