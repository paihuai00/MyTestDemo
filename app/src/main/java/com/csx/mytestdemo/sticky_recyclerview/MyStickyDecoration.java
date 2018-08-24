package com.csx.mytestdemo.sticky_recyclerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * Create by cuishuxiang
 *
 * @date: on 2018/6/19
 * @description:
 */

public class MyStickyDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = "MyStickyDecoration";

    private Context mContext;
    private int mDecorColor = Color.GRAY;
    private int mDecorHeight;

    public MyStickyDecoration(Context context) {
        super();
        mContext = context;
        mDecorHeight = dp2px(context, 20);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int childCount = parent.getChildCount();
        Log.d(TAG, "getItemOffsets: childCount = " + childCount);

        int left = view.getPaddingLeft();
        int top = mDecorHeight;
        int right = view.getWidth() - view.getPaddingRight();
        int bottom = view.getPaddingBottom();
        outRect.set(left, top, right, bottom);
    }


    private int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) dp, context.getResources().getDisplayMetrics());
    }
}
