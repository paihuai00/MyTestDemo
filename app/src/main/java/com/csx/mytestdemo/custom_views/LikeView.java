package com.csx.mytestdemo.custom_views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import com.csx.mytestdemo.R;

/**
 * Author: cuishuxiang
 * Date: 2020/6/18 14:14
 * Description: 点赞动画view
 */
public class LikeView extends View {
    private static final String TAG = "LikeView";

    private Paint mBitmapPaint;


    private Bitmap mCurBitmap;

    private Bitmap mSelectBitmap;
    private Bitmap mUnSelectBitmap;
    private Bitmap mStarBitmap;


    private boolean isNeedTranslate = false;

    public LikeView(Context context) {
        this(context, null);
    }

    public LikeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LikeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);

        /**
         * 设置点击事件
         */
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isNeedTranslate = true;
                postInvalidate();
            }
        });
    }

    /**
     * 初始化
     */
    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
        mBitmapPaint.setColor(Color.RED);
        mSelectBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_like_selected);
        mUnSelectBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_like_unselected);
        mStarBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_like_selected_shining);


        //默认bitmap为未选择
        mCurBitmap = mUnSelectBitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBitmap(canvas);
    }

    private void drawBitmap(Canvas canvas) {
        //计算view绘制的top left 点
        int left = canvas.getWidth() / 2 - mCurBitmap.getWidth() / 2;
        int top = canvas.getHeight() / 2 - mCurBitmap.getHeight() / 2;

        canvas.drawBitmap(mCurBitmap, left, top, mBitmapPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }



}
