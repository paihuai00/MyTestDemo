package com.csx.mytestdemo.diffutil_rv;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Create by cuishuxiang
 *
 * @date: on 2018/6/14
 * @description: DiffUtil 工具类  使用介绍
 */

public class DiffUtilsActivity extends BaseActivity {
    private static final String TAG = "DiffUtilsActivity";
    @BindView(R.id.rv_diff_utils)
    RecyclerView mRvDiffUtils;

    private List<String> mStringList;
    private List<String> mNewStringList;


    private DiffUtilRvAdapter mDiffUtilRvAdapter;

    private DiffUtil.DiffResult diffUtil;

    @Override
    public int getLayoutId() {

        return R.layout.activity_diff_utils;
    }

    @Override
    public void initView() {
        mStringList = new ArrayList<>();


        for (int i = 0; i < 100; i++) {
            mStringList.add("点击删除item: " + i);
        }
        mNewStringList = mStringList;

        mDiffUtilRvAdapter = new DiffUtilRvAdapter(R.layout.item_string, mStringList);

        mRvDiffUtils.setLayoutManager(new LinearLayoutManager(this));

        mRvDiffUtils.setAdapter(mDiffUtilRvAdapter);

        mDiffUtilRvAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                mStringList.clear();
                for (int i = 0; i < 10; i++) {
                    mStringList.add("new:" + i);
                }

                mDiffUtilRvAdapter.notifyDataSetChanged();
            }
        });


        mDiffUtilRvAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                mStringList.clear();

                for (int i = 0; i < 10; i++) {
                    mNewStringList.add("new:" + i);
                }
//                mNewStringList.remove(position);
                diffRefresh(mStringList, mNewStringList);
                return false;
            }
        });

    }

    private void diffRefresh(final List<String> stringList, final List<String> newStringList) {
        diffUtil = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return stringList.size();
            }

            @Override
            public int getNewListSize() {
                return newStringList.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return stringList.get(oldItemPosition).equals(newStringList.get(newItemPosition));
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return stringList.get(oldItemPosition).equals(newStringList.get(newItemPosition));
            }
        });

        diffUtil.dispatchUpdatesTo(mDiffUtilRvAdapter);
    }

    @Override
    public void initData() {

    }
}
