package com.csx.mytestdemo;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.common_dialog.CommonDialogActivity;
import com.csx.mytestdemo.drag_recyclerview.DragActivity;
import com.csx.mytestdemo.mvp.MvpActivity;
import com.csx.mytestdemo.rxjava_test.RxJavaActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    @BindView(R.id.rxjava_btn)
    Button mRxjavaBtn;
    @BindView(R.id.mvp_btn)
    Button mMvpBtn;
    @BindView(R.id.dialog_btn)
    Button mDialogBtn;
    @BindView(R.id.video_btn)
    Button mVideoBtn;
    @BindView(R.id.drag_rv_btn)
    Button mDragRvBtn;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.rxjava_btn, R.id.mvp_btn, R.id.dialog_btn, R.id.video_btn, R.id.drag_rv_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rxjava_btn:
                Intent intent = new Intent(MainActivity.this, RxJavaActivity.class);
                startActivity(intent);
                break;
            case R.id.mvp_btn:
                startActivity(new Intent(MainActivity.this, MvpActivity.class));
                break;
            case R.id.dialog_btn:
                startActivity(new Intent(MainActivity.this, CommonDialogActivity.class));
                break;
            case R.id.video_btn:
                break;
            case R.id.drag_rv_btn:
                startActivity(new Intent(MainActivity.this, DragActivity.class));
                break;
        }
    }
}
