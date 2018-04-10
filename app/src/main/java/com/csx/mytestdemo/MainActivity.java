package com.csx.mytestdemo;

import android.app.Activity;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.audio_record.AudioActivity;
import com.csx.mytestdemo.bottom_bar.BottomBarActivity;
import com.csx.mytestdemo.broadcast_test.BroadCastActivity;
import com.csx.mytestdemo.butterknife_test.ButterKnifeActivity;
import com.csx.mytestdemo.common_dialog.CommonDialogActivity;
import com.csx.mytestdemo.drag_recyclerview.DragActivity;
import com.csx.mytestdemo.float_menu.FloatMenuActivity;
import com.csx.mytestdemo.flow_view.FlowActivity;
import com.csx.mytestdemo.gson_test.GsonActivity;
import com.csx.mytestdemo.immerse_state_bar.StatusBarActivity;
import com.csx.mytestdemo.keyboard_test.KeyBoardActivity;
import com.csx.mytestdemo.ksoap_webservice.KsoapActivity;
import com.csx.mytestdemo.mvp.MvpActivity;
import com.csx.mytestdemo.rxjava_test.RxJavaActivity;
import com.csx.mytestdemo.scroller_view.ScrollerActivity;
import com.csx.mytestdemo.service_test.ServiceActivity;
import com.csx.mytestdemo.share_mob.ShareActivity;
import com.csx.mytestdemo.thread_test.ThreadActivity;
import com.csx.mytestdemo.view_gesture_velocity.GestureVelocityActivity;
import com.csx.mytestdemo.view_slide_menu.SlideMenuActivity;
import com.csx.mytestdemo.view_touch_nine.NineDotActivity;
import com.csx.mytestdemo.view_touch_scroll.TouchScrollActivity;
import com.csx.mytestdemo.wifi_demo.WifiActivity;

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
    @BindView(R.id.slide_menu_btn)
    Button mSlideMenuBtn;
    @BindView(R.id.ksoap_btn)
    Button mKsoapBtn;
    @BindView(R.id.bf_btn)
    Button mBfBtn;
    @BindView(R.id.gson_btn)
    Button mGsonBtn;
    @BindView(R.id.flow_btn)
    Button mFlowBtn;
    @BindView(R.id.state_bar_btn)
    Button mStateBarBtn;
    @BindView(R.id.asynctask_btn)
    Button mAsyncTaskBtn;
    @BindView(R.id.keyboard_btn)
    Button mKeyBoardBtn;
    @BindView(R.id.wifi_btn)
    Button mWifiBtn;
    @BindView(R.id.float_menu_btn)
    Button mFloatMenuBtn;
    @BindView(R.id.service_btn)
    Button mServiceBtn;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float dpi = displayMetrics.densityDpi;
        Log.d(TAG, "设备dpi = " + dpi);

        float widthPixels = displayMetrics.widthPixels;
        float heightPixels = displayMetrics.heightPixels;

        Log.d(TAG, "initView: widthPixels = " + widthPixels + "   heightPixels = " + heightPixels);

        Log.d(TAG, "initView: 核心线程数：" + Runtime.getRuntime().availableProcessors());

    }

    @Override
    public void initData() {

    }


    @OnClick({R.id.rxjava_btn, R.id.mvp_btn, R.id.dialog_btn, R.id.video_btn,
            R.id.drag_rv_btn, R.id.touch_scroll_btn, R.id.audio_btn, R.id.bottom_bar_btn,
            R.id.broadcast_btn, R.id.gesture_velocity_btn, R.id.share_btn,
            R.id.scroller_btn, R.id.slide_menu_btn, R.id.ksoap_btn, R.id.gson_btn,
            R.id.bf_btn, R.id.flow_btn, R.id.state_bar_btn, R.id.asynctask_btn, R.id.keyboard_btn,
            R.id.wifi_btn, R.id.float_menu_btn,R.id.service_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rxjava_btn:
                openActivity(RxJavaActivity.class);
                break;
            case R.id.mvp_btn:
                openActivity(MvpActivity.class);
                break;
            case R.id.dialog_btn:
                openActivity(CommonDialogActivity.class);
                break;
            case R.id.video_btn:
                openActivity(DragActivity.class);
                break;
            case R.id.audio_btn:
                openActivity(AudioActivity.class);
                break;
            case R.id.bottom_bar_btn:
                openActivity(BottomBarActivity.class);
                break;
            case R.id.broadcast_btn:
                openActivity(BroadCastActivity.class);
                break;
            case R.id.gesture_velocity_btn:
                openActivity(GestureVelocityActivity.class);
                break;
            case R.id.nice_dot_btn:
                openActivity(NineDotActivity.class);
                break;
            case R.id.touch_scroll_btn:
                openActivity(TouchScrollActivity.class);
                break;
            case R.id.share_btn:
                openActivity(ShareActivity.class);
                break;
            case R.id.scroller_btn:
                openActivity(ScrollerActivity.class);
                break;
            case R.id.slide_menu_btn:
                openActivity(SlideMenuActivity.class);
                break;
            case R.id.ksoap_btn:
                openActivity(KsoapActivity.class);
                break;
            case R.id.gson_btn:
                openActivity(GsonActivity.class);
                break;
            case R.id.bf_btn:
                openActivity(ButterKnifeActivity.class);
                break;
            case R.id.flow_btn:
                openActivity(FlowActivity.class);
                break;
            case R.id.state_bar_btn:
                openActivity(StatusBarActivity.class);
                break;
            case R.id.asynctask_btn:
                openActivity(ThreadActivity.class);
                break;
            case R.id.keyboard_btn:
                openActivity(KeyBoardActivity.class);
                break;
            case R.id.wifi_btn:
                openActivity(WifiActivity.class);
                break;
            case R.id.float_menu_btn:
                startActivity(new Intent(MainActivity.this, FloatMenuActivity.class));
                break;

            case R.id.service_btn:
                startActivity(new Intent(MainActivity.this, ServiceActivity.class));
                break;


        }
    }

    /**
     * 跳转到 c.class
     * @param c
     */
    private void  openActivity(Class c) {
        startActivity(new Intent(MainActivity.this, c));

    }

}
