package com.csx.mlibrary.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

/**
 * @Created by cuishuxiang
 * @date 2018/2/2.
 */

public class EnsureDialog extends AlertDialog implements DialogInterface.OnCancelListener {
    private static final String TAG = "EnsureDialog";

    public EnsureDialog(@NonNull Context context) {
        this(context,0);
    }

    public EnsureDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected EnsureDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);


    }

    @Override
    public void onCancel(DialogInterface dialog) {

    }


    public static class Builder {

        private String title;
        private OnCancelListener mOnCancelListener;




    }
}

