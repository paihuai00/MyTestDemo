package com.csx.mytestdemo.connect_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
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
 * @date 2018/4/17.
 * @description: 连线题view
 * 1, ConnectCallBack 回调
 * 2，设置最大连线数量（如果超出，就会重置之前的连线，重新绘制）
 * 3，设置question / answer ，默认都是List<String>
 */

public class ConnectLineView extends View {
    private static final String TAG = ConnectLineView.class.getSimpleName();

    private List<LinesData> mLinesDataList;//绘制的线 list

    //传入的题目 / 答案（默认是String类型）
    private List<String> mQuestionList, mAnswerList;

    private Paint mTextPaint;//绘制文字的画笔
    private Paint.FontMetrics fontMetrics;//用于计算基线
    private int mTextColor = Color.BLACK;
    private int mTextSize = 30;

    private Paint mLinePaint;//绘制连线的画笔
    private int mLineColor = Color.RED;
    private int mLineSize = 5;

    private List<Rect> mQuestionRectList;
    private List<Rect> mAnswerRectList;

    private int offsetX = 30;//x 轴的偏移量

    private int maxLinesNum = 2;// 最大的连线数量

    public ConnectLineView(Context context) {
        this(context, null);
    }

    public ConnectLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ConnectLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d(TAG, "ConnectLineView: ");
        initView(context);


        initData();

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

