package com.csx.mytestdemo.custom_views;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.IntRange;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;


/**
 * Author: cuishuxiang
 * Date: 2020/6/23 14:45
 * Description: 间隔色块的 progressbar3
 * 
 * 
 * 设置进度{@link ColorProgress#setCurProgressPercent(int)}
 *        {@link ColorProgress#setCurProgressPercentWithAnim(int)}}
 */
public class ColorProgress extends View {

    private Paint paint;
    private int mWidth;
    private int mHeight;
    //菱形色块的宽度
    private int blockWidth = 30;

    //当前进度
    private float progressLength = 1f;

    //双螺旋色块图片的偏移量
    private float offset = 0f;

    //两个菱形色块组的数量
    int pairCount = 0;

    //圆角
    private float[] corners = new float[8];

    //进度条区域
    Bitmap areaBp = null;
    //双螺旋区域
    Bitmap colorsBp = null;

    //双螺旋图片是否绘制成功
    boolean isColorsBP = false;

    private int bgColor = Color.parseColor("#F3F7F8");
    private int progressColor = Color.parseColor("#7263ff");    //进度条颜色一
    private int progressColor2 = Color.parseColor("#674EFF");  //进度条颜色二

    private int defaultHeight = 10;//默认高度

    private int curProgressPercent = -1;//进度当前百分比

    PorterDuffXfermode porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

    private boolean isNeedAnim = false;//是否需要动画

    //执行的动画
    private AnimatorSet mAnimatorSet;
    private ValueAnimator mAlternateAnim;//交替动画
    private int mDuration = 4000;//间隔越小，动画执行越快


    private Path mProgressPath;
    private Path mBgPath;


    public ColorProgress(Context context) {
        this(context, null);
    }

