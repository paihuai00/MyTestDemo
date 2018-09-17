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
import me.henrytao.smoothappbarlayout.SmoothAppBarLayout;
import me.henrytao.smoothappbarlayout.base.ObservableFragment;
import me.henrytao.smoothappbarlayout.base.Utils;
import me.henrytao.smoothappbarlayout.widget.NestedScrollView;

/**
 * Create by cuishuxiang
 *
 * @date: on 2018/6/21
 * @description:
 */

public class LazyFragment_1 extends BaseFragment implements ObservableFragment {
    private static final String TAG = LazyFragment_1.class.getSimpleName().toString();
    @BindView(R.id.lazy_tv)
    TextView mLazyTv;
    @BindView(R.id.nested_scroll_view)
    NestedScrollView mNestedScrollView;

    public LazyFragment_1() {
        super();
        Log.d(TAG, "LazyFragment_1: ");
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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d(TAG, "setUserVisibleHint: isVisibleToUser = " + isVisibleToUser);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fg_lazy;
    }

    @Override
    protected void initView() {

//        SpannableStringBuilder mSpannableStringBuilder = new SpannableStringBuilder("这是 SpannableStringBuilder ");
//        mSpannableStringBuilder.setSpan(new ClickableSpan() {
//            @Override
//            public void onClick(View widget) {
//                Log.i(TAG, "onClick: ");
//                Toast.makeText(getContext(), "点击我！", Toast.LENGTH_SHORT).show();
//            }
//        }, 0, 10, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//        mLazyTv.setText(mSpannableStringBuilder);
//        mLazyTv.setMovementMethod(LinkMovementMethod.getInstance());//需要设置这行代码，否则点击失效
    }

    public void setTextString(String name) {
        if (mLazyTv != null)
            mLazyTv.setText(name);
    }

    @Override
    public View getScrollTarget() {
        return mNestedScrollView;
    }

    @Override
    public boolean onOffsetChanged(SmoothAppBarLayout smoothAppBarLayout, View target, int verticalOffset) {
        return Utils.syncOffset(smoothAppBarLayout,target,verticalOffset,getScrollTarget());
    }
}
