package com.csx.mytestdemo.audio_record;

import android.Manifest;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mlibrary.dialog.CommonDialog;
import com.csx.mlibrary.utils.ToastUtils;
import com.csx.mytestdemo.R;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cafe.adriel.androidaudioconverter.AndroidAudioConverter;
import cafe.adriel.androidaudioconverter.callback.IConvertCallback;
import cafe.adriel.androidaudioconverter.model.AudioFormat;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * @Created by cuishuxiang
 * @date 2018/2/6.
 * <p>
 * 录音功能  需动态处理，录音/存储权限
 */
@RuntimePermissions
public class AudioActivity extends BaseActivity {
    private static final String TAG = "AudioActivity";
    @BindView(R.id.audio_time_count)
    Chronometer mAudioTimeCount;
    @BindView(R.id.audio_voice_img)
    ImageView mAudioVoiceImg;
    @BindView(R.id.audio_notice_txt)
    TextView mAudioNoticeTxt;
    @BindView(R.id.audio_btn)
    Button mAudioBtn;
    @BindView(R.id.audio_root_view)
    LinearLayout mAudioRootView;

    private int mMaxAudioTime = 60 * 1000;//最长录音60s
    private int mMinAudioTime = 2 * 1000;//最短不少于 2s
    private int mCurAudioTime = 0;//已经录制的时长

    private AudioUtils mAudioUtils;

    private boolean isCancel = false;
    private View.OnTouchListener mOnTouchListener;


    /**
     * 是否要转换成mp3
     * 参考:https://github.com/adrielcafe/AndroidAudioConverter
     *
     * 转换为MP3格式，有个坑；录音的输出格式，最好不要是 MPEG_4
     * 可以设置为default ，后缀为 wav / amr ;这样转换出来的MP3 在ios上也是可以播放的
     */
    private boolean isTurnMp3 = true;

    @Override
    public int getLayoutId() {
        return R.layout.activity_audio;
    }

    @Override
    public void initView() {
        mAudioUtils = new AudioUtils(this);
        Log.d(TAG, "initView: start" + System.currentTimeMillis());

        AudioActivityPermissionsDispatcher.audioNeedPermissionWithPermissionCheck(this);
        //设置格式(默认"MM:SS"格式)
        mAudioTimeCount.setFormat("%S");
        mAudioTimeCount.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                //SystemClock.elapsedRealtime() 系统当前时间
                //chronometer.getBase()   计数器开始时间
                mCurAudioTime = (int) (SystemClock.elapsedRealtime() - chronometer.getBase());
                if (mCurAudioTime > mMaxAudioTime) {
                    mAudioUtils.stopAudioRecord();
                    ToastUtils.showShortToast("最多录音 " + mMaxAudioTime + " s");
                }

            }
        });
        initTouchListener();

        //touch Listener 处理，手势问题；
        mAudioBtn.setOnTouchListener(mOnTouchListener);


        Log.d(TAG, "initView: end" + System.currentTimeMillis());
    }

    /**
     * 滑动取消录制
     */
    private void initTouchListener() {
        mOnTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                float downY = 0;//按下的坐标

                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        downY = event.getY();
                        Log.d(TAG, "onTouch: down  - downY :" + downY);
                        mAudioRootView.setVisibility(View.VISIBLE);
                        mAudioTimeCount.setBase(SystemClock.elapsedRealtime());
                        mAudioTimeCount.start();
                        mAudioUtils.startAudioRecord();

                        break;
                    case MotionEvent.ACTION_MOVE:
                        int currentY = (int) event.getY();
                        Log.d(TAG, "onTouch: move - currentY = " + currentY);
                        if (downY - currentY >= 10) {
                            isCancel = true;
                            mAudioVoiceImg.setImageResource(R.drawable.ic_undo);
                            mAudioNoticeTxt.setText("松手取消");
                        } else {
                            isCancel = false;
                            mAudioVoiceImg.setImageResource(R.drawable.ic_mic_voice);
                            mAudioNoticeTxt.setText("上滑取消");
                        }

                        break;
                    case MotionEvent.ACTION_UP:
                        mAudioRootView.setVisibility(View.GONE);
                        mAudioTimeCount.stop();
                        //如果取消
                        if (isCancel) {
                            mAudioUtils.stopAudioRecordUnSave();
                            Log.d(TAG, "onTouch: 取消保存 isCancel  =" + isCancel);
                        } else if (mCurAudioTime < mMinAudioTime) {
                            ToastUtils.showShortToast("录音时长不能少于：" + mMinAudioTime / 1000 + "s");
                            mAudioUtils.stopAudioRecordUnSave();
                        } else {
                            mAudioUtils.stopAudioRecord();
                            ToastUtils.showShortToast("存储路径为：" + mAudioUtils.getAudioFile().getAbsolutePath());

                            //是否转换成MP3
                            if (isTurnMp3) {
                                turnToMp3();
                            }
                        }

                        //重置时间
                        mCurAudioTime = 0;
                        break;

                }


                return true;
            }
        };
    }

    /**
     * 录音转换成mp3
     */
    private void turnToMp3() {
        AndroidAudioConverter.with(this)
                .setFile(mAudioUtils.getAudioFile())
                .setCallback(new IConvertCallback() {
                    @Override
                    public void onSuccess(File file) {
                        Log.d(TAG, "mp3文件转换成功 " + file.getAbsolutePath());
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d(TAG, "mp3文件转换失败 ");

                    }
                }).setFormat(AudioFormat.MP3)//设置转换的格式
                .convert();
    }

    @Override
    public void initData() {

    }

    @NeedsPermission({Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void audioNeedPermission() {
        Log.d(TAG, "audioNeedPermission: ");
    }


    @OnShowRationale({Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void audioShowWhyPermission(final PermissionRequest request) {
        Log.d(TAG, "audioShowWhyPermission: ");
        CommonDialog commonDialog = new CommonDialog.Builder(this)
                .setContentView(R.layout.dialog_permission_check)
                .setOnClickListener(R.id.permission_ensure, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击确定
                        request.proceed();
                    }
                }).setOnClickListener(R.id.permission_cancel, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //取消
                        request.cancel();
                    }
                }).create();
        commonDialog.show();

    }

    @OnPermissionDenied({Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void audioPermissionDenied() {
        Log.d(TAG, "audioPermissionDenied: ");
    }

    @OnNeverAskAgain({Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void audioPermissionNeverAsk() {
        Log.d(TAG, "audioPermissionNeverAsk: ");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AudioActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

}
