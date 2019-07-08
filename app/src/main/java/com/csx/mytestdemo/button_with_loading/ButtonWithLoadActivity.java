package com.csx.mytestdemo.button_with_loading;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Date: 2019/7/8
 * create by cuishuxiang
 * description:
 */
public class ButtonWithLoadActivity extends BaseActivity {
    private static final String TAG = "ButtonWithLoadActivity";
    @BindView(R.id.btn_start)
    Button mBtnStart;
    @BindView(R.id.btn_stop)
    Button mBtnStop;
    @BindView(R.id.load_view)
    LoadButtonView mLoadView;
    @BindView(R.id.btn_complete)
    Button mBtnComplete;
    @BindView(R.id.btn_failed)
    Button mBtnFailed;

    @Override
    public int getLayoutId() {
        return R.layout.activity_btn_with_load;
    }

    @Override
    public void initView() {
        mLoadView.setEnableShrink(true)
                .setLoadingPosition(DrawableTextView.POSITION.START)//加载框位于左侧
                .setShrinkDuration(500)//动画持续时间
                .setOnLoadingListener(new OnLoadBtnListen() {
                    @Override
                    public void onLoadingStart() {
                        Log.d(TAG, "onLoadingStart: onLoadingStart");
                    }

                    @Override
                    public void onLoadingStop() {
                        Log.d(TAG, "onLoadingStart: onLoadingStop");
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onLoadingStart: onCompleted");
                    }

                    @Override
                    public void onFailed() {
                        Log.d(TAG, "onLoadingStart: onFailed");
                    }

                    @Override
                    public void onCanceled() {
                        Log.d(TAG, "onLoadingStart: onCanceled");
                    }
                });


    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.btn_start, R.id.btn_stop, R.id.btn_complete, R.id.btn_failed})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                mLoadView.start();
                break;
            case R.id.btn_stop:
                mLoadView.cancel();
                break;
            case R.id.btn_complete:
                mLoadView.complete();
                break;
            case R.id.btn_failed:
                mLoadView.fail();
                break;
        }
    }
}
