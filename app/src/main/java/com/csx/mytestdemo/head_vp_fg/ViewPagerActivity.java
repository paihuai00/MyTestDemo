package com.csx.mytestdemo.head_vp_fg;

import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.R;
import com.csx.mytestdemo.lazy_fg.LazyFragment_1;
import com.csx.mytestdemo.lazy_fg.LazyFragment_2;
import com.csx.mytestdemo.lazy_fg.LazyFragment_3;
import com.csx.mytestdemo.lazy_fg.LazyFragment_4;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.henrytao.smoothappbarlayout.SmoothAppBarLayout;
import me.henrytao.smoothappbarlayout.base.ObservableFragment;
import me.henrytao.smoothappbarlayout.base.ObservablePagerAdapter;

/**
 * Create by cuishuxiang
 *
 * @date: on 2018/8/29
 * @description: https://github.com/henrytao-me/smooth-app-bar-layout
 */
public class ViewPagerActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.smooth_app_bar_layout)
    SmoothAppBarLayout mSmoothAppBarLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mStrings = {"TAB_1", "TAB_2", "TAB_3", "TAB_4"};
    private VpAdapter mVpAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_vp_fg;
    }

    @Override
    public void initView() {
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        initFgs();
    }

    private void initFgs() {
        mFragments.add(new LazyFragment_1());
        mFragments.add(new LazyFragment_2());
        mFragments.add(new LazyFragment_3());
        mFragments.add(new LazyFragment_4());

        mVpAdapter = new VpAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mVpAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void initData() {

    }


    class VpAdapter extends FragmentPagerAdapter implements ObservablePagerAdapter {

        public VpAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mStrings[position];
        }

        @Override
        public ObservableFragment getObservableFragment(int position) {
            if (getItem(position) instanceof ObservableFragment) {
                return (ObservableFragment) getItem(position);
            }
            return null;
        }
    }


}
