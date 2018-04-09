package com.csx.mytestdemo.float_menu;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * @Created by cuishuxiang
 * @date 2018/4/9.
 * @description: fab按钮，监听列表滑动实现隐藏or出现
 */

public class ScrollFabBehavior extends FloatingActionButton.Behavior {


    public ScrollFabBehavior() {
    }

    public ScrollFabBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 页面开始滑动。
     * @param coordinatorLayout
     * @param child
     * @param directTargetChild
     * @param target
     * @param nestedScrollAxes
     * @return
     */
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton
            child, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL ||
                super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target,
                        nestedScrollAxes);
    }

    /**
     * 页面正在滑动
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param dxConsumed
     * @param dyConsumed
     * @param dxUnconsumed
     * @param dyUnconsumed
     */
    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child,
                               View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int
                                       dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed,
                dxUnconsumed, dyUnconsumed);
        if (dyConsumed >= 0 && child.getVisibility() == View.VISIBLE) {
//            child.hide();
            child.setVisibility(View.INVISIBLE);
        } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
            child.show();
            child.setVisibility(View.VISIBLE);
        }
    }

}
