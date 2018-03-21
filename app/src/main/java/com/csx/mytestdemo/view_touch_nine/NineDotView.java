package com.csx.mytestdemo.view_touch_nine;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @Created by cuishuxiang
 * @date 2018/3/5.
 * @description: 九宫格解锁View(Touch事件) 外圈 + 内部实心圆圈，带箭头
 *
 */

public class NineDotView extends View{
    private static final String TAG = "NineDotView";

    private String NORMAL_COLOR = "#408ce2";//普通蓝色

    //首次初始化标识符
    private boolean mIsFirstInit = false;

    public NineDotView(Context context) {
        this(context,null);
    }

    public NineDotView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NineDotView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {

    }

    /**
     * 初始化画笔
     * 1，三个点的画笔，默认圆圈，选中圆圈，错误圆圈
     * 2，连线的画笔
     * 3，连线的箭头
     */
    private void initPaint() {

    }


    /**
     * 点
     * centerX ：中心点X坐标
     * centerY ：中心店Y坐标
     * index   ：位置
     */
     class Dot {
        private float centerX,centerY;
        private int index;

        public Dot(float centerX, float centerY, int index) {
            this.centerX = centerX;
            this.centerY = centerY;
            this.index = index;
        }
    }


}
