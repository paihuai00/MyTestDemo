package com.csx.mytestdemo.switch_view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * Create by cuishuxiang
 *
 * @date: on 2018/9/27
 * @description:
 */
public class SwitchView extends View {
    private static final String TAG = "SwitchView";
    //1,绘制背景
    private RectF mBgRectF;

    //2，内矩形
    private RectF mInnerRectF;
    private String leftString = "女士";
    private String riughtString = "男士";


    private int width = 200;
    private int height = 50;
    private int textSize = 25;
    private int radius = 60;//圆角

    private Paint mBgPaint;//背景画笔
    private int bgColor = Color.parseColor("#f3f3f3");//灰色
    private Paint mTextPaint;//字体画笔
    private int leftTextColor = Color.WHITE;
    private int rightTextColor = Color.GRAY;

    private Paint.FontMetrics mFontMetrics;
    private int baseLineY;

    private Paint mInnerPaint;//内部矩形画笔
    private int boundsWidth;
    private LinearGradient mLinearGradient;
    private int leftStartColor;
    private int leftEndColor;
    private int rightStartColor;
    private int rightEndColor;

    private int mTouchSlop;
    private int mClickTimeout;

    //渐变色计算类
    private ArgbEvaluator argbEvaluator;

    private ValueAnimator mProcessAnimator;//动画
    private int duration = 250;//动画持续时间

    public SwitchView(Context context) {
        this(context, null);
    }

    public SwitchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwitchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        width = dp2px(context, width);
        height = dp2px(context, height);
        textSize = dp2px(context, textSize);
        radius = dp2px(context, radius);
        boundsWidth = width / 2;//滑块宽度为总宽度的1/2

        initPaint(context);

        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mClickTimeout = ViewConfiguration.getPressedStateDuration() + ViewConfiguration.getTapTimeout();

