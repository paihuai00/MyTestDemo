package com.csx.mytestdemo.coordinate_layout;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.http.GET;

/**
 * Create by cuishuxiang
 *
 * @date: on 2018/8/31
 * @description:
 */
public class SimpleBehaviorActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_title)
    TextView mTvTitle;

    RvAdapter mRvAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_behavior;
    }

    @Override
    public void initView() {
        List<String> mStringList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            mStringList.add("This is " + i);
        }
        mRvAdapter = new RvAdapter(this, mStringList);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mRvAdapter);
    }

    @Override
    public void initData() {

    }
}
