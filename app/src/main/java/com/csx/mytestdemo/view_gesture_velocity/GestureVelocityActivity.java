package com.csx.mytestdemo.view_gesture_velocity;

import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.R;

import butterknife.BindView;

/**
 * @Created by cuishuxiang
 * @date 2018/2/28.
 * <p>
 * 测试  view 中，手势/速度demo
 */

public class GestureVelocityActivity extends BaseActivity
        implements GestureVelocityView.ReturnEventLog,
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {
    private static final String TAG = "GestureVelocityActivity";
    @BindView(R.id.gv_show_info)
    TextView mGvShowInfo;
    @BindView(R.id.gv_view)
    GestureVelocityView mGvView;
    @BindView(R.id.gv_scroll)
    ScrollView mGvScroll;

    private int indexInfo = 0;

    private StringBuilder mStringBuilder = new StringBuilder();

    //-----------------手势相关-----------------------
    private GestureDetector mGestureDetector;

    @Override
    public int getLayoutId() {
        return R.layout.activity_gesture_velocity;
    }

    @Override
    public void initView() {
        mGvView.setReturnEventLog(this);

        mGestureDetector = new GestureDetector(this, this);
        //检测双击
        mGestureDetector.setOnDoubleTapListener(this);
        //为了解决，长按之后，无法拖动的bug
        mGestureDetector.setIsLongpressEnabled(false);

        mGvShowInfo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //接管TextView的OnTouchEvent方法，检测手势
                mGestureDetector.onTouchEvent(event);
                return true;
            }
        });


        mGvView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("GestureVelocityView", "OnTouchListener  -> " + event.getAction());
                return false;
            }
        });

//        mGvView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("GestureVelocityView", "----> onClick: ");
//            }
//        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void eventLog(String eventInfo) {

        if (!TextUtils.isEmpty(eventInfo)) {
            mStringBuilder.append(eventInfo + "\n\n");
            mGvShowInfo.setText(mStringBuilder);

            mGvScroll.fullScroll(ScrollView.FOCUS_DOWN);//滚动到底部
        }

    }


    //-----------------手势相关-----------------------

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Log.d(TAG, "onSingleTapConfirmed: ");
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.d(TAG, "onDoubleTap: ");
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        Log.d(TAG, "onDoubleTapEvent: ");
        return true;
    }

    //---------------OnGestureListener--------------------------------
    @Override
    public boolean onDown(MotionEvent e) {
        Log.d(TAG, "onDown: ");
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.d(TAG, "onShowPress: ");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.d(TAG, "onSingleTapUp: ");
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.d(TAG, "onScroll: ");
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.d(TAG, "onLongPress: t");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d(TAG, "onFling: ");
        return true;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent: " + event.getAction());
        return super.onTouchEvent(event);
    }

}
