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

public class LoadingCallBack extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.dialog_loading;
    }

    @Override
    protected boolean onReloadEvent(Context context, View view) {
        return super.onReloadEvent(context, view);
    }

    /**
     * 是否在显示Callback视图的时候显示原始图(SuccessView)，
     * 返回true显示，false隐藏
     * @return
     */
    @Override
    public boolean getSuccessVisible() {
        return super.getSuccessVisible();
    }
}
