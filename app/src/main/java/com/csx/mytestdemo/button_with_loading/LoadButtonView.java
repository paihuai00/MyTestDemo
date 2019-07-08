package com.csx.mytestdemo.button_with_loading;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.csx.mytestdemo.R;

/**
 * Date: 2019/7/8
 * create by cuishuxiang
 * description: 带有加载按钮的 TextView
 */
public class LoadButtonView extends DrawableTextView {

    private int curStatus = STATE.IDE;      //当前的状态

    interface STATE {
        int IDE = 0;
        int SHRINKING = 1;
        int LOADING = 2;
        int END_DRAWABLE_SHOWING = 3;
        int RESTORING = 4;
    }

    private Drawable[] mDrawablesSaved;
    private int mDrawablePaddingSaved;
    private CharSequence mTextSaved;
    private boolean mEnableTextInCenterSaved;
    private int[] mRootViewSizeSaved = new int[]{0, 0};

    private boolean disableClickOnLoading;   //Loading中禁用点击
    private boolean enableShrink;            //是否开启收缩动画
    private ValueAnimator mShrinkAnimator;
    private int mShrinkDuration;
    private CircularProgressDrawable mLoadingDrawable;
    private OnLoadingListener mOnLoadingListener;
    private EndDrawable mEndDrawable;
    private int mLoadingSize;
    private int mLoadingPosition;

    private boolean nextShrinkReverse;      //下一步是否是恢复动画
    private boolean isCancel;               //是取消当前动画
    private boolean isFail;


    public LoadButtonView(Context context) {
        super(context);
        init(context, null);
    }

