package com.csx.mytestdemo.multiple_state;

import android.content.Context;
import android.view.View;

import com.csx.mytestdemo.R;
import com.kingja.loadsir.callback.Callback;

/**
 * @Created by cuishuxiang
 * @date 2018/5/2.
 * @description:
 */

public class EmptyCallBack extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.empty_layout;
    }

    @Override
    protected boolean onReloadEvent(Context context, View view) {
        return super.onReloadEvent(context, view);
    }

    @Override
    public boolean getSuccessVisible() {
        return super.getSuccessVisible();
    }
}