package com.csx.mytestdemo.view_gesture_velocity;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

/**
 * @Created by cuishuxiang
 * @date 2018/2/28.
 *
 * 测试 gesture velocity
 */

public class GestureVelocityView extends View {
    private static final String TAG = "GestureVelocityView";

    public ReturnEventLog mReturnEventLog;

    public void setReturnEventLog(ReturnEventLog mReturnEventLog) {
        this.mReturnEventLog = mReturnEventLog;
    }

    public GestureVelocityView(Context context) {
        this(context, null);
    }

    public GestureVelocityView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GestureVelocityView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d(TAG, "dispatchTouchEvent: ----> " + event.getAction());
        return super.dispatchTouchEvent(event);
    }


        @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent: ----> " + event.getAction());
        int action = event.getAction();

        calculateVelocity(event);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                printLogs("MotionEvent.ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                printLogs("MotionEvent.ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                printLogs("MotionEvent.ACTION_UP");
//                if (velocityTracker != null) {
//                    velocityTracker.clear();
//                    velocityTracker.recycle();
//                }
                break;
        }
        return true;
    }

    /**
     * 计算速度，需与 event 绑定
     * @param event
     */
    private void calculateVelocity(MotionEvent event) {
        //手指滑动速度
        VelocityTracker velocityTracker = VelocityTracker.obtain();
        //event绑定
        velocityTracker.addMovement(event);

        /**
         * 首先调用该方法 计算速度 时间间隔为（1000ms）；
         * 例如：在1000ms间隔内，滑动100px
         * 速度就为100
         *
         * 速度 = (终点  -   起点) / 时间间隔
         */
        velocityTracker.computeCurrentVelocity(1000);

        //水平速度
        float xVelocity = velocityTracker.getXVelocity();
        //竖直方向速度
        float yVelocity = velocityTracker.getXVelocity();

        //记得回收
        //velocityTracker.clear();
        //velocityTracker.recycle();

        printLogs("水平速度为：" + xVelocity + "\n垂直速度为:" + yVelocity);
    }

    /**
     * 将log，传递出去
     * @param info
     */
    private void printLogs(String info) {
        if (mReturnEventLog ==null) return;

        mReturnEventLog.eventLog(info);
    }

    public interface ReturnEventLog {
        void eventLog(String eventInfo);
    }

}
