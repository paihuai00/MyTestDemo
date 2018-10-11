package com.csx.mytestdemo;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.csx.mlibrary.base.BaseActivity;
import com.csx.mlibrary.utils.XPermission;
import com.csx.mytestdemo.app.LocationUtil;
import com.csx.mytestdemo.audio_record.AudioActivity;
import com.csx.mytestdemo.auto_size.AutoSizeActivity;
import com.csx.mytestdemo.banner_.BannerActivity;
import com.csx.mytestdemo.bottom_bar.BottomBarActivity;
import com.csx.mytestdemo.bottom_dialog.BottomDialogActivity;
import com.csx.mytestdemo.broadcast_test.BroadCastActivity;
import com.csx.mytestdemo.butterknife_test.ButterKnifeActivity;
import com.csx.mytestdemo.color_picker.ColorPickerActivity;
import com.csx.mytestdemo.common_dialog.CommonDialogActivity;
import com.csx.mytestdemo.coordinate_layout.CoordinateActivity;
import com.csx.mytestdemo.diffutil_rv.DiffUtilsActivity;
import com.csx.mytestdemo.drag_edittext.DragEditTextActivity;
import com.csx.mytestdemo.drag_recyclerview.DragActivity;
import com.csx.mytestdemo.expend_textview.ExpendTvActivity;
import com.csx.mytestdemo.float_menu.FloatMenuActivity;
import com.csx.mytestdemo.flow_view.FlowActivity;
import com.csx.mytestdemo.glide4.Glide4Activity;
import com.csx.mytestdemo.gson_test.GsonActivity;
import com.csx.mytestdemo.head_vp_fg.ViewPagerActivity;
import com.csx.mytestdemo.image_select.CustomImageSelectActivity;
import com.csx.mytestdemo.immerse_state_bar.StatusBarActivity;
import com.csx.mytestdemo.keyboard_test.KeyBoardActivity;
import com.csx.mytestdemo.ksoap_webservice.KsoapActivity;
import com.csx.mytestdemo.lazy_fg.LazyFgActivity;
import com.csx.mytestdemo.line_connect_view.ContactActivity;
import com.csx.mytestdemo.loading_view.LoadingActivity;
import com.csx.mytestdemo.multiple_state.MultipleActivity;
import com.csx.mytestdemo.mvp.MvpActivity;
import com.csx.mytestdemo.my_butterknife.MyButterKnifeActivity;
import com.csx.mytestdemo.photoview.PhotoViewActivity;
import com.csx.mytestdemo.progress_view.ProgressActivity;
import com.csx.mytestdemo.rxjava_test.RxJavaActivity;
import com.csx.mytestdemo.scroller_view.ScrollerActivity;
import com.csx.mytestdemo.service_test.ServiceActivity;
import com.csx.mytestdemo.share_mob.ShareActivity;
import com.csx.mytestdemo.smart_refresh.SmartRefreshActivity;
import com.csx.mytestdemo.sticky_recyclerview.StickyRvActivity;
import com.csx.mytestdemo.switch_view.SwitchViewActivity;
import com.csx.mytestdemo.thread_test.ThreadActivity;
import com.csx.mytestdemo.transform_anim.TransformActivity;
import com.csx.mytestdemo.video_player.VideoPlayerActivity;
import com.csx.mytestdemo.view_gesture_velocity.GestureVelocityActivity;
import com.csx.mytestdemo.view_slide_menu.SlideMenuActivity;
import com.csx.mytestdemo.view_touch_nine.NineDotActivity;
import com.csx.mytestdemo.view_touch_scroll.TouchScrollActivity;
import com.csx.mytestdemo.webview_progressbar.WebViewActivity;
import com.csx.mytestdemo.wifi_demo.WifiActivity;

import butterknife.BindView;
import butterknife.OnClick;

