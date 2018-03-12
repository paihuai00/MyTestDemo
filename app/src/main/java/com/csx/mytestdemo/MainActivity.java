package com.csx.mytestdemo;

import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.audio_record.AudioActivity;
import com.csx.mytestdemo.bottom_bar.BottomBarActivity;
import com.csx.mytestdemo.broadcast_test.BroadCastActivity;
import com.csx.mytestdemo.common_dialog.CommonDialogActivity;
import com.csx.mytestdemo.drag_recyclerview.DragActivity;
import com.csx.mytestdemo.scroller_view.ScrollerActivity;
import com.csx.mytestdemo.share_mob.ShareActivity;
import com.csx.mytestdemo.mvp.MvpActivity;
import com.csx.mytestdemo.rxjava_test.RxJavaActivity;
import com.csx.mytestdemo.view_gesture_velocity.GestureVelocityActivity;
import com.csx.mytestdemo.view_touch_nine.NineDotActivity;
import com.csx.mytestdemo.view_touch_scroll.TouchScrollActivity;

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
    @BindView(R.id.audio_btn)
    Button mAudioBtn;
    @BindView(R.id.bottom_bar_btn)
    Button mBottomBarBtn;
    @BindView(R.id.broadcast_btn)
    Button mBroadcastBtn;
    @BindView(R.id.gesture_velocity_btn)
    Button mGestureVelocityBtn;
    @BindView(R.id.nice_dot_btn)
    Button mNiceDotBtn;
    @BindView(R.id.touch_scroll_btn)
    Button mTouchScrollBtn;
    @BindView(R.id.share_btn)
    Button mShareBtn;
    @BindView(R.id.scroller_btn)
    Button mScrollBtn;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float dpi = displayMetrics.densityDpi;
    }

    @Override
    public void initData() {
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        float dpi = mDisplayMetrics.densityDpi;
        Log.d(TAG, "设备dpi = " + dpi);

    }

    @OnClick({R.id.mvp_btn, R.id.dialog_btn, R.id.video_btn, R.id.drag_rv_btn, R.id.touch_scroll_btn,
            R.id.audio_btn, R.id.bottom_bar_btn,
            R.id.broadcast_btn, R.id.gesture_velocity_btn,
            R.id.share_btn, R.id.scroller_btn})
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
            case R.id.audio_btn:
                startActivity(new Intent(MainActivity.this, AudioActivity.class));
                break;
            case R.id.bottom_bar_btn:
                startActivity(new Intent(MainActivity.this, BottomBarActivity.class));
                break;
            case R.id.broadcast_btn:
                startActivity(new Intent(MainActivity.this, BroadCastActivity.class));
                break;
            case R.id.gesture_velocity_btn:
                startActivity(new Intent(MainActivity.this, GestureVelocityActivity.class));
                break;
            case R.id.nice_dot_btn:
                startActivity(new Intent(MainActivity.this, NineDotActivity.class));
                break;
            case R.id.touch_scroll_btn:
                startActivity(new Intent(MainActivity.this, TouchScrollActivity.class));
                break;
            case R.id.share_btn:
                startActivity(new Intent(MainActivity.this, ShareActivity.class));
                break;
            case R.id.scroller_btn:
                startActivity(new Intent(MainActivity.this, ScrollerActivity.class));
                break;


        }
    }

}
