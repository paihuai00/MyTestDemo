package com.csx.mytestdemo.lazy_fg;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Create by cuishuxiang
 *
 * @date: on 2018/6/21
 * @description:
 */

public class LazyFgActivity extends BaseActivity {
    private static final String TAG = "LazyFgActivity";
    @BindView(R.id.tb_lazy)
    TabLayout mTbLazy;
    @BindView(R.id.vp_lazy)
    ViewPager mVpLazy;
    List<Fragment> mFragmentList;

    private VpAdapter mVpAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_lazy_fg;
    }

    @Override
    public void initView() {
        mFragmentList = new ArrayList<>();
        LazyFragment_1 lazyFragment_1 = new LazyFragment_1();
        mFragmentList.add(lazyFragment_1);
        LazyFragment_2 lazyFragment_2 = new LazyFragment_2();
        mFragmentList.add(lazyFragment_2);
        LazyFragment_3 lazyFragment_3 = new LazyFragment_3();
        mFragmentList.add(lazyFragment_3);
        LazyFragment_4 lazyFragment_4 = new LazyFragment_4();
        mFragmentList.add(lazyFragment_4);


        mVpAdapter = new VpAdapter(getSupportFragmentManager());

        mVpLazy.setAdapter(mVpAdapter);
        mTbLazy.setupWithViewPager(mVpLazy);
    }

    @Override
    public void initData() {

    }

    class VpAdapter extends FragmentPagerAdapter {
        String[] titles = new String[]{"Tab_1", "Tab_2", "Tab_3", "Tab_4"};

        public VpAdapter(FragmentManager fm) {
            super(fm);
        }


        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }


    }

}
