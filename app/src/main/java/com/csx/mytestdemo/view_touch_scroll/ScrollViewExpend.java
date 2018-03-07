package com.csx.mytestdemo.view_touch_scroll;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

/**
 * @Created by cuishuxiang
 * @date 2018/3/6.
 * @description: 继承scrollView，解决嵌套ListView的滑动问题
 *
 * 如果触摸的范围在 ScrollView 就拦截该事件
 * 范围在ListView中，ScrollView 就不拦截该事件
 */

public class ScrollViewExpend extends ScrollView {
    public ScrollViewExpend(Context context) {
        super(context);
    }

    public ScrollViewExpend(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollViewExpend(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isTouchListView(ev)) {
            return false;
        }
        return true;
    }

    /**
     * 判断是否按下的位置，在ListView内
     *
     * @param ev
     * @return
     */
    private boolean isTouchListView(MotionEvent ev) {
        if (0 == getChildCount()) {
            return super.onInterceptTouchEvent(ev);
        }

        float touchX = ev.getX();
        float touchY = ev.getY() + getScrollY();

        //ScrollView 只有一个子View
        ViewGroup rootView = (ViewGroup) getChildAt(0);

        for (int i = 0; i < rootView.getChildCount(); i++) {
            View childView = rootView.getChildAt(i);

            //判断是否是ListView
            if (isListView(childView)) {
                if (touchX > childView.getLeft() && touchX < childView.getRight() &&
                        touchY < childView.getBottom() && touchY > childView.getTop()) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isListView(View childView) {
        return childView instanceof ListView;
    }


}