            Log.d(TAG, "initView: " + (fontMetrics.bottom - fontMetrics.top));
        }

        if (mLinePaint == null) {
            mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mLinePaint.setColor(mLineColor);//文字颜色 默认黑色
            mLinePaint.setAntiAlias(true);
            mLinePaint.setStrokeWidth(dp2px(context, mLineSize));
            mLinePaint.setStyle(Paint.Style.STROKE);
        }

        //保存连线的list
        mLinesDataList = new ArrayList<>();
    }

    /**
     * 初始化数据(假数据，需要删除)
     */
    private void initData() {
        //问题
        mQuestionList = new ArrayList<>();
        mQuestionList.add("A");
        mQuestionList.add("B");
        mQuestionList.add("C");
        mQuestionList.add("D");

        //答案
        mAnswerList = new ArrayList<>();
        mAnswerList.add("1");
        mAnswerList.add("2");
        mAnswerList.add("3");
//        mAnswerList.add("答案为：2");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw: " + getWidth() + " getHeight()=" + getHeight());
//        canvas.drawColor(Color.GRAY);

        drawText(canvas);

        drawMoveLine(canvas);

        drawSaveLine(canvas);

        //打印矩形框
        for (int i = 0; i < mQuestionRectList.size(); i++) {
            canvas.drawRect(mQuestionRectList.get(i), mLinePaint);
        }

        for (int i = 0; i < mAnswerRectList.size(); i++) {
            canvas.drawRect(mAnswerRectList.get(i), mLinePaint);
        }
    }

    /**
     * 绘制移动时候的线
     *
     * @param canvas
     */
    private void drawMoveLine(Canvas canvas) {
        if (downRect == null || movePoint == null) {
            Log.d(TAG, "drawMoveLine: downRect == null || movePoint == null");
            return;
        }
        if (0 == movePoint.x && 0 == movePoint.y) {
            Log.d(TAG, "drawMoveLine: 0 == movePoint.x && 0 == movePoint.y");
            return;
        }
        canvas.drawLine(downRect.right - downRect.width() / 2, (downRect.bottom + downRect.top) / 2, movePoint.x, movePoint.y, mLinePaint);
    }

    /**
     * 绘制已经保存的line
     *
     * @param canvas
     */
    private void drawSaveLine(Canvas canvas) {
        if (mLinesDataList == null || mLinesDataList.size() == 0) {
            Log.d(TAG, "drawSaveLine: mLinesDataList == null || mLinesDataList.size() == 0");
            return;
        }

        for (int i = 0; i < mLinesDataList.size(); i++) {
            LinesData linesData = mLinesDataList.get(i);
            canvas.drawLine(linesData.getStartPoint().x, linesData.getStartPoint().y,
                    linesData.getEndPoint().x, linesData.getEndPoint().y, mLinePaint);
        }

    }

    /**
     * 绘制 题目 + 答案
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        int itemQuestionViewHeight = getHeight() / mQuestionList.size();

        //绘制左侧的题目
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        for (int i = 0; i < mQuestionList.size(); i++) {

//            canvas.drawLine(0, itemQuestionViewHeight * i, 100, itemQuestionViewHeight * i, mLinePaint);

            int baseLineY = (int) (itemQuestionViewHeight / 2 + itemQuestionViewHeight * i + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom);

            canvas.drawText(mQuestionList.get(i), offsetX, baseLineY, mTextPaint);

        }

        mTextPaint.setTextAlign(Paint.Align.RIGHT);
        int itemAnswerViewHeight = getHeight() / mAnswerList.size();
        //绘制右侧的答案
        for (int i = 0; i < mAnswerList.size(); i++) {
//            canvas.drawLine(getWidth(), itemAnswerViewHeight * i, getWidth() - 100, itemAnswerViewHeight * i, mLinePaint);

            int baseLineY = (int) (itemAnswerViewHeight / 2 + itemAnswerViewHeight * i + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom);

            canvas.drawText(mAnswerList.get(i), getWidth() - 30, baseLineY, mTextPaint);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure: ");

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);


        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                int number = mAnswerList.size() > mQuestionList.size() ? mAnswerList.size() : mQuestionList.size();
                heightSize = (int) (getPaddingTop() + getPaddingBottom() + number * (fontMetrics.bottom - fontMetrics.top));
                Log.d(TAG, "onMeasure: heightSize = " + heightSize);
                break;
            case MeasureSpec.AT_MOST:
                break;
        }

        setMeasuredDimension(widthSize, (int) (heightSize * 1.3));

        //存储矩形
        initRect(widthSize, (int) (heightSize * 1.3));

    }

    /**
     * 初始化，问题/答案 的矩形外框
     */
    private void initRect(int w, int h) {
        //----------------------存储问题的 Rect-----------------------------
        mQuestionRectList = new ArrayList<>();

//        int maxQuestionWidth = 0;
//
//        for (int i = 0; i < mQuestionList.size(); i++) {
//            Rect rect = new Rect();
//            mTextPaint.getTextBounds(mQuestionList.get(i), 0, mQuestionList.get(i).length(), rect);
//            Log.d(TAG, "initRect: mQuestionList.get(i).length()=" + mQuestionList.get(i).length() + " rect.width()=" + rect.width());
//            int currentWidth = rect.width();
//            if (maxQuestionWidth < currentWidth) {
//                maxQuestionWidth = currentWidth;
//            }
//        }

//        Log.d(TAG, "initRect: maxQuestionWidth = " + maxQuestionWidth);


        for (int i = 0; i < mQuestionList.size(); i++) {
            Rect rect = new Rect();
            mTextPaint.getTextBounds(mQuestionList.get(i), 0, mQuestionList.get(i).length(), rect);
            Log.d(TAG, "initRect: mQuestionList.get(i).length()=" + mQuestionList.get(i).length() + " rect.width()=" + rect.width());
            int currentWidth = rect.width();
            Rect rect1 = new Rect(offsetX - 20, h / mQuestionList.size() * i, currentWidth + offsetX + 20, h / mQuestionList.size() * (i + 1));

            mQuestionRectList.add(rect1);
        }


        //----------------------存储问题的 Rect-----------------------------
        mAnswerRectList = new ArrayList<>();

        int maxAnswerWidth = 0;

        for (int i = 0; i < mAnswerList.size(); i++) {
            Rect rect = new Rect();
            mTextPaint.getTextBounds(mAnswerList.get(i), 0, mAnswerList.get(i).length(), rect);
            Log.d(TAG, "initRect: mQuestionList.get(i).length()=" + mAnswerList.get(i).length() + " rect.width()=" + rect.width());
            int currentWidth = rect.width();
            if (maxAnswerWidth < currentWidth) {
                maxAnswerWidth = currentWidth;
            }
        }
        Log.d(TAG, "initRect: maxAnswerWidth = " + maxAnswerWidth);

        for (int i = 0; i < mAnswerList.size(); i++) {
            Rect rect = new Rect(w - offsetX - maxAnswerWidth - 20, h / mAnswerList.size() * i, w - offsetX + 20, h / mAnswerList.size() * (i + 1));

            mAnswerRectList.add(rect);
        }
    }

    private boolean downInRect = false;
    private Rect downRect;
    private boolean upInRect = false;
    private Rect upRect;
    private Point movePoint = new Point();

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downInRect = calculateDownInRect(event.getX(), event.getY());
                Log.d(TAG, "onTouchEvent: downInRect =" + downInRect);
                break;
            case MotionEvent.ACTION_MOVE:
                if (downInRect) {
                    movePoint.x = (int) event.getX();
                    movePoint.y = (int) event.getY();
                } else {
                    movePoint.x = 0;
                    movePoint.y = 0;
                }
                break;
            case MotionEvent.ACTION_UP:
                movePoint.x = 0;
                movePoint.y = 0;
                upInRect = calculateUpInRect(event.getX(), event.getY());
                Log.d(TAG, "onTouchEvent: upInRect = " + upInRect);
                if (downInRect && upInRect) {
                    Log.d(TAG, "onTouchEvent: downInRect && upInRect = true");
                    //需要判断，所连的线不能再同侧
                    if (mAnswerRectList.contains(downRect) && mAnswerRectList.contains(upRect)) {
                        Log.d(TAG, "onTouchEvent: mAnswerRectList 此时连线在同侧");
                        break;
                    }

                    if (mQuestionRectList.contains(downRect) && mQuestionRectList.contains(upRect)) {
                        Log.d(TAG, "onTouchEvent: mQuestionRectList 此时连线在同侧");
                        break;
                    }
                    //存储连线
                    saveLines(downRect, upRect);
                }

                break;
        }
        invalidate();

        return true;
    }

    /**
     * 根据按下的rect 和 抬起的rect 存储需要绘制的lines
     * <p>
     * 如果当前绘制的linesNum，大于设置的chooseNum，则清空，重新绘制
     *
     * @param downRect
     * @param upRect
     */
    private void saveLines(Rect downRect, Rect upRect) {
        if (mLinesDataList.size() >= maxLinesNum) {
            Log.d(TAG, "saveLines: mLinesDataList.size() =" + mLinesDataList.size() + "  当前最大可选chooseNum：" + maxLinesNum);
            if (mConnectCallBack != null) {
                mConnectCallBack.onFailConnect("当前超出最大的可选数量，即将清除从新选择！");
            }
            mLinesDataList.clear();
        }
        LinesData linesData = new LinesData();
        //按下的点 是在问题区域(左侧)
        for (int i = 0; i < mQuestionRectList.size(); i++) {
            if (mQuestionRectList.get(i) == downRect) {
                linesData.setStartPoint(new Point(downRect.right, (downRect.top + downRect.bottom) / 2));
                linesData.setStartPosition(i);
                linesData.setStartType(0);
                break;
            }
        }
        //按下的点在 答案区域（右侧）
        for (int i = 0; i < mAnswerRectList.size(); i++) {
            if (mAnswerRectList.get(i) == downRect) {
                linesData.setStartPoint(new Point(downRect.left, (downRect.top + downRect.bottom) / 2));
                linesData.setStartPosition(i);
                linesData.setStartType(1);
                break;
            }
        }

        //抬起的点在，问题（左侧）
        for (int i = 0; i < mQuestionRectList.size(); i++) {
            if (mQuestionRectList.get(i) == upRect) {
                linesData.setEndPoint(new Point(upRect.right, (upRect.top + upRect.bottom) / 2));
                linesData.setEndPosition(i);
                linesData.setEndType(0);
                break;
            }
        }

        //抬起的点在 答案区域（右侧）
        for (int i = 0; i < mAnswerRectList.size(); i++) {
            if (mAnswerRectList.get(i) == upRect) {
                linesData.setEndPoint(new Point(upRect.left, (upRect.top + upRect.bottom) / 2));
                linesData.setEndPosition(i);
                linesData.setEndType(1);
                break;
            }
        }

        mLinesDataList.add(linesData);

        this.downRect = null;
        this.upRect = null;

        //将结果回调出去
        if (mConnectCallBack != null) {
            mConnectCallBack.onSucceedConnect(mLinesDataList);
        }

        invalidate();
    }

    /**
     * 抬起的点，是否在矩形范围
     *
     * @param x
     * @param y
     * @return
     */
    private boolean calculateUpInRect(float x, float y) {

        for (int i = 0; i < mQuestionRectList.size(); i++) {
            if (mQuestionRectList.get(i).contains((int) x, (int) y)) {
                upRect = mQuestionRectList.get(i);
                return true;
            }
        }

        for (int i = 0; i < mAnswerRectList.size(); i++) {
            if (mAnswerRectList.get(i).contains((int) x, (int) y)) {
                upRect = mAnswerRectList.get(i);
                return true;
            }
        }

        Log.d(TAG, "calculateUpInRect: false");
        return false;
    }

    /**
     * 按下的点，是否在矩形范围
     *
     * @param x
     * @param y
     * @return
     */
    private boolean calculateDownInRect(float x, float y) {
        for (int i = 0; i < mQuestionRectList.size(); i++) {
            if (mQuestionRectList.get(i).contains((int) x, (int) y)) {
                downRect = mQuestionRectList.get(i);
                return true;
            }
        }

        for (int i = 0; i < mAnswerRectList.size(); i++) {
            if (mAnswerRectList.get(i).contains((int) x, (int) y)) {
                downRect = mAnswerRectList.get(i);
                return true;
            }
        }
        Log.d(TAG, "calculateDownInRect: = false");
        return false;
    }

    /**
     * 设置 题目数据
     *
     * @param questionList
     */
    public void setQuestionList(List<String> questionList) {
        this.mQuestionList = questionList;
        invalidate();
    }

    /**
     * 设置 答案数据
     *
     * @param answerList
     */
    public void setAnswerList(List<String> answerList) {
        this.mAnswerList = answerList;
        invalidate();
    }

    /**
     * 设置单选or多选
     */
    public void setMaxLinesNum(int maxLinesNum) {
        this.maxLinesNum = maxLinesNum;
    }

    /**
     * 定义连线实体类
     * 1，开始 / 结束，的点
     * 2，类型（0：试题   1：答案）
     * 3，起始 / 终止 连接的位置
     */
    class LinesData {
        Point startPoint, endPoint;
        int startType;//0 :为试题  1：为答案
        int startPosition;

        int endType;
        int endPosition;

        public Point getStartPoint() {
            return startPoint;
        }

        public void setStartPoint(Point startPoint) {
            this.startPoint = startPoint;
        }

        public Point getEndPoint() {
            return endPoint;
        }

        public void setEndPoint(Point endPoint) {
            this.endPoint = endPoint;
        }

        public int getStartType() {
            return startType;
        }

        public void setStartType(int startType) {
            this.startType = startType;
        }

        public int getStartPosition() {
            return startPosition;
        }

        public void setStartPosition(int startPosition) {
            this.startPosition = startPosition;
        }

        public int getEndType() {
            return endType;
        }

        public void setEndType(int endType) {
            this.endType = endType;
        }

        public int getEndPosition() {
            return endPosition;
        }

        public void setEndPosition(int endPosition) {
            this.endPosition = endPosition;
        }
    }

    /**
     * 设置回调
     */
    private ConnectCallBack mConnectCallBack;

    public void setConnectCallBack(ConnectCallBack mConnectCallBack) {
        this.mConnectCallBack = mConnectCallBack;
    }

    public interface ConnectCallBack {
        //连线失败的回调
        void onFailConnect(String failInfo);

        //连线成功的回调
        void onSucceedConnect(List<LinesData> linesDataList);

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

