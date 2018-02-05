package com.csx.mytestdemo.drag_recyclerview;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Created by cuishuxiang
 * @date 2018/2/5.
 *
 * 使用两种方式，实现recyclerview 的拖拽，滑动删除功能
 */

public class DragActivity extends BaseActivity {
    private static final String TAG = "DragActivity";
    @BindView(R.id.drag_tl)
    TabLayout mDragTl;
    @BindView(R.id.drag_vp)
    ViewPager mDragVp;
    private DragPagerAdapter mDragPagerAdapter;
    private BravhDragFragment mBravhDragFragment;
    private HelperDragFragment mHelperDragFragment;


    @Override
    public int getLayoutId() {
        return R.layout.activity_drag;
    }

    @Override
    public void initView() {
        mDragPagerAdapter = new DragPagerAdapter(getSupportFragmentManager());

        mBravhDragFragment = new BravhDragFragment();
        mHelperDragFragment = new HelperDragFragment();

        mDragVp.setAdapter(mDragPagerAdapter);

        mDragTl.setupWithViewPager(mDragVp);

    }

    @Override
    public void initData() {

    }

    /***
     * viewpager adapter
     */
    class DragPagerAdapter extends FragmentPagerAdapter {
        private String[] mTitles = new String[]{"BRVAH", "Helper"};

        public DragPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return position == 0 ? mBravhDragFragment : mHelperDragFragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }

}
