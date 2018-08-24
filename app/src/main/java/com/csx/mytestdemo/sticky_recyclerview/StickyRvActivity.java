package com.csx.mytestdemo.sticky_recyclerview;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Create by cuishuxiang
 *
 * @date: on 2018/6/19
 * @description:
 */

public class StickyRvActivity extends BaseActivity {
    private static final String TAG = "StickyRvActivity";
    @BindView(R.id.sticky_rv)
    RecyclerView mStickyRv;
    private List<CityBean.DataBean> mCityDatas;
    private StickyRvAdapter mStickyRvAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_sticky_rv;
    }

    @Override
    public void initView() {
        //1,添加数据
        mCityDatas = new ArrayList<>();
        //json 数据，从assets 中的文件获取
        mCityDatas.addAll(CityJsonString.getCityBean(this).getData());

        mStickyRvAdapter = new StickyRvAdapter(mCityDatas);

        mStickyRv.setLayoutManager(new LinearLayoutManager(this));

        mStickyRv.addItemDecoration(new MyStickyDecoration(this));

        mStickyRv.setAdapter(mStickyRvAdapter);

    }

    @Override
    public void initData() {

    }
}
