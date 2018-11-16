package com.csx.mlibrary.actionbar;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Create by cuishuxiang
 *
 * @date: on 2018/10/12
 * @description: 头部的Builder 基类
 */
public abstract class AbsActionBar<T extends AbsActionBar.Builder.ActionBarParams> implements IActionBar {

    private T mParams;
    private View mActionBarView;

    public AbsActionBar(T params) {
        this.mParams = params;

        createAndBindView();
    }

    /**
     * 绑定创建View
     */
    private void createAndBindView() {
        //增加判断，是否存在parent(使用代码，拿到 DecorView 的 0  位置 上的布局)
        if (mParams.mParent == null) {
                ViewGroup activityRoot = (ViewGroup) ((Activity) mParams.mContext).getWindow().getDecorView();
                mParams.mParent = (ViewGroup) activityRoot.getChildAt(0);
        }

        if (mParams.mParent == null) {
            try {
                throw new IllegalAccessException("请添加ViewPargent！");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }finally {
                return;
            }
        }


        //1，创建view
        mActionBarView = LayoutInflater.from(mParams.mContext).
                inflate(bindLayoutResId(), mParams.mParent, false);

        //2,添加 到 0 位
        mParams.mParent.addView(mActionBarView, 0);

        applyView();
    }

    public void setText(int viewId, CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            TextView mTextView = findViewById(viewId);
            mTextView.setText(charSequence);
        }
    }

    public void setLeftClickListener(int left_iv, View.OnClickListener leftClickListener) {
        if (leftClickListener != null) {
            ImageView mLeftBack = findViewById(left_iv);
            mLeftBack.setOnClickListener(leftClickListener);
        }
    }

    public <V extends View> V findViewById(int viewId) {
        return (V) mActionBarView.findViewById(viewId);
    }

    public T getParams() {
        return mParams;
    }

    public abstract static class Builder {
        ActionBarParams P;

        public Builder(Context context, ViewGroup viewGroup) {
            P = new ActionBarParams(context, viewGroup);
        }

        public abstract AbsActionBar builder();

        public static class ActionBarParams {
            public Context mContext;
            public ViewGroup mParent;

            public ActionBarParams(Context context, ViewGroup viewGroup) {
                this.mContext = context;
                this.mParent = viewGroup;
            }
        }
    }
}
