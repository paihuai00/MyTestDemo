package com.csx.mytestdemo.drag_edittext;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * @Created by cuishuxiang
 * @date 2018/5/16.
 * @description:
 */
public class DragEditText extends EditText implements View.OnTouchListener {
    private static final String TAG = "DragEditText";

    private Context mContext;

    private int screenWidth, screenHeight;
    private void getDisplayMetrics() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels - 50;
    }

    private int lastX, lastY;

    private int downX, downY; // 按下View的X，Y坐标
    private int upX, upY; // 放手View的X,Y坐标
    private int rangeDifferenceX, rangeDifferenceY; // 放手和按下X,Y值差
    private int mDistance = 10; // 设定点击事件的移动距离值
    private int mL,mB,mR,mT;//重绘时layout的值

    public DragEditText(Context context) {
        this(context, null);
    }

    public DragEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public DragEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        getDisplayMetrics();
        setOnTouchListener(this);
    }

    /*隐藏键盘*/
    public static void hideSoftInput(Activity mContext, View view) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /*弹出键盘*/
    public static void showSoftInput(Activity mContext, View view) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.showSoftInput(view, 0);
        }
    }

    public interface IOnKeyboardStateChangedListener{
        public void openKeyboard();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getRawX();
                downY = (int) event.getRawY();

                lastX = (int) event.getRawX();// 获取触摸事件触摸位置的原始X坐标
                lastY = (int) event.getRawY();

                Log.d("按下：", downX + "----X轴坐标");
                Log.d("按下：", downY + "----Y轴坐标");
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = (int) event.getRawX() - lastX;
                int dy = (int) event.getRawY() - lastY;

                mL = v.getLeft() + dx;
                mB = v.getBottom() + dy;
                mR = v.getRight() + dx;
                mT = v.getTop() + dy;

                if (mL < 0) {
                    mL = 0;
                    mR = mL + v.getWidth();
                }

                if (mT < 0) {
                    mT = 0;
                    mB = mT + v.getHeight();
                }

                if (mR > screenWidth) {
                    mR = screenWidth;
                    mL = mR - v.getWidth();
                }

                if (mB > screenHeight) {
                    mB = screenHeight;
                    mT = mB - v.getHeight();
                }
                v.layout(mL, mT, mR, mB);
                Log.d("绘制：", "l="+mL+ ";t="+ mT+";r="+mR+";b="+mB);

                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                v.postInvalidate();

                v.setFocusable(false);
                v.setFocusableInTouchMode(false);
                hideSoftInput((Activity)mContext, v);
                break;
            case MotionEvent.ACTION_UP:
                upX = (int) event.getRawX();
                upY = (int) event.getRawY();

                Log.d("离开：", upX + "----X轴坐标");
                Log.d("离开：", upY + "----Y轴坐标");

                rangeDifferenceX = upX - downX;
                rangeDifferenceY = upY - downY;
                if (rangeDifferenceX > 0 && rangeDifferenceX <= mDistance) {
                    if (rangeDifferenceY >= 0 && rangeDifferenceY <= mDistance) {
                        v.setFocusable(true);
                        v.setFocusableInTouchMode(true);
                        Log.d("是否是点击事件：", true + "");

                    } else {
                        if (rangeDifferenceY <= 0 && rangeDifferenceY >= -mDistance) {
                            v.setFocusable(true);
                            v.setFocusableInTouchMode(true);
                            Log.d("是否是点击事件：", true + "");

                        } else {
                            v.setFocusable(false);
                            v.setFocusableInTouchMode(false);
                            Log.d("是否是点击事件：", false + "");
                        }
                    }
                } else {
                    if (rangeDifferenceX <= 0 && rangeDifferenceX >= -mDistance) {
                        v.setFocusable(true);
                        v.setFocusableInTouchMode(true);
                        Log.d("是否是点击事件：", true + "");

                    } else {
                        v.setFocusable(false);
                        v.setFocusableInTouchMode(false);
                        Log.d("是否是点击事件：", false + "");
                    }
                }
                break;
            default:
                break;
        }
        return false;
    }
}
