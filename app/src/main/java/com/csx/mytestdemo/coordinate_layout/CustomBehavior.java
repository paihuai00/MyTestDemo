package com.csx.mytestdemo.coordinate_layout;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Create by cuishuxiang
 *
 * @date: on 2018/8/31
 * @description:
 */
public class CustomBehavior extends CoordinatorLayout.Behavior<View> {
    private static final String TAG = "CustomBehavior";
    private float dy;

    public CustomBehavior() {
        super();
    }

    public CustomBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof RecyclerView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        Log.i(TAG, "onDependentViewChanged: child.getHeight() = " + child.getHeight() + "  dependency.getTranslationY() = " + dependency.getTranslationY());
//        if (dy == 0)

        return true;
    }
}