        mBgRectF = new RectF(0, 0, width, height);
        mInnerRectF = new RectF(0, 0, boundsWidth, height);

    }

    private void initPaint(Context context) {
        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setColor(bgColor);
        mBgPaint.setDither(true);
        mBgPaint.setStyle(Paint.Style.FILL);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setDither(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(textSize);
        mFontMetrics = mTextPaint.getFontMetrics();
        //计算基线
        baseLineY = (int) ((-mFontMetrics.top + mFontMetrics.bottom) / 2 - mFontMetrics.bottom + height / 2);

        leftStartColor = Color.parseColor("#ff719e");
        leftEndColor = Color.parseColor("#ffae9b");
        rightStartColor = Color.parseColor("#55a8ff");
        rightEndColor = Color.parseColor("#8998ff");

        mInnerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mInnerPaint.setDither(true);
        mInnerPaint.setStyle(Paint.Style.FILL);
        mLinearGradient = new LinearGradient(0, 0, boundsWidth, height, leftStartColor, leftEndColor, Shader.TileMode.REPEAT);
        mInnerPaint.setShader(mLinearGradient);

        argbEvaluator = new ArgbEvaluator();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //1,绘制背景
        canvas.drawRoundRect(mBgRectF, radius, radius, mBgPaint);

        //2,绘制内举行
        canvas.drawRoundRect(mInnerRectF, radius, radius, mInnerPaint);

        //3，绘制文字
        mTextPaint.setColor(leftTextColor);
        canvas.drawText(leftString, width / 4, baseLineY, mTextPaint);
        mTextPaint.setColor(rightTextColor);
        canvas.drawText(riughtString, width * 3 / 4, baseLineY, mTextPaint);
    }

    private float process;
    private float lastX;
    private float downX;
    private float downY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float deltaX = event.getX() - downX;
        float deltaY = event.getY() - downY;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                lastX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                setProcess(process + (-lastX + event.getX()) / boundsWidth);
                //这里比较x轴方向的滑动 和y轴方向的滑动 如果y轴大于x轴方向的滑动 事件就不在往下传递
                if ((Math.abs(deltaX) > mTouchSlop / 2 || Math.abs(deltaY) > mTouchSlop / 2)) {
                    if (Math.abs(deltaY) > Math.abs(deltaX)) {
                        return false;
                    }
                }
                lastX = event.getX();
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                //计算从手指触摸到手指抬起时的时间
                float time = event.getEventTime() - event.getDownTime();
                //如果x轴和y轴滑动距离小于系统所能识别的最小距离 切从手指按下到抬起时间 小于系统默认的点击事件触发的时间  整个行为将被视为触发点击事件
                if (Math.abs(deltaX) < mTouchSlop &&
                        Math.abs(deltaY) < mTouchSlop &&
                        time < mClickTimeout) {
                    //获取事件触发的x轴区域 主要用于区分是左边还是右边
                    float clickX = event.getX();

                    if (clickX < boundsWidth) {
                        //左侧
                        if (process == 0f)
                            return false;
                        else
                            updateAnimator(false);
                    } else {
                        //右侧
                        if (process == 1f)
                            return false;
                        else
                            updateAnimator(true);
                    }

                    return false;
                } else {
                    updateAnimator(process > 0.5f ? true : false);
                }

                break;
        }

        return true;
    }


    private void setProcess(float percent) {
        if (percent > 1)
            percent = 1;
        if (percent < 0)
            percent = 0;
        process = percent;
        mInnerRectF.left = boundsWidth * percent;
        mInnerRectF.right = boundsWidth * (1 + percent);

        //设置渐变
        updatePaintColor(percent);

        Log.e(TAG, "setProcess: percent = " + percent + "  mInnerRectF.left = " + mInnerRectF.left + " mInnerRectF.right = " + mInnerRectF.right);
        invalidate();
    }

    //滑块移动的时候，渐变色
    private void updatePaintColor(float percent) {
        int startColor;
        int endColor;
        if (percent > 0.5f) {
            leftTextColor = Color.GRAY;
            rightTextColor = Color.WHITE;
        } else {
            leftTextColor = Color.WHITE;
            rightTextColor = Color.GRAY;
        }

        startColor = (int) argbEvaluator.evaluate(process, leftStartColor, rightStartColor);
        endColor = (int) argbEvaluator.evaluate(process, leftEndColor, rightEndColor);
        LinearGradient mLinearGradient = new LinearGradient(0, 0, boundsWidth, height, startColor, endColor, Shader.TileMode.REPEAT);
        mInnerPaint.setShader(mLinearGradient);
    }

    /**
     * @param isRight 滑块是否在右侧
     */
    private void updateAnimator(boolean isRight) {
        if (mProcessAnimator == null)
            mProcessAnimator = new ValueAnimator();

        if (mProcessAnimator.isRunning()) {
            mProcessAnimator.cancel();
            mProcessAnimator.removeAllUpdateListeners();
        }

        mProcessAnimator.setDuration(duration);

        if (isRight)
            mProcessAnimator.setFloatValues(process, 1f);
        else
            mProcessAnimator.setFloatValues(process, 0f);

        mProcessAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                process = (float) animation.getAnimatedValue();
                updatePaintColor(process);
                mInnerRectF.left = boundsWidth * process;
                mInnerRectF.right = boundsWidth * (1 + process);

                postInvalidate();
            }
        });
        mProcessAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (mOnChecklistenter != null)
                    mOnChecklistenter.onBoundCheck(process < 0.5f ? true : false);
            }
        });
        mProcessAnimator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int dp2px(Context context, int dp) {
        return (int) (context.getResources().getDisplayMetrics().density * dp + 0.5f);
    }

    private OnCheckListenter mOnChecklistenter;

    public void setOnCheckListenter(OnCheckListenter onChecklistenter) {
        this.mOnChecklistenter = onChecklistenter;
    }

    public interface OnCheckListenter {
        void onBoundCheck(boolean isLeft);
    }
}
