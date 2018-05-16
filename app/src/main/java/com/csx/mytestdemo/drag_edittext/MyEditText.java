package com.csx.mytestdemo.drag_edittext;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.hardware.input.InputManager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.csx.mytestdemo.R;

/**
 * @Created by cuishuxiang
 * @date 2018/5/16.
 * @description:
 */
public class MyEditText extends EditText implements View.OnTouchListener {
    private static final String TAG = "MyEditText";
    private Paint mPaint;
    private int radius = 20;

    private Context mContext;

    private float mCurrentFontSize;

    private ViewGroup.LayoutParams mLayoutParams;

    private float screenWidth, screenHeight;

    private Bitmap scaleBitmap;//右下角缩放的bitmap

    public MyEditText(Context context) {
        super(context, null);
        init(context);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        initPaint();
        setOnTouchListener(this);
        setFocusable(true);

        setPadding(0, 0, radius, 0);

        setBackgroundColor(Color.WHITE);

        scaleBitmap= BitmapFactory.decodeResource(getResources(), R.drawable.ic_sticker_control);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        mCurrentFontSize = getTextSize();

        Log.d(TAG, "init:屏幕宽高为: screenWidth=" + screenWidth + "  screenHeight = " + screenHeight + " mCurrentFontSize = " + mCurrentFontSize);
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    private boolean isInCircle = false;
    float downRawX = 0, downRawY = 0;
    private int mViewLeft, mViewTop, mViewRight, mViewBottom;
    float startRawX = 0, startRawY = 0;

    float moveTextSize = mCurrentFontSize;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        float downX, downY;

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                downRawX = event.getRawX();
                downRawY = event.getRawY();

                startRawX = event.getRawX();
                startRawY = event.getRawY();

                isInCircle = isDownInCircle(downX, downY);
                Log.d(TAG, "onTouch:按下的点是否在圆？ " + isInCircle + "  downX = " + downX + " downY=" + downY + "\nevent.getRawX()=" + event.getRawX() + " event.getRawY()=" + event.getRawY());
                if (isInCircle) {
                    mLayoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();

                }
                break;
            case MotionEvent.ACTION_MOVE:
                //如果按下，并开始移动，就屏蔽焦点，隐藏键盘（防止拖拽跟点击冲突）
                v.setFocusable(false);
                v.setFocusableInTouchMode(false);
                hideSoftInput(mContext, this);

                if (isInCircle) {//如果是在右下角的圆中，则为缩放
                    float offsetX = event.getRawX() / startRawX;
                    float offsetY = event.getRawY() / startRawY;
                    Log.d(TAG, "onTouch: offsetX =" + offsetX + " offsetY= " + offsetY);
                    float offset;
                    if (offsetX >= 1 && offsetY >= 1) {
                        offset = offsetX > offsetY ? offsetX : offsetY;
                    }else{
                        offset = offsetX > offsetY ? offsetY : offsetX;
                    }



                    Log.d(TAG, "offset: " + offset);

                    moveTextSize = mCurrentFontSize * offset;
                    setTextSize(TypedValue.COMPLEX_UNIT_DIP, moveTextSize);

                } else {
                    float dx = event.getRawX() - downRawX;
                    float dy = event.getRawY() - downRawY;
                    Log.d(TAG, "onTouch: 移动的 dx= " + dx + "  dy = " + dy);

                    mViewLeft = (int) (v.getLeft() + dx);
                    mViewTop = (int) (v.getTop() + dy);
                    mViewRight = (int) (v.getRight() + dx);
                    mViewBottom = (int) (v.getBottom() + dy);

                    //防止出边界
                    if (mViewLeft <= 0) {
                        mViewLeft = 0;
                        mViewRight = getMeasuredWidth();
                    }
                    if (mViewTop <= 0) {
                        mViewBottom = getMeasuredHeight();
                        mViewTop = 0;
                    }
                    if (mViewRight >= screenWidth) {
                        mViewLeft = (int) (screenWidth - getMeasuredWidth());
                        mViewRight = (int) screenWidth;
                    }
                    if (mViewBottom >= screenHeight) {
                        mViewTop = (int) (screenHeight - getMeasuredHeight());
                        mViewBottom = (int) screenHeight;
                    }


                    Log.d(TAG, "onTouch: mViewLeft= " + mViewLeft + " mViewTop = " + mViewTop + " mViewRight= " + mViewRight + " mViewBottom=" + mViewBottom);
                    v.layout(mViewLeft, mViewTop, mViewRight, mViewBottom);
                }

                downRawX = event.getRawX();
                downRawY = event.getRawY();

//                v.postInvalidate();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);

                mCurrentFontSize = moveTextSize;
                break;
        }

        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        canvas.drawBitmap(scaleBitmap, width - scaleBitmap.getWidth() - 5, height - scaleBitmap.getHeight() - 5, mPaint);

        RectF rectF = new RectF(0, 0, width, height);
        canvas.drawRect(rectF, mPaint);
    }


    /**
     * 判断 按下的点，是否在右下角的 圆中
     *
     * @param downX
     * @param downY
     * @return
     */
    private boolean isDownInCircle(float downX, float downY) {
        RectF circleRect = new RectF(getMeasuredWidth() - radius * 2, getMeasuredHeight() - radius * 2, getMeasuredWidth(), getMeasuredHeight());
        return circleRect.contains(downX, downY);
    }


    /**
     * @param mContext
     * @param view
     */
    private void hideSoftInput(Context mContext, View view) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
