package com.csx.mytestdemo.progress_view;

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
 * @Created by cuishuxiang
 * @date 2018/5/4.
 * @description:
 */

public class ProgressActivity extends BaseActivity {
    private static final String TAG = "ProgressActivity";
    @BindView(R.id.hpv_language)
    HorizontalProgressView mHpvLanguage;
    @BindView(R.id.btn_start)
    Button mBtnStart;
    @BindView(R.id.progressView_circle)
    CircleProgressView mProgressViewCircle;

    @Override
    public int getLayoutId() {
        return R.layout.activity_progress;
    }

    @Override
    public void initView() {
        mHpvLanguage.setProgressViewUpdateListener(new HorizontalProgressView.HorizontalProgressUpdateListener() {
            @Override
            public void onHorizontalProgressStart(View view) {
                Log.d(TAG, "onHorizontalProgressStart: ");
            }

            @Override
            public void onHorizontalProgressUpdate(View view, float progress) {
                Log.d(TAG, "onHorizontalProgressUpdate: " + progress);
            }

            @Override
            public void onHorizontalProgressFinished(View view) {
                Log.d(TAG, "onHorizontalProgressFinished: ");
            }
        });

        mProgressViewCircle.setProgressViewUpdateListener(new CircleProgressView.CircleProgressUpdateListener() {
            @Override
            public void onCircleProgressStart(View view) {
                Log.d(TAG, "onCircleProgressStart: ");
            }

            @Override
            public void onCircleProgressUpdate(View view, float progress) {
                Log.d(TAG, "onCircleProgressUpdate: ");
            }

            @Override
            public void onCircleProgressFinished(View view) {
                Log.d(TAG, "onCircleProgressFinished: ");
            }
        });


    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.btn_start)
    public void onViewClicked() {
        mHpvLanguage.startProgressAnimation();

        mProgressViewCircle.setGraduatedEnabled(true);
        mProgressViewCircle.startProgressAnimation();
    }


}
