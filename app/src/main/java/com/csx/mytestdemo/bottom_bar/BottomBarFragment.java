package com.csx.mytestdemo.bottom_bar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csx.mlibrary.base.BaseFragment;

/**
 * @Created by cuishuxiang
 * @date 2018/2/26.
 */

public class BottomBarFragment extends Fragment {

    public static final String CONTENT = "content";
    private TextView mTextView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle bundle) {
        mTextView = new TextView(getActivity());
        mTextView.setGravity(Gravity.CENTER);
        String content = getArguments().getString(CONTENT);
        mTextView.setText(content);
        return mTextView;
    }


}