    public LoadButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LoadButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        //getConfig
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LoadingButton);
        enableShrink = array.getBoolean(R.styleable.LoadingButton_enableShrink, true);
        disableClickOnLoading = array.getBoolean(R.styleable.LoadingButton_disableClickOnLoading, true);
        mShrinkDuration = array.getInt(R.styleable.LoadingButton_shrinkDuration, 500);
        int loadingDrawableSize = array.getDimensionPixelSize(R.styleable.LoadingButton_loadingEndDrawableSize, (int) (enableShrink ? getTextSize() * 2 : getTextSize()));
        int loadingDrawableColor = array.getColor(R.styleable.LoadingButton_loadingDrawableColor, getTextColors().getDefaultColor());
        int loadingDrawablePosition = array.getInt(R.styleable.LoadingButton_loadingDrawablePosition, POSITION.START);
        int endCompleteDrawableResId = array.getResourceId(R.styleable.LoadingButton_endCompleteDrawable, -1);
        int endFailDrawableResId = array.getResourceId(R.styleable.LoadingButton_endFailDrawable, -1);
        int endDrawableAppearTime = array.getInt(R.styleable.LoadingButton_endDrawableAppearTime, EndDrawable.DEFAULT_APPEAR_DURATION);
        int endDrawableDuration = array.getInt(R.styleable.LoadingButton_endDrawableDuration, 900);
        array.recycle();

        //initLoadingDrawable
        mLoadingDrawable = new CircularProgressDrawable(context);
        mLoadingDrawable.setColorSchemeColors(loadingDrawableColor);
        mLoadingDrawable.setStrokeWidth(loadingDrawableSize * 0.14f);

        mLoadingSize = loadingDrawableSize;
        mLoadingPosition = loadingDrawablePosition;
        setDrawable(mLoadingPosition, mLoadingDrawable, loadingDrawableSize, loadingDrawableSize);

        //initLoadingDrawable
        if (endCompleteDrawableResId != -1 || endFailDrawableResId != -1) {
            mEndDrawable = new EndDrawable(endCompleteDrawableResId, endFailDrawableResId);
            mEndDrawable.mAppearAnimator.setDuration(endDrawableAppearTime);
            mEndDrawable.setKeepDuration(endDrawableDuration);
        }

        //initShrinkAnimator
        setUpShrinkAnimator();

        //Start|End -> true  Top|Bottom ->false
        setEnableTextInCenter(mLoadingPosition % 2 == 0);

        if (getRootView().isInEditMode()) {
            mLoadingDrawable.setStartEndTrim(0, 0.8f);
        }

    }


    /**
     * 设置收缩动画，主要用来收缩和恢复布局的宽度，动画开始前会保存一些收缩前的参数（文字，其他Drawable等）
     */
    private void setUpShrinkAnimator() {
        mShrinkAnimator = ValueAnimator.ofFloat(0, 1f);
        mShrinkAnimator.setDuration(mShrinkDuration);
        mShrinkAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // y = kx + b
                // b = getRootViewSize()
                // k = getRootViewSize() - getLoadingSize
                getLayoutParams().width = (int) ((getShrinkSize() - mRootViewSizeSaved[0]) * (float) animation.getAnimatedValue() + mRootViewSizeSaved[0]);
                getLayoutParams().height = (int) ((getShrinkSize() - mRootViewSizeSaved[1]) * (float) animation.getAnimatedValue() + mRootViewSizeSaved[1]);
                requestLayout();
            }
        });

        mShrinkAnimator.addListener(new AnimatorListenerAdapter() {

            //onAnimationStart(Animator animation, boolean isReverse) 在7.0测试没有调用fuck
            @Override
            public void onAnimationStart(Animator animation) {
                if (!nextShrinkReverse) {
                    //begin shrink
                    curStatus = STATE.SHRINKING;
                    if (mOnLoadingListener != null) {
                        mOnLoadingListener.onShrinking();
                    }

                    saveStatus();
                    setText("");
                    setCompoundDrawablePadding(0);
                    setCompoundDrawablesRelative(mLoadingDrawable, null, null, null);
                    setEnableTextInCenter(false);

                } else {
                    //begin restore
                    curStatus = STATE.RESTORING;
                    stopLoading();
                    if (mOnLoadingListener != null) {
                        mOnLoadingListener.onRestoring();
                        mOnLoadingListener.onLoadingStop();
                    }

                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (!nextShrinkReverse) {
                    //shrink over
                    curStatus = STATE.LOADING;
                    startLoading();
                    nextShrinkReverse = true;

                } else {
                    //restore over
                    curStatus = STATE.IDE;
                    restoreStatus();
                    endCallbackListener();
                    nextShrinkReverse = false;
                }
            }

        });

    }

    /**
     * 结束时状态的回调，Cancel Fail Complete 三种
     */
    private void endCallbackListener() {
        if (mOnLoadingListener != null) {
            if (isCancel)
                mOnLoadingListener.onCanceled();
            else if (isFail)
                mOnLoadingListener.onFailed();
            else
                mOnLoadingListener.onCompleted();
        }
        isCancel = false;
        isFail = false;
    }


    /**
     * 保存一些即将被改变的数据或状态
     */
    private void saveStatus() {
        mTextSaved = getText();
        mDrawablesSaved = copyDrawables(true);
        mDrawablePaddingSaved = getCompoundDrawablePadding();
        mEnableTextInCenterSaved = isEnableTextInCenter();
    }

    /**
     * 恢复保存的状态
     */
    private void restoreStatus() {
        setText(mTextSaved);
        setCompoundDrawablePadding(mDrawablePaddingSaved);
        setCompoundDrawablesRelative(mDrawablesSaved[POSITION.START], mDrawablesSaved[POSITION.TOP], mDrawablesSaved[POSITION.END], mDrawablesSaved[POSITION.BOTTOM]);
        setEnableTextInCenter(mEnableTextInCenterSaved);
        getLayoutParams().width = mRootViewSizeSaved[0];
        getLayoutParams().height = mRootViewSizeSaved[1];
        requestLayout();

        addOnLayoutChangeListener(new OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                measureTextHeight();
                measureTextWidth();
                removeOnLayoutChangeListener(this);
            }
        });

    }


    /**
     * 如果disableClickOnLoading==true，点击会无效
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            //disable click
            if (disableClickOnLoading && curStatus != STATE.IDE)
                return true;
        }
        return super.onTouchEvent(event);
    }


    /**
     * 开始收缩或恢复
     *
     * @param isReverse true：恢复 ，false：收缩
     * @param lastFrame 是否只显示最后一帧
     */
    private void beginShrinkAnim(boolean isReverse, boolean lastFrame) {
        if (enableShrink) {
            if (mShrinkAnimator.isRunning()) {
                //如果上一个动画还在执行，就结束到最后一帧
                mShrinkAnimator.end();
            }
            nextShrinkReverse = isReverse;
            if (!isReverse) {
                mShrinkAnimator.start();
            } else {
                mShrinkAnimator.reverse();
            }

            if (lastFrame) {
                mShrinkAnimator.end();
            }

        }
    }


    /**
     * 开始加载
     */
    private void startLoading() {
        curStatus = STATE.LOADING;
        if (mOnLoadingListener != null) {
            mOnLoadingListener.onLoadingStart();
        }

        mLoadingDrawable.start();
    }

    /**
     * 停止加载
     */
    private void stopLoading() {
        mLoadingDrawable.stop();
    }

    /**
     * 取消当前所有的动画进程
     *
     * @param withAnim 是否显示恢复动画
     */
    private void cancelAllRunning(boolean withAnim) {
        switch (curStatus) {
            case STATE.SHRINKING:
                beginShrinkAnim(true, !withAnim);
                break;
            case STATE.LOADING:
                if (enableShrink) {
                    beginShrinkAnim(true, !withAnim);
                } else {
                    stopLoading();
                    endCallbackListener();
                    restoreStatus();
                    curStatus = STATE.IDE;
                }
                break;
            case STATE.END_DRAWABLE_SHOWING:
                if (mEndDrawable != null) {
                    mEndDrawable.cancel(withAnim);
                } else {
                    beginShrinkAnim(true, !withAnim);
                }
                break;
            case STATE.RESTORING:
                if (!withAnim)
                    mShrinkAnimator.end();
                else {
                    beginShrinkAnim(true, true);
                }
                break;
        }

    }


    /**
     * 开始加载，默认禁用加载时的点击
     * <p>
     * 步骤：
     * shrink -> startLoading
     */
    public void start() {
        //cancel last loading
        if (curStatus == STATE.SHRINKING || curStatus == STATE.LOADING)
            isCancel = true;
        cancelAllRunning(false);


        if (enableShrink) {
            beginShrinkAnim(false, false);
        } else {
            saveStatus();
            startLoading();
        }
    }

    /**
     * 结束加载
     * <p>
     * 步骤
     * stopLoading -> showEndDrawable -> restore
     *
     * @param isFail 结束时显示失败的Drawable
     */
    private void end(boolean isFail) {
        if (mEndDrawable != null) {
            mEndDrawable.show(isFail);
        } else {
            if (curStatus == STATE.LOADING)
                beginShrinkAnim(true, false);
            else
                beginShrinkAnim(true, true);
        }
    }

    /**
     * 加载完成
     */
    public void complete() {
        end(false);
    }

    /**
     * 加载错误
     */
    public void fail() {
        end(true);
    }


    /**
     * 取消加载，默认显示恢复动画
     */
    public void cancel() {
        cancel(true);
    }

    /**
     * 取消加载
     *
     * @param withAnim 是否显示收缩动画
     */
    public void cancel(boolean withAnim) {
        if (curStatus != STATE.IDE) {
            isCancel = true;
            cancelAllRunning(withAnim);
        }
    }

    /**
     * 设置加载中不可点击
     */
    public LoadButtonView setDisableClickOnLoading(boolean disable) {
        this.disableClickOnLoading = disable;
        return this;
    }

    /**
     * 设置是否显示收缩动画
     */
    public LoadButtonView setEnableShrink(boolean enable) {
        this.enableShrink = enable;
        return this;
    }

    /**
     * 收缩后的尺寸（正方形）
     */
    public int getShrinkSize() {
        return Math.max(Math.min(mRootViewSizeSaved[0], mRootViewSizeSaved[1]), getLoadingEndDrawableSize());
    }

    /**
     * 收缩的Animator，可自行设置参数
     */
    public ValueAnimator getShrinkAnimator() {
        return mShrinkAnimator;
    }

    /**
     * 设置收缩的总时间
     */
    public LoadButtonView setShrinkDuration(long milliseconds) {
        mShrinkAnimator.setDuration(milliseconds);
        return this;
    }

    /**
     * 收缩的总时间
     */
    public int getShrinkDuration() {
        return mShrinkDuration;
    }

    /**
     * 拿到CircularProgressDrawable 可自行设置想要的参数
     *
     * @return CircularProgressDrawable
     */
    public CircularProgressDrawable getLoadingDrawable() {
        return mLoadingDrawable;
    }

    /**
     * 加载时的环形颜色，可设置多个
     *
     * @param colors 颜色组
     */
    public LoadButtonView setLoadingColor(@NonNull @ColorInt int... colors) {
        mLoadingDrawable.setColorSchemeColors(colors);
        return this;
    }

    public LoadButtonView setLoadingStrokeWidth(@Px int size) {
        mLoadingDrawable.setStrokeWidth(size);
        return this;
    }

    /**
     * 设置LoadingDrawable的位置，如果开启收缩动画，则建议放Start或End
     *
     * @param position {@link DrawableTextView.POSITION}
     */
    public LoadButtonView setLoadingPosition(@POSITION int position) {
        boolean enableTextInCenter = position % 2 == 0;
        setEnableTextInCenter(enableTextInCenter);
        mEnableTextInCenterSaved = enableTextInCenter;
        setDrawable(mLoadingPosition, null, 0, 0);
        mLoadingPosition = position;
        setDrawable(position, getLoadingDrawable(), getLoadingEndDrawableSize(), getLoadingEndDrawableSize());
        return this;
    }

    /**
     * 设置LoadingDrawable和EnaDrawable的尺寸
     *
     * @param size Px
     */
    public LoadButtonView setLoadingEndDrawableSize(@Px int size) {
        mLoadingSize = size;
        setDrawable(mLoadingPosition, mLoadingDrawable, size, size);
        return this;
    }


    public int getLoadingEndDrawableSize() {
        return mLoadingSize;
    }


    public LoadButtonView setCompleteDrawable(@DrawableRes int id) {
        if (mEndDrawable == null)
            mEndDrawable = new EndDrawable(id, -1);
        else {
            mEndDrawable.setCompleteDrawable(id);
        }
        return this;
    }

    public LoadButtonView setCompleteDrawable(Drawable drawable) {
        if (mEndDrawable == null)
            mEndDrawable = new EndDrawable(drawable, null);
        else {
            mEndDrawable.setCompleteDrawable(drawable);
        }
        return this;
    }

    public LoadButtonView setFailDrawable(@DrawableRes int id) {
        if (mEndDrawable == null)
            mEndDrawable = new EndDrawable(-1, id);
        else {
            mEndDrawable.setFailDrawable(id);
        }
        return this;
    }

    public LoadButtonView setFailDrawable(Drawable drawable) {
        if (mEndDrawable == null)
            mEndDrawable = new EndDrawable(null, drawable);
        else {
            mEndDrawable.setFailDrawable(drawable);
        }
        return this;
    }

    /**
     * EndDrawable 停留显示的时间
     */
    public LoadButtonView setEndDrawableKeepDuration(long milliseconds) {
        if (mEndDrawable != null)
            mEndDrawable.setKeepDuration(milliseconds);
        return this;
    }

    public long getEndDrawableDuration() {
        if (mEndDrawable != null)
            return mEndDrawable.mKeepDuration;
        return EndDrawable.DEFAULT_APPEAR_DURATION;
    }


    /**
     * CompleteDrawable或FailDrawable 变大出现的时间
     */
    public LoadButtonView setEndDrawableAppearDuration(long milliseconds) {
        if (mEndDrawable != null)
            mEndDrawable.getAppearAnimator().setDuration(milliseconds);
        return this;
    }

    @Nullable
    public ObjectAnimator getEndDrawableAnimator() {
        if (mEndDrawable != null) {
            return mEndDrawable.getAppearAnimator();
        }
        return null;
    }


    @Override
    public void setCompoundDrawablePadding(int pad) {
        super.setCompoundDrawablePadding(pad);
        if (curStatus == STATE.IDE)
            mDrawablePaddingSaved = pad;
    }


    @Override
    public void setText(CharSequence text, BufferType type) {
        if (enableShrink && (curStatus != STATE.IDE)) {
            text = "";
        }
        super.setText(text, type);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (curStatus == STATE.IDE) {
            mRootViewSizeSaved[0] = getWidth();
            mRootViewSizeSaved[1] = getHeight();
        }
    }

    /**
     * 第一次Layoutb
     */
    @Override
    protected void onFirstLayout(int left, int top, int right, int bottom) {
        super.onFirstLayout(left, top, right, bottom);
        saveStatus();


    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mEndDrawable != null)
            mEndDrawable.draw(canvas);
        super.onDraw(canvas);
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public class EndDrawable {
        private static final int DEFAULT_APPEAR_DURATION = 300;
        private Bitmap mCompleteBitmap;
        private Bitmap mFailBitmap;
        private Paint mPaint;
        private Rect mBounds = new Rect();
        private Path mCirclePath;   //圆形裁剪路径
        private ObjectAnimator mAppearAnimator;
        private long mKeepDuration;
        private float animValue;
        int[] offsetTemp = new int[]{0, 0};
        private boolean isShowing;
        private Runnable mRunnable;

        private EndDrawable(@Nullable Drawable completeDrawable, @Nullable Drawable failDrawable) {
            setCompleteDrawable(completeDrawable);
            setFailDrawable(failDrawable);
            init();
        }

        private EndDrawable(@DrawableRes int completeResId, @DrawableRes int failResId) {
            setCompleteDrawable(completeResId);
            setFailDrawable(failResId);
            init();
        }

        private void init() {
            setLayerType(LAYER_TYPE_HARDWARE, null);
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
            mCirclePath = new Path();
            mAppearAnimator = ObjectAnimator.ofFloat(this, "animValue", 1.0f);
            mRunnable = new Runnable() {
                @Override
                public void run() {
                    setAnimValue(0);
                    if (enableShrink)
                        beginShrinkAnim(true, !nextShrinkReverse);
                    else {
                        curStatus = STATE.IDE;
                        restoreStatus();
                        endCallbackListener();
                    }
                    isShowing = false;
                }
            };
            mAppearAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    curStatus = STATE.END_DRAWABLE_SHOWING;
                    if (mOnLoadingListener != null) {
                        mOnLoadingListener.onEndDrawableAppear(!isFail, mEndDrawable);
                    }
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (isShowing) {
                        postDelayed(mRunnable, mKeepDuration);
                    }
                }
            });
        }


        /**
         * 显示EndDrawable
         *
         * @param isFail true:FailDrawable false:CompleteDrawable
         */
        private void show(boolean isFail) {
            //end running Shrinking
            if (mShrinkAnimator.isRunning()) {
                mShrinkAnimator.end();
            }

            //StopLoading
            stopLoading();
            if (!enableShrink && mOnLoadingListener != null) {
                mOnLoadingListener.onLoadingStop();
            }

            //end showing endDrawable
            if (isShowing) {
                cancel(false);
            }
            LoadButtonView.this.isFail = isFail;
            mAppearAnimator.start();
            isShowing = true;

          /*  //if mFailBitmap or mCompleteBitmap is null cancel appearAnim
            if ((isFail && mFailBitmap == null) || (!isFail && mCompleteBitmap == null)) {
                cancel(true);
            }*/
        }

        /**
         * 取消出现动画
         *
         * @param withAnim 是否显示恢复动画
         */
        private void cancel(boolean withAnim) {
            isShowing = false;
            if (mAppearAnimator.isRunning()) {
                mAppearAnimator.end();
            }
            getHandler().removeCallbacks(mRunnable);
            if (enableShrink)
                beginShrinkAnim(true, !withAnim);
            else {
                endCallbackListener();
                restoreStatus();
                curStatus = STATE.IDE;
            }
            setAnimValue(0);
        }

        /**
         * 消失动画，暂不使用
         */
        private void hide() {
            if (isShowing) {
                cancel(false);
            }
            mAppearAnimator.reverse();
            isShowing = true;
        }

        /**
         * 测量EndDrawable需要位移的offsetX和offsetY,(因为EnaDrawable一开始是在左上角开始显示的)
         *
         * @param canvas 当前画布
         * @param bounds LoadingDrawable的显示范围
         * @param pos    EndDrawable的显示位置
         * @return int[0] = offsetX，int[1] = offsetY
         */
        private int[] calcOffset(Canvas canvas, Rect bounds, @POSITION int pos) {
            final int[] offset = offsetTemp;
            offset[0] = canvas.getWidth() / 2;
            offset[1] = canvas.getHeight() / 2;

            switch (pos) {
                case POSITION.START: {
                    offset[0] -= (int) getTextWidth() / 2 + bounds.width() + getCompoundDrawablePadding();
                    if (enableShrink && nextShrinkReverse) {
                        offset[0] += bounds.width() / 2;
                    } else if (!isEnableTextInCenter()) {
                        offset[0] += (bounds.width() + getCompoundDrawablePadding()) / 2;
                    }

                    offset[1] -= bounds.height() / 2;
                    break;
                }
                case POSITION.TOP: {
                    offset[0] -= bounds.width() / 2;
                    offset[1] -= (int) getTextHeight() / 2 + bounds.height() + getCompoundDrawablePadding();
                    if (enableShrink && nextShrinkReverse) {
                        offset[1] += bounds.height() / 2;
                    } else if (!isEnableTextInCenter()) {
                        offset[1] += (bounds.height() + getCompoundDrawablePadding()) / 2;
                    }
                    break;
                }
                case POSITION.END: {
                    offset[0] += (int) getTextWidth() / 2 + getCompoundDrawablePadding();
                    if (enableShrink && nextShrinkReverse) {
                        offset[0] -= bounds.width() / 2;
                    } else if (!isEnableTextInCenter()) {
                        offset[0] -= (bounds.width() + getCompoundDrawablePadding()) / 2;
                    }
                    offset[1] -= bounds.height() / 2;
                    break;
                }
                case POSITION.BOTTOM: {
                    offset[0] -= bounds.width() / 2;
                    offset[1] += (int) getTextHeight() / 2 + getCompoundDrawablePadding();
                    if (enableShrink && nextShrinkReverse) {
                        offset[1] -= bounds.height() / 2;
                    } else if (!isEnableTextInCenter()) {
                        offset[1] -= (bounds.height() + getCompoundDrawablePadding()) / 2;
                    }
                    break;
                }
            }
            return offset;
        }

        /**
         * 绘制
         * <p>
         * 步骤:
         * 将画布平移到LoadingDrawable的位置 -> 裁剪出一个圆形画布（由小到大）-> 在裁剪后的绘制图形
         * ->随animValue值画布逐渐变大，实现出现的效果
         */
        private void draw(Canvas canvas) {
            if (getAnimValue() > 0 && mLoadingDrawable != null) {
                final Bitmap targetBitMap = isFail ? mFailBitmap : mCompleteBitmap;
                if (targetBitMap != null) {
                    final Rect bounds = mLoadingDrawable.getBounds();
                    mBounds.right = bounds.width();
                    mBounds.bottom = bounds.height();

                    final int[] offsets = calcOffset(canvas, mBounds, mLoadingPosition);
                    canvas.save();
                    canvas.translate(offsets[0], offsets[1]);
                    mCirclePath.reset();
                    mCirclePath.addCircle(mBounds.centerX(), mBounds.centerY(),
                            ((getLoadingEndDrawableSize() >> 1) * 1.5f) * animValue, Path.Direction.CW);
                    canvas.clipPath(mCirclePath);
                    canvas.drawBitmap(targetBitMap, null, mBounds, mPaint);
                    canvas.restore();
                }
            }
        }

        private void setAnimValue(float animValue) {
            this.animValue = animValue;
            invalidate();
        }

        private float getAnimValue() {
            return animValue;
        }

        /**
         * EndDrawable的Animator
         *
         * @return ObjectAnimator
         */
        public ObjectAnimator getAppearAnimator() {
            return mAppearAnimator;
        }

        /**
         * EndDrawable的停留时间
         *
         * @param keepDuration millionMs
         */
        public void setKeepDuration(long keepDuration) {
            this.mKeepDuration = keepDuration;
        }


        public void setCompleteDrawable(Drawable drawable) {
            mCompleteBitmap = getBitmap(drawable);
        }

        public void setCompleteDrawable(@DrawableRes int id) {
            if (id != -1) {
                Drawable drawable = ContextCompat.getDrawable(getContext(), id);
                mCompleteBitmap = getBitmap(drawable);
            }
        }

        public void setFailDrawable(@DrawableRes int id) {
            if (id != -1) {
                Drawable failDrawable = ContextCompat.getDrawable(getContext(), id);
                mFailBitmap = getBitmap(failDrawable);
            }
        }

        public void setFailDrawable(Drawable drawable) {
            mCompleteBitmap = getBitmap(drawable);
        }

    }

    @Nullable
    private Bitmap getBitmap(Drawable drawable) {
        if (drawable != null) {
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        }
        return null;
    }

    public interface OnLoadingListener {
        void onLoadingStart();

        void onLoadingStop();

        void onShrinking();

        void onRestoring();

        void onEndDrawableAppear(boolean isComplete, EndDrawable endDrawable);

        void onCompleted();

        void onFailed();

        void onCanceled();
    }


    public static class OnLoadingListenerAdapter implements OnLoadingListener {

        @Override
        public void onShrinking() {

        }


        @Override
        public void onLoadingStart() {

        }

        @Override
        public void onLoadingStop() {


        }

        @Override
        public void onEndDrawableAppear(boolean isComplete, EndDrawable endDrawable) {

        }

        @Override
        public void onRestoring() {

        }

        @Override
        public void onCompleted() {

        }

        @Override
        public void onFailed() {

        }

        @Override
        public void onCanceled() {

        }


    }

    public LoadButtonView setOnLoadingListener(OnLoadingListener onLoadingListener) {
        mOnLoadingListener = onLoadingListener;
        return this;
    }

}
