package com.csx.mytestdemo;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;

import com.csx.mlibrary.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import yanzhikai.textpath.PathAnimatorListener;
import yanzhikai.textpath.PathView;
import yanzhikai.textpath.SyncTextPathView;

/**
 * @Created by cuishuxiang
 * @date 2018/4/10.
 * @description:
 */

public class WelcomeActivity extends BaseActivity {
    private static final String TAG = "WelcomeActivity";

    @BindView(R.id.path_view)
    SyncTextPathView mPathView;

    private MyListener mPathAnimatorListener;

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initView() {
//        mPathView.setTypeface(Typeface.SERIF);


        mPathAnimatorListener = new MyListener();

        mPathView.setAnimatorListener(mPathAnimatorListener);

        mPathView.startAnimation(0,1);
    }

    @Override
    public void initData() {

    }


    class MyListener extends PathAnimatorListener {
        public MyListener() {
            super();
        }

        @Override
        protected void setTarget(PathView pathView) {
            super.setTarget(pathView);
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
            super.onAnimationRepeat(animation);
        }

        @Override
        public void onAnimationStart(Animator animation) {
            super.onAnimationStart(animation);
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            Log.d(TAG, "onAnimationEnd: ");

            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));

            finish();
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            super.onAnimationCancel(animation);
        }
    }


}
