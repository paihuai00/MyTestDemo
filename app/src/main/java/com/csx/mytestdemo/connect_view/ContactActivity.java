package com.csx.mytestdemo.connect_view;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Created by cuishuxiang
 * @date 2018/4/16.
 * @description: 连线View
 */

public class ContactActivity extends BaseActivity {
    private static final String TAG = "ContactActivity";
    @BindView(R.id.connect_view)
    ConnectLineView mConnectView;
    @BindView(R.id.loading_view)
    ImageView mLoadingView;

    AnimationDrawable mAnimationDrawable;

    @Override
    public int getLayoutId() {
        return R.layout.activity_contact;
    }

    @Override
    public void initView() {
        mConnectView.setMaxLinesNum(1);

        mConnectView.setConnectCallBack(new ConnectLineView.ConnectCallBack() {
            @Override
            public void onFailConnect(String failInfo) {
                Log.d(TAG, "onFailConnect: " + failInfo);
            }

            @Override
            public void onSucceedConnect(List<ConnectLineView.LinesData> linesDataList) {
                Log.d(TAG, "onSucceedConnect: " + linesDataList.toString());
            }
        });

//        mLoadingView.setImageResource(R.drawable.loading_anim);
        //loading 帧动画
        mAnimationDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.loading_anim);

        mLoadingView.setImageDrawable(mAnimationDrawable);

        mAnimationDrawable.start();
    }

    @Override
    public void initData() {

    }


}
