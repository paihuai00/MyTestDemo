package com.csx.mytestdemo.connect_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * @Created by cuishuxiang
 * @date 2018/4/16.
 * @description: 连线题
 */

public class ConnectView extends View {
    private static final String TAG = ConnectView.class.getSimpleName();
    private List<String> questionList;
    private List<String> answerList;

    private Paint mTextPaint;//绘制文字的画笔
    private Paint.FontMetrics fontMetrics;//用于计算基线
    private int mTextColor = Color.BLACK;
    private int mTextSize = 30;
    private List<Rect> mQuestionRectList;//保存 问题text 的Rect
    private List<Rect> mAnswerRectList;//  保存 答案text 的Rect

    private Paint mLinePaint;//绘制连线的画笔
    private int mLineColor = Color.RED;
    private int mLineSize = 5;


    private float moveX,moveY;//绘制移动的时候的线

    public ConnectView(Context context) {
        this(context, null);
    }

    public ConnectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ConnectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initDate();

        initView(context);
    }

    /**
     * 初始化画笔相关
     */
    private void initView(Context context) {
        //绘制文字的view
        if (mTextPaint == null) {
            mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mTextPaint.setColor(mTextColor);//文字颜色 默认黑色
            mTextPaint.setTextSize(dp2px(context, mTextSize));
            mTextPaint.setAntiAlias(true);
            mTextPaint.setTextAlign(Paint.Align.LEFT);//左侧开始绘制文字

            //设置完size属性之后调用
            fontMetrics = mTextPaint.getFontMetrics();
        }

        if (mLinePaint == null) {
            mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mLinePaint.setColor(mLineColor);//文字颜色 默认黑色
            mLinePaint.setAntiAlias(true);
            mLinePaint.setStrokeWidth(dp2px(context, mLineSize));
            mLinePaint.setStyle(Paint.Style.STROKE);
        }

    }

    /**
     * 初始化数据
     */
    private void initDate() {
        //问题
        questionList = new ArrayList<>();
        questionList.add("问题1:1+1=？？");
        questionList.add("问题1:1+1=？？");
        questionList.add("问题1:1+1=？？");
        questionList.add("问题1:1+1=？？");

        mQuestionRectList = new ArrayList<>();

        //答案
        answerList = new ArrayList<>();
        answerList.add("答案为：2");
        answerList.add("答案为：2");
        answerList.add("答案为：2");
        answerList.add("答案为：2");

        mAnswerRectList = new ArrayList<>();
    }

    /**
     * 绘制
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.GRAY);

        drawText(canvas);


        drawLines(canvas);

    }

    /**
     * 绘制 连线
     * @param canvas
     */
    private void drawLines(Canvas canvas) {
        Log.d(TAG, "drawLines: moveX = " + moveX + "moveY" + moveY);
        canvas.drawLine(0, 0, moveX, moveY, mLinePaint);
    }

    /**
     * 绘制题目 / 答案
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        Log.d(TAG, "drawText: getWidth()= " + getWidth() + "  getHeight() = " + getHeight());


        //绘制文字
        if (questionList == null || questionList.size() == 0) {
            Log.d(TAG, "drawText: questionList==null，传入的题目List有问题！");
            return;
        }

        //文字从左侧开始绘制
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        for (int i = 0; i < questionList.size(); i++) {

            float baseLineY = calculateBaseLineY(questionList.get(i), 0);

            //计算位置
            float currentTextHeight = (fontMetrics.bottom - fontMetrics.top) * 2 * i;

            //重新计算 文字矩形的位置
            mQuestionRectList.get(i).offset(0, (int) (currentTextHeight + baseLineY));

            canvas.drawText(questionList.get(0), 0, baseLineY + currentTextHeight, mTextPaint);
        }

        if (answerList == null || answerList.size() == 0) {
            Log.d(TAG, "drawText: answerList == null,传入答案List有误");
            return;
        }
        //从右侧开始绘制
        mTextPaint.setTextAlign(Paint.Align.RIGHT);
        for (int i = 0; i < answerList.size(); i++) {
            float baseLineY = calculateBaseLineY(answerList.get(i), 1);
            //计算位置
            float currentTextHeight = (fontMetrics.bottom - fontMetrics.top) * 2 * i;

            //答案在右侧绘制，需要加上 x 的偏移量
            mAnswerRectList.get(i).offset(getWidth() - mAnswerRectList.get(i).width(), (int) (currentTextHeight + baseLineY));

            canvas.drawText(answerList.get(0), getWidth(), baseLineY + currentTextHeight, mTextPaint);
        }


        for (int i = 0; i < mQuestionRectList.size(); i++) {
            canvas.drawRect(mQuestionRectList.get(i), mLinePaint);
        }

        Log.d(TAG, "drawText: 绘制完成之后，保存的文字Rect \nmQuestionRectList.size()= " + mQuestionRectList.size() + "  mAnswerRectList.size() = " + mAnswerRectList.size());
    }

    /**
     * 计算 baseLine
     *
     * @param text
     * @param code 0：问题  1：答案  （加这个参数，为了将文字的矩形保存）
     * @return
     */
    private float calculateBaseLineY(String text, int code) {
        //得到文字的矩形大小
        Rect textRect = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), textRect);

        if (0 == code && mQuestionRectList != null) {
            //保存问题的 textRect
            mQuestionRectList.add(textRect);
        } else if (1 == code && mAnswerRectList != null) {
            mAnswerRectList.add(textRect);
        }

        return textRect.height() / 2 + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int downX, downY;
        boolean isInRect = false;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                downY = (int) event.getY();

                //判断是否在（问题/答案）的矩形内
                isInRect = isTouchInRect(downX, downY);
                break;

            case MotionEvent.ACTION_MOVE:

                //按下的点，在矩形内，才开始绘制 连线
//                if (isInRect) {
//                    moveX = getX();
//                    moveY = getY();
//
//
//                }
                moveX = event.getX();
                moveY = event.getY();
                invalidate();//刷新
                break;

            case MotionEvent.ACTION_UP:


                break;
        }


        return true;
    }

    private boolean isTouchInRect(int downX, int downY) {
        Log.d(TAG, "isTouchInRect:downX =  " + downX + "  downY = " + downY);

        for (int i = 0; i < mQuestionRectList.size(); i++) {
            if (mQuestionRectList.get(i).contains(downX, downY)) {
                Log.d(TAG, "isTouchInRect: 当前点击的是 mQuestionRectList " + i);
                return true;
            }
        }

        for (int i = 0; i < mAnswerRectList.size(); i++) {
            if (mAnswerRectList.get(i).contains(downX, downY)) {
                Log.d(TAG, "isTouchInRect: 当前点击的是 mAnswerRectList " + i);
                return true;
            }
        }

        return false;
    }

    /**
     * 测量
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);


    }


    /**
     * dp --> px
     *
     * @param context
     * @param dp
     * @return
     */
    private int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
