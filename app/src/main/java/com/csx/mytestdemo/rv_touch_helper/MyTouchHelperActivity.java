package com.csx.mytestdemo.rv_touch_helper;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Create by cuishuxiang
 *
 * @date: on 2018/11/16
 * @description:
 */
public class MyTouchHelperActivity extends BaseActivity {

    @BindView(R.id.rv)
    RecyclerView mRv;

    private MyRvTouchAdapter mAdapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_touch_helper;
    }

    @Override
    public void initView() {
        List<String> mStringList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mStringList.add("长按移动： " + i);
        }

        mAdapter = new MyRvTouchAdapter(mStringList);
        mRv.setLayoutManager(new GridLayoutManager(this, 4));
        mRv.setAdapter(mAdapter);

        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(new MyTouchHelper(mAdapter,mStringList));
        mItemTouchHelper.attachToRecyclerView(mRv);

    }

    @Override
    public void initData() {

    }
}