    public ColorProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
//        paint.setStrokeWidth(1f);
        //关闭硬件加速，部分手机使用  Xfermode 会不显示
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    private int getMeasurement(int measureSpec, int defaultSize) {
        int measureSize = MeasureSpec.getSize(measureSpec);
        int measureMode = MeasureSpec.getMode(measureSpec);

        int result = 0;
        switch (measureMode) {
            case MeasureSpec.EXACTLY:
                result = measureSize;
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(measureSize, defaultSize);
                break;
            case MeasureSpec.UNSPECIFIED:
                result = defaultSize;
                break;
        }
        return result;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mHeight = getHeight();
        mWidth = getWidth();
        corners[0] = mHeight / 2;
        corners[1] = mHeight / 2;
        corners[2] = mHeight / 2;
        corners[3] = mHeight / 2;
        corners[4] = mHeight / 2;
        corners[5] = mHeight / 2;
        corners[6] = mHeight / 2;
        corners[7] = mHeight / 2;

        //两个菱形色块组成一组双螺旋色块,计算控件长度能花多少个双螺旋色块
        pairCount = (mWidth) / (blockWidth * 2);
        colorsBp = Bitmap.createBitmap(mWidth * 12, mHeight, Bitmap.Config.ARGB_8888);

        isColorsBP = false;

        /**
         * 如果当前百分比不为 - 1，说明是静态的进度条
         */
        if (curProgressPercent != -1)
            progressLength = mWidth * curProgressPercent / 100;

        //判断是否需要动画
        if (isNeedAnim) startAnim();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //设置默认控件的宽高
        float defaultWidth = dp2px(getContext(), 200);
        float defaultHeight = dp2px(getContext(), this.defaultHeight);

        int width = getMeasurement(widthMeasureSpec, (int) defaultWidth);
        int height = getMeasurement(heightMeasureSpec, (int) defaultHeight);

        setMeasuredDimension(width, height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //防止需要动画，没开启的bug
        if (isNeedAnim && mAlternateAnim != null && !mAlternateAnim.isRunning()) {
            mAlternateAnim.start();
        }

        drawBg(canvas);

        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);

        //进度条进度区域
        if (mProgressPath == null) mProgressPath = new Path();
        mProgressPath.reset();
        RectF progressRect = new RectF(0f, 0f, progressLength, mHeight);
        mProgressPath.addRoundRect(progressRect, corners, Path.Direction.CW);
        areaBp = Bitmap.createBitmap((int) progressLength <= 0 ? 1 : (int) progressLength, mHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas1 = new Canvas(areaBp);
        canvas1.drawPath(mProgressPath, paint);


        drawProgressWithColor(canvas);


        canvas.drawBitmap(areaBp, -1, 0, paint);
        paint.setXfermode(porterDuffXfermode);
        canvas.drawBitmap(colorsBp, offset, 0, paint);
        paint.setXfermode(null);

        canvas.restoreToCount(layerId);
    }

    /**
     * 绘制不同色块
     *
     * @param canvas
     */
    private void drawProgressWithColor(Canvas canvas) {
        if (!isColorsBP) {
            //画双螺旋色块,从进度条控件的负一倍坐标起始点开始画双螺旋色块，画两倍进度条控件长度的双螺旋，这样是方便平移这个双螺旋动画。
            Path colorPath1 = new Path();
            Path colorPath2 = new Path();
            int x = -blockWidth * 2;
            int y = mHeight;
            Canvas canvas2 = new Canvas(colorsBp);
            for (int i = -pairCount; i < (pairCount + 1) * 2; i++) {
                colorPath1.reset();
                colorPath1.moveTo(x, y);
                colorPath1.lineTo(x + blockWidth, 0);
                colorPath1.lineTo(x + blockWidth * 2, 0);
                colorPath1.lineTo(x + blockWidth, y);
                colorPath1.lineTo(x, y);
                colorPath1.close();
                paint.setColor(progressColor);
                canvas2.drawPath(colorPath1, paint);


                x += blockWidth-2;//坐标移动小一像素，两个色块中会有白线
                colorPath2.reset();
                colorPath2.moveTo(x, y);
                colorPath2.lineTo(x + blockWidth, 0);
                colorPath2.lineTo(x + blockWidth * 2, 0);
                colorPath2.lineTo(x + blockWidth, y);
                colorPath2.lineTo(x, y);
                colorPath2.close();
                paint.setColor(progressColor2);
                canvas2.drawPath(colorPath2, paint);
                x += blockWidth-2;
            }
            isColorsBP = true;
        }

    }

    /**
     * 绘制背景
     *
     * @param canvas
     */
    private void drawBg(Canvas canvas) {
        //画背景 , 裁剪
        if (mBgPath == null) mBgPath = new Path();
        mBgPath.reset();
        mBgPath.addRoundRect(new RectF(0, 0, mWidth, mHeight), corners, Path.Direction.CW);
        canvas.clipPath(mBgPath);
        canvas.drawColor(bgColor);//绘制背景颜色
    }

    /**
     * 开启动画
     */
    public void startAnim() {
//        postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                //进度条 0 - mWidth 动画
//                ValueAnimator valueAnimator1 = ValueAnimator.ofFloat(1f, mWidth);
//                valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animation) {
//                        progressLength = (float) animation.getAnimatedValue();
//
//                        if (progressLength > mWidth * curProgressPercent / 100) {
//                            progressLength = mWidth * curProgressPercent / 100;
//                        }
//
//                        invalidate();
//                    }
//                });
//
//                valueAnimator1.setDuration(mDuration / 3);
//                valueAnimator1.setInterpolator(new LinearInterpolator());
//
//                ValueAnimator valueAnimator2 = ValueAnimator.ofFloat(-mWidth, 0);
//                valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animation) {
//                        offset = (float) animation.getAnimatedValue();
//                        invalidate();
//                    }
//                });
//                valueAnimator2.setDuration(mDuration);
//                valueAnimator2.setRepeatMode(ValueAnimator.RESTART);
//                valueAnimator2.setRepeatCount(ValueAnimator.INFINITE);
//                valueAnimator2.setInterpolator(new LinearInterpolator());
//
//                mAnimatorSet = new AnimatorSet();
//                mAnimatorSet.playTogether(valueAnimator1, valueAnimator2);
//                mAnimatorSet.start();
//            }
//        }, 500);

        //交替动画
        if (mAlternateAnim == null) {
            mAlternateAnim = ValueAnimator.ofFloat(-mWidth, 0);
            mAlternateAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    offset = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            mAlternateAnim.setDuration(mDuration);
            mAlternateAnim.setRepeatMode(ValueAnimator.RESTART);
            mAlternateAnim.setRepeatCount(ValueAnimator.INFINITE);
            mAlternateAnim.setInterpolator(new LinearInterpolator());
        }

        if (!mAlternateAnim.isRunning())mAlternateAnim.start();
    }

    /**
     * 动画回收
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        release();//资源释放
    }

    /**
     * 设置当前进度百分比 - 无动画
     *
     * @param curProgressPercent
     */
    public void setCurProgressPercent(@IntRange(from = 0, to = 100) int curProgressPercent) {
        this.curProgressPercent = curProgressPercent;
        isNeedAnim = false;
        progressLength = mWidth * curProgressPercent / 100;
        postInvalidate();
    }

    /**
     * 设置当前进度百分比 - 动画
     *
     * @param curProgressPercent
     */
    public void setCurProgressPercentWithAnim(@IntRange(from = 0, to = 100) int curProgressPercent) {
        if (curProgressPercent < 0) curProgressPercent = 0;
        if (curProgressPercent > 100) curProgressPercent = 100;
        this.curProgressPercent = curProgressPercent;
        progressLength = mWidth * curProgressPercent / 100;
        isNeedAnim = true;
        postInvalidate();
    }


    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }

    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    public int getProgressColor() {
        return progressColor;
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
    }

    public int getProgressColor2() {
        return progressColor2;
    }

    public void setProgressColor2(int progressColor2) {
        this.progressColor2 = progressColor2;
    }

    private float dp2px(Context context, int dp) {
        float result = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
        return result;
    }

    /**
     * 资源释放
     */
    public void release() {
        isNeedAnim = false;
        if (mAnimatorSet != null && mAnimatorSet.isRunning()) {
            mAlternateAnim.removeAllUpdateListeners();
            mAnimatorSet.cancel();
        }
        if (mAlternateAnim != null && mAlternateAnim.isRunning()) {
            mAlternateAnim.removeAllUpdateListeners();
            mAlternateAnim.cancel();
        }
    }
}