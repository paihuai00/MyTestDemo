package com.csx.mytestdemo.view_slide_menu;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;

/**
 * Created by cuishuxiang on 2018/3/12.
 * 侧滑菜单，实现分析
 * 1，继承 HorizontalScrollView ，写好两个布局 menu content
 * 2，计算 menu content的跨度。菜单的宽度 = 屏幕宽度 - 右侧一段距离
 * 3，默认关闭，手指滑动中间处理是打开/关闭状态，使用代码滑动到相应的位置；
 * 4，快速滑动的处理，手指抬起的时候，需要消费事件（否则会调用super方法中发fling()，导致菜单打开关闭失效）
 * 5，内容的缩放，菜单部分有位移和透明度
 * 6，事件分发
 */

public class SlidingMenu extends HorizontalScrollView {
    private static final String TAG = "SlidingMenu";

    //屏幕宽度
    private int screenWidth;
    //1，内容页，宽度 = 屏幕宽度
    private int contentWidth;
    //2，菜单页，宽度 = 屏幕宽度 - 右侧一小段距离
    private int menuWidth;
    //菜单 右侧的offset
    private float menuRightOffset;

    private ViewGroup contentView, menuView;

    private GestureDetector mGestureDetector;//手势

    private boolean isMenuOpen = false;


    public SlidingMenu(Context context) {
        this(context, null);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);

    }

    private void init(Context context) {
        screenWidth = getScreenWidth(context);

        menuRightOffset = dp2px(context, 60);

        menuWidth = (int) (screenWidth - menuRightOffset);

        contentWidth = screenWidth;

        mGestureDetector = new GestureDetector(context, mOnGestureListener);
    }

    private GestureDetector.OnGestureListener mOnGestureListener = new GestureDetector.SimpleOnGestureListener(){
        @Override
        public boolean onDown(MotionEvent e) {
            if (e.getX() > menuWidth && isMenuOpen) {
                Log.d(TAG, "onDown: ");
                closeMenu();
            }
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//            Log.d(TAG, "onFling: velocityX = " + velocityX + "  velocityY = " + velocityY);
            //  velocityX > 0 向右快速滑动
            //  velocityX < 0 向左快速滑动

            //判断是否是 X 方向的快速滑动
            boolean isXScroll = Math.abs(velocityX) > Math.abs(velocityY);
            if (isMenuOpen) {
                //屏蔽Y方向的快速滑动
                if (velocityX < 0 && isXScroll) {
                    closeMenu();
                    return true;
                }
            } else {
                if (velocityX > 0 && isXScroll) {

                    openMenu();
                    return true;
                }
            }

            return false;
        }
    };

    /**
     * 当菜单打开，点击右侧时，关闭菜单
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isMenuOpen) {
            // i此时菜单处于打开状态
            if (ev.getX() > menuWidth) {
                //按下的点，大于菜单的范围，拦截事件
                //此处返回 true ，表示子View接收不到事件；
                //将事件交由自己的 onTouchEvent处理
                return true;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 需要优化
     * 事件的拦截：当菜单打开的时候，点击右侧内容页。菜单关闭(且不会响应内容页中的点击事件)
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mGestureDetector.onTouchEvent(ev)) {
            //处理快速滑动。
            return true;
        }

        int action = ev.getAction();
        int scrollX = getScrollX();
        switch (action) {
            case MotionEvent.ACTION_UP:
                //处理抬起的事件,判断当前是显示那个View
                if (scrollX <= menuWidth / 2) {
                    //打开菜单
                    openMenu();
                } else {
                    //关闭菜单
                    closeMenu();
                }
                //这里需要消费事件，否则进入super.onTouchEvent
                //上面的打开/关闭菜单会失效
                return false;
        }

        return super.onTouchEvent(ev);
    }


    /**
     * 在xml布局加载完毕，才会调该方法
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        //1,获取 scrollView 内部包裹的child
        ViewGroup linearLayoutRoot = (ViewGroup) getChildAt(0);

        int childCount = linearLayoutRoot.getChildCount();
        if (2 != childCount) {
            throw new RuntimeException("SlidingMenu 只允许放置2个布局，menu 和 content ！！");
        }

        //2，通过 linearLayoutRoot 获取 menu  content
        menuView = (ViewGroup) linearLayoutRoot.getChildAt(0);
        ViewGroup.LayoutParams menuLayoutParams = menuView.getLayoutParams();
        menuLayoutParams.width = menuWidth;
        menuView.setLayoutParams(menuLayoutParams);


        contentView = (ViewGroup) linearLayoutRoot.getChildAt(1);
        ViewGroup.LayoutParams contentParams = contentView.getLayoutParams();
        contentParams.width = contentWidth;
        contentView.setLayoutParams(contentParams);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //初始化的时候，默认是 内容页(滑动到内容页)
        closeMenu();
    }

    /**
     * 处理 menu content 的缩放，需要获取当前滚动的位置
     *
     * @param l  now距左边的距离
     * @param t  now距顶部的距离
     * @param oldl  old 距左边的距离
     * @param oldt  old 距顶部的距离
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
//        Log.d(TAG, "onScrollChanged: left = " + l + " top = " + t + " oldl = " + oldl + "  oldt = " + oldt);
        /**
         * 只需处理 l 的距离，根据 l 左边距。计算一个缩放的比例
         *
         * left ：最大为 menuWidth(菜单隐藏)
         *        最小为 0 (此时菜单显示)
         */
        float scale = l * 1f / menuWidth;//scale menu范围：1~0
        float contentScale = 0.7f + 0.3f * scale;//内容scale范围  1~0.7
        float menuScale = 0.7f + 0.3f * (1 - scale);//菜单的scale与内容相反

//        Log.d(TAG, "onScrollChanged: scale = " + scale + "  contentScale = " + contentScale);

        //需要调整缩放的中心点(左边的中心)。不设置的话缩放是以 中心 缩放的
        contentView.setPivotX(0);
        contentView.setPivotY(contentView.getMeasuredHeight() / 2);

        contentView.setScaleX(contentScale);
        contentView.setScaleY(contentScale);

        //菜单的 缩放，0.7 ~ 1
        //      透明度:0.7 ~ 1
        menuView.setPivotX(menuWidth);
        menuView.setPivotY(menuView.getMeasuredHeight() / 2);
        menuView.setScaleY(menuScale);
        menuView.setScaleX(menuScale);
        menuView.setAlpha(menuScale);

        //制作抽屉效果
        //左侧menu开始的时候，在content底部，慢慢滑动出来
//        menuView.setTranslationX(l * 0.7f);
    }

    /**
     * 打开菜单
     */
    private void openMenu() {
        //有动画
        smoothScrollTo(0, 0);
        isMenuOpen = true;
    }
    /**
     * 关闭菜单
     */
    private void closeMenu() {
        //有动画
        smoothScrollTo(menuWidth, 0);
        isMenuOpen = false;
    }

    /**
     * 获取屏幕的宽度
     * @param context
     * @return
     */
    private int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        DisplayMetrics displayMetrics = new DisplayMetrics();

        wm.getDefaultDisplay().getMetrics(displayMetrics);

        int screenWidth = displayMetrics.widthPixels;

        Log.d(TAG, "getScreenWidth: = " + screenWidth);
        return screenWidth;
    }

    /**
     * dp -- > px
     * @param context
     * @param dp
     * @return
     */
    private int dp2px(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