import static android.os.Environment.DIRECTORY_MUSIC;

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
    @BindView(R.id.contact_btn)
    Button mContactBtn;
    @BindView(R.id.multiple_state_btn)
    Button mMultipleStateBtn;
    @BindView(R.id.webview_btn)
    Button mWebViewBtn;
    @BindView(R.id.progress_btn)
    Button mProgressBtn;
    @BindView(R.id.drag_et_btn)
    Button mDragEditTextBtn;
    @BindView(R.id.color_picker_btn)
    Button mColorPickerBtn;
    @BindView(R.id.photoview_btn)
    Button mPhotoViewBtn;
    @BindView(R.id.glide4_btn)
    Button mGlide4Btn;
    @BindView(R.id.diff_utils_btn)
    Button mDiffUtilsBtn;
    @BindView(R.id.loading_btn)
    Button mLoadingBtn;
    @BindView(R.id.sticky_rv_btn)
    Button mStickyRvBtn;
    @BindView(R.id.lazy_fg_btn)
    Button mLazyFgBtn;
    @BindView(R.id.refresh_btn)
    Button mRefreshBtn;
    @BindView(R.id.bottom_dialog_btn)
    Button mBottomDialogBtn;
    @BindView(R.id.banner_btn)
    Button mBannerBtn;
    @BindView(R.id.transform_explode_btn)
    Button mExplodeBtn;
    @BindView(R.id.transform_fade_btn)
    Button mFadeBtn;
    @BindView(R.id.transform_slide_btn)
    Button mSlideBtn;
    @BindView(R.id.vp_fg_btn)
    Button mVpBtn;
    @BindView(R.id.coordinate)
    Button mConstraintBtn;
    @BindView(R.id.btn_select_image)
    Button mImageSelectBtn;
    @BindView(R.id.btn_switch)
    Button mSwitchBtn;
    @BindView(R.id.btn_butterknife)
    Button mButterKnifeBtn;

    public AMapLocationClient mAMapLocationClient;
    @BindView(R.id.tv_city)
    TextView mTvCity;
//    @BindView(R.id.btn_auto_size)
    Button mBtnAutoSize;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float dpi = displayMetrics.densityDpi;
        Log.e(TAG, "设备displayMetrics.densityDpi = " + dpi);
        Log.e(TAG, "设备displayMetrics.density = " + displayMetrics.density);
        Log.e(TAG, "设备displayMetrics.scaledDensity = " + displayMetrics.scaledDensity);

        float widthPixels = displayMetrics.widthPixels;
        float heightPixels = displayMetrics.heightPixels;

        Log.e(TAG, "initView: widthPixels = " + widthPixels + "   heightPixels = " + heightPixels);

        Log.e(TAG, "initView: 核心线程数：" + Runtime.getRuntime().availableProcessors());

