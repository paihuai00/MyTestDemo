package com.csx.mytestdemo.multiple_state;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mlibrary.utils.ToastUtils;
import com.csx.mytestdemo.R;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.kingja.loadsir.core.Transport;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Created by cuishuxiang
 * @date 2018/5/2.
 * @description: 多状态View
 */

public class MultipleActivity extends BaseActivity {
    private static final String TAG = "MultipleActivity";
    @BindView(R.id.state_loading_btn)
    Button mStateLoadingBtn;
    @BindView(R.id.state_empty_btn)
    Button mStateEmptyBtn;
    @BindView(R.id.state_error_btn)
    Button mStateErrorBtn;
    @BindView(R.id.state_success_btn)
    Button mStateSuccessBtn;

    private LoadSir mLoadSir;
    private LoadService mLoadService;

    @Override
    public int getLayoutId() {
        return R.layout.activity_multiple_state;
    }

    @Override
    public void initView() {
        mLoadService = LoadSir.getDefault().register(this, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                Log.d(TAG, "onReload: ");
                ToastUtils.showShortToast("重新加载");
                mLoadService.showCallback(LoadingCallBack.class);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mLoadService.showSuccess();
                    }
                }).start();

            }
        }).setCallBack(EmptyCallBack.class, new Transport() {
            @Override
            public void order(Context context, View view) {
                //这里可以动态修改回调页面的内容；
                //View就是 EmptyCallBack 的layout view
                Log.d(TAG, "order: ");
            }
        });
        mLoadService.showSuccess();
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.state_loading_btn, R.id.state_empty_btn, R.id.state_error_btn, R.id.state_success_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.state_loading_btn:
                mLoadService.showCallback(LoadingCallBack.class);
                break;
            case R.id.state_empty_btn:
                mLoadService.showCallback(EmptyCallBack.class);
                break;
            case R.id.state_error_btn:
                mLoadService.showCallback(ErrorCallBack.class);
                break;
            case R.id.state_success_btn:
                mLoadService.showSuccess();
                break;
        }
    }
}
