package com.csx.mytestdemo.bottom_dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csx.mytestdemo.R;

/**
 * Create by cuishuxiang
 *
 * @date: on 2018/7/26
 * @description:
 */

public class BottomDialogFragment extends DialogFragment {
    private static final String TAG = "BottomDialogFragment";
    private View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_dialog, container, false);

        return mView;
    }

}