//        mStringList.add("11");

        Log.e(TAG, "Environment.getExternalStorageDirectory() = " + Environment.getExternalStorageDirectory());
        Log.e(TAG, "Environment.getExternalStorageDirectory().getAbsolutePath()= " + Environment.getExternalStorageDirectory().getAbsolutePath());
        Log.e(TAG, "Environment.getExternalStoragePublicDirectory(DIRECTORY_MUSIC)= " + Environment.getExternalStoragePublicDirectory(DIRECTORY_MUSIC));
        Log.e(TAG, "Environment.getDownloadCacheDirectory() = " + Environment.getDownloadCacheDirectory());
        Log.e(TAG, "Environment.getExternalStorageState() = " + Environment.getExternalStorageState());
        Log.e(TAG, "getExternalCacheDir().getAbsolutePath() =  " + getExternalCacheDir().getAbsolutePath());


        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
            XPermission.requestPermissions(this, 100, new String[]{Manifest.permission_group.LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, new XPermission.OnPermissionListener() {
                @Override
                public void onPermissionGranted() {
                    initGaoDeMap();
                }

                @Override
                public void onPermissionDenied() {
                    Toast.makeText(getApplicationContext(), "定位权限被拒绝！", Toast.LENGTH_SHORT).show();
                }
            });
//        initGaoDeMap();

        mBtnAutoSize.setText("11");
    }

    private void initGaoDeMap() {
        mAMapLocationClient = LocationUtil.getLocation(this, new LocationUtil.LocationListener() {
            @Override
            public void onLocation(AMapLocation location) {
                if (location != null) {
                    if (location.getErrorCode() == 0) {
                        //定位成功
                        String city = location.getCity();
                        System.out.println("@city:" + city);
                        if (!TextUtils.isEmpty(city)) {
                            mTvCity.setText("当前：" + city);
                            Toast.makeText(getApplicationContext(), "当前：" + city, Toast.LENGTH_SHORT).show();
                        }
                        mAMapLocationClient.stopLocation();
                    } else {
                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError", "location Error, ErrCode:"
                                + location.getErrorCode() + ", errInfo:"
                                + location.getErrorInfo());
                    }
                }
            }
        });
    }

    @Override
    public void initData() {

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.rxjava_btn, R.id.mvp_btn, R.id.dialog_btn, R.id.video_btn,
            R.id.drag_rv_btn, R.id.touch_scroll_btn, R.id.audio_btn, R.id.bottom_bar_btn,
            R.id.broadcast_btn, R.id.gesture_velocity_btn, R.id.share_btn,
            R.id.scroller_btn, R.id.slide_menu_btn, R.id.ksoap_btn, R.id.gson_btn,
            R.id.bf_btn, R.id.flow_btn, R.id.state_bar_btn, R.id.asynctask_btn, R.id.keyboard_btn,
            R.id.wifi_btn, R.id.float_menu_btn, R.id.service_btn, R.id.contact_btn,
            R.id.loading_btn, R.id.multiple_state_btn, R.id.webview_btn, R.id.progress_btn,
            R.id.drag_et_btn, R.id.color_picker_btn, R.id.photoview_btn, R.id.glide4_btn,
            R.id.diff_utils_btn, R.id.sticky_rv_btn, R.id.lazy_fg_btn, R.id.refresh_btn, R.id.banner_btn,
            R.id.bottom_dialog_btn, R.id.transform_explode_btn, R.id.transform_slide_btn, R.id.transform_fade_btn,
            R.id.vp_fg_btn, R.id.coordinate, R.id.btn_select_image, R.id.btn_auto_size, R.id.btn_expend, R.id.btn_switch,
            R.id.btn_butterknife})
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
            case R.id.drag_rv_btn:
                openActivity(DragActivity.class);
                break;
            case R.id.video_btn:
                openActivity(VideoPlayerActivity.class);
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

            case R.id.contact_btn:
                openActivity(ContactActivity.class);
                break;
            case R.id.loading_btn:
                openActivity(LoadingActivity.class);
                break;
            case R.id.multiple_state_btn:
                openActivity(MultipleActivity.class);
                break;
            case R.id.webview_btn:
                openActivity(WebViewActivity.class);
                break;
            case R.id.progress_btn:
                openActivity(ProgressActivity.class);
                break;
            case R.id.drag_et_btn:
                openActivity(DragEditTextActivity.class);
                break;
            case R.id.color_picker_btn:
                openActivity(ColorPickerActivity.class);
                break;
            case R.id.photoview_btn:
                openActivity(PhotoViewActivity.class);
                break;
            case R.id.glide4_btn:
                openActivity(Glide4Activity.class);
                break;
            case R.id.diff_utils_btn:
                openActivity(DiffUtilsActivity.class);
                break;
            case R.id.sticky_rv_btn:
                openActivity(StickyRvActivity.class);
                break;
            case R.id.lazy_fg_btn:
                openActivity(LazyFgActivity.class);
                break;
            case R.id.refresh_btn:
                openActivity(SmartRefreshActivity.class);
                break;
            case R.id.bottom_dialog_btn:
                openActivity(BottomDialogActivity.class);
                break;
            case R.id.banner_btn:
                openActivity(BannerActivity.class);
                break;
            case R.id.transform_explode_btn:
                Intent explodeIntent = new Intent(this, TransformActivity.class);
                explodeIntent.putExtra("flag", "explode");
                startActivity(explodeIntent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());

                break;
            case R.id.transform_slide_btn:
                Intent slideIntent = new Intent(this, TransformActivity.class);
                slideIntent.putExtra("flag", "slide");
                startActivity(slideIntent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());

                break;
            case R.id.transform_fade_btn:
                Intent fadeIntent = new Intent(this, TransformActivity.class);
                fadeIntent.putExtra("flag", "fade");
                startActivity(fadeIntent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());

                break;
            case R.id.vp_fg_btn:
                openActivity(ViewPagerActivity.class);
                break;
            case R.id.coordinate:
                openActivity(CoordinateActivity.class);
                break;
            case R.id.btn_select_image:
                openActivity(CustomImageSelectActivity.class);
                break;
            case R.id.btn_auto_size:
                openActivity(AutoSizeActivity.class);
                break;
            case R.id.btn_expend:
                openActivity(ExpendTvActivity.class);
                break;
            case R.id.btn_switch:
                openActivity(SwitchViewActivity.class);
                break;
            case R.id.btn_butterknife:
                openActivity(MyButterKnifeActivity.class);
                break;
        }
    }

    /**
     * 跳转到 c.class
     *
     * @param c
     */
    private void openActivity(Class c) {
        Intent intent = new Intent(MainActivity.this, c);
        overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d(TAG, "onBackPressed: ");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

}
