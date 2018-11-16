package com.csx.mlibrary.actionbar;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.csx.mlibrary.R;

/**
 * Create by cuishuxiang
 *
 * @date: on 2018/10/12
 * @description:
 */
public class DefaultActionBar extends AbsActionBar<DefaultActionBar.Builder.DefaultActionBarParams> {


    public DefaultActionBar(Builder.DefaultActionBarParams params) {
        super(params);
    }

    @Override
    public int bindLayoutResId() {
        return R.layout.default_actionbar;
    }

    @Override
    public void applyView() {
        if (!TextUtils.isEmpty(getParams().mTitle)) {
            setText(R.id.title, "hahhaha");
        }

        if (getParams().mLeftClickListener != null) {
            setLeftClickListener(R.id.left_iv, getParams().mLeftClickListener);
        }
    }


    public static class Builder extends AbsActionBar.Builder {
        DefaultActionBarParams P;

        public Builder(Context context) {
            this(context, null);
        }

        public Builder(Context context, ViewGroup viewGroup) {
            super(context, viewGroup);
            P = new DefaultActionBarParams(context, viewGroup);
        }

        @Override
        public DefaultActionBar builder() {
            DefaultActionBar mDefaultActionBar = new DefaultActionBar(P);

            return mDefaultActionBar;
        }

        public Builder setTitle(CharSequence title) {
            P.mTitle = title;
            return this;
        }


        public Builder setLeftIcon(@DrawableRes int drawableId) {
            P.mLeftIcon = drawableId;
            return this;
        }

        public Builder setLeftClick(View.OnClickListener onClickListener) {
            P.mLeftClickListener = onClickListener;
            return this;
        }

        public class DefaultActionBarParams extends AbsActionBar.Builder.ActionBarParams {
            public CharSequence mTitle;
            public int mLeftIcon;
            public View.OnClickListener mLeftClickListener;


            //放置所有效果
            public DefaultActionBarParams(Context context, ViewGroup viewGroup) {
                super(context, viewGroup);

                if (mLeftClickListener == null)
                    mLeftClickListener = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((Activity) mContext).finish();
                        }
                    };

            }
        }
    }


}
