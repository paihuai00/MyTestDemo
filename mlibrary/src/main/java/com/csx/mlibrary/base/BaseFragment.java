package com.csx.mlibrary.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Created by cuishuxiang
 * @date 2018/2/5.
 * fg基类
 */

public abstract class BaseFragment extends Fragment {
    private static final String TAG = BaseFragment.class.getSimpleName().toString();

    protected View mView;
    private Unbinder mUnbinder;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView==null)
            mView = inflater.inflate(getLayoutRes(), container, false);
        mUnbinder = ButterKnife.bind(this, mView);

        initView();

        return mView;
    }


    //设置布局id
    public abstract int getLayoutRes();
    //初始化view
    protected abstract void initView();


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnbinder!=null)
            mUnbinder.unbind();
    }
}
