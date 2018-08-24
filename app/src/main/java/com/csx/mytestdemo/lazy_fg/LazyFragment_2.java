package com.csx.mytestdemo.lazy_fg;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csx.mlibrary.base.BaseFragment;
import com.csx.mytestdemo.R;

import butterknife.BindView;

/**
 * Create by cuishuxiang
 *
 * @date: on 2018/6/21
 * @description:
 */

public class LazyFragment_2 extends BaseFragment {
    private static final String TAG = LazyFragment_2.class.getSimpleName().toString();
    @BindView(R.id.lazy_tv)
    TextView mLazyTv;
    public LazyFragment_2(){
        super();
        Log.d(TAG, "LazyFragment_2: ");
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ");
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fg_lazy;
    }

    @Override
    protected void initView() {
        mLazyTv.setText("2");
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d(TAG, "setUserVisibleHint: isVisibleToUser = " + isVisibleToUser);
    }
    public void setTextString(String name) {
        if (mLazyTv != null)
            mLazyTv.setText(name);
    }
}
