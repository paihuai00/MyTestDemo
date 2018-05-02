package com.csx.mlibrary.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.csx.mlibrary.R;


/**
 * 描述：带进度条的WebView和一些初始化操作
 * ================================================================
 */
public class ProgressWebView extends LinearLayout {
    private ProgressBar progressbar;
    private WebView mWebView;

    public ProgressWebView(Context context) {
        this(context, null);
    }
    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        addProgressbar(context);// 添加进度条
    }
    /**
     * 添加进度条
     */
    private void addProgressbar(Context context) {
        removeAllViews();
        setOrientation(VERTICAL);
        progressbar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        progressbar.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.FILL_PARENT, dip2px(2)));
        Drawable drawable = context.getResources().getDrawable(R.drawable.progress_bar_states);
        progressbar.setProgressDrawable(drawable);
        addView(progressbar);
        mWebView = new WebView(context);
        mWebView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(mWebView);
    }

    /**
     * dip----to---px
     *
     * @return
     */
    public int dip2px(int dip) {
        // 缩放比例(密度)
        float density = getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5);
    }

    public WebView getWebView() {
        if (mWebView == null) {
            addProgressbar(getContext());
        }
        return mWebView;
    }

    public ProgressBar getProgressbar() {
        if (progressbar == null) {
            addProgressbar(getContext());
        }
        return progressbar;
    }
}