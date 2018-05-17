package com.csx.mytestdemo.drag_edittext;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.csx.mytestdemo.R;

/**
 * @Created by cuishuxiang
 * @date 2018/5/16.
 * @description:
 * 说明:1，点击view移动控件
 *      2，点击右上角图标，编辑
 *      3，点击右下角图标，缩放
 *
 *
 */
public class MyEditText extends EditText implements View.OnTouchListener {
    private static final String TAG = "MyEditText";
    private Paint mPaint;
    private Context mContext;

    private float mCurrentFontSize;

    private ViewGroup.LayoutParams mLayoutParams;
    private ViewGroup.MarginLayoutParams mMarginLayoutParams;//为当前view设置位于父控件的位置

    private float screenWidth, screenHeight;

    private Bitmap scaleBitmap;//右下角缩放的bitmap
    private Bitmap editBitmap;//右上角的编辑bitmap


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

        setBackgroundColor(Color.WHITE);//背景设置白色

        scaleBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_sticker_control);
        editBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_edit_text);

        int paddRight = scaleBitmap.getWidth() > editBitmap.getWidth() ? scaleBitmap.getWidth() : editBitmap.getWidth();
        setPadding(0, 0, paddRight+20, 0);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        mCurrentFontSize = getTextSize();

        Log.d(TAG, "init:屏幕宽高为: screenWidth=" + screenWidth + "  screenHeight = " + screenHeight + " mCurrentFontSize = " + mCurrentFontSize);
        mLayoutParams = getLayoutParams();
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    private boolean isInScaleCircle = false;
    private boolean isInEditCircle = false;
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

                isInScaleCircle = isDownInCircle(downX, downY);
                isInEditCircle = isDownEditCircle(downX, downY);
                Log.d(TAG, "onTouch:按下的点是否在缩放框？ " + isInEditCircle + " 按下的点是否在编辑框：" + isInScaleCircle + "  downX = " + downX + " downY=" + downY + "\nevent.getRawX()=" + event.getRawX() + " event.getRawY()=" + event.getRawY());

                if (isInEditCircle) {
                    v.setFocusable(true);
                    v.setFocusableInTouchMode(true);
                } else {
                    //如果按下，并开始移动，就屏蔽焦点，隐藏键盘（防止拖拽跟点击冲突）
                    v.setFocusable(false);
                    v.setFocusableInTouchMode(false);
                    hideSoftInput(mContext, this);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isInScaleCircle) {//如果是在右下角的圆中，则为缩放
                    float offsetX = event.getRawX() / startRawX;
                    float offsetY = event.getRawY() / startRawY;

                    //判断是放大还是缩小
                    if (offsetX >= 1 && offsetY >= 1) {
                        moveTextSize += 1;
                    } else {
                        moveTextSize -= 1;
                    }

                    if (moveTextSize < 40) {
                        moveTextSize = 40;
                    }
                    Log.d(TAG, "onTouch: offsetX =" + offsetX + " offsetY= " + offsetY+"  moveTextSize = "+moveTextSize);
                    setTextSize(TypedValue.COMPLEX_UNIT_DIP, moveTextSize);

                } else {//平移
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

                v.postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                mCurrentFontSize = moveTextSize;

                //得到此控件于父布局的margin
                int marginLeft = (int) (event.getRawX() - event.getX());
                int marginTop = (int) (event.getRawY() - event.getY());

                //重新设置view的params，防止获取焦点的时候，恢复到原来的位置
                if (mLayoutParams == null) {
                    mLayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    mMarginLayoutParams = new ViewGroup.MarginLayoutParams(mLayoutParams);
                }
                mMarginLayoutParams.setMargins(marginLeft, marginTop, 0, 0);

                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(mMarginLayoutParams);

                setLayoutParams(layoutParams);
                break;
        }

        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        //绘制右侧两个图片
        canvas.drawBitmap(scaleBitmap, width - scaleBitmap.getWidth() - 5, height - scaleBitmap.getHeight() - 5, mPaint);
        canvas.drawBitmap(editBitmap, width - scaleBitmap.getWidth() - 5, 5, mPaint);

        RectF rectF = new RectF(0, 0, width, height);
        canvas.drawRect(rectF, mPaint);
    }

    /**
     * 判断 按下的点，是否在右下角的 缩放框
     *
     * @param downX
     * @param downY
     * @return
     */
    private RectF circleRect;
    private boolean isDownInCircle(float downX, float downY) {
        circleRect = new RectF(getMeasuredWidth() - scaleBitmap.getWidth() - 20, getMeasuredHeight() - scaleBitmap.getHeight()- 20, getMeasuredWidth(), getMeasuredHeight());
        return circleRect.contains(downX, downY);
    }

    /**
     * 判断按下的点，是否为编辑框
     */
    private RectF editRectF;
    private boolean isDownEditCircle(float downX, float downY) {
        editRectF = new RectF(getMeasuredWidth() - editBitmap.getWidth() - 20, 0, getMeasuredWidth(), editBitmap.getHeight() + 20);

        return editRectF.contains(downX, downY);
    }


    /**
     * 隐藏软键盘
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
