package com.csx.mytestdemo.search_demo;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * date: 2019/3/26
 * create by cuishuxiang
 * description:
 */
public class SearchActivity extends BaseActivity {
    private static final String TAG = "SearchActivity";
    @BindView(R.id.XTextView)
    TextView mXTextView;
    @BindView(R.id.xet_search)
    EditText mXetSearch;
    @BindView(R.id.xbt_voicesearch)
    Button mXbtVoicesearch;
    @BindView(R.id.xtv_cancel)
    TextView mXtvCancel;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.btn_1)
    Button mBtn1;
    @BindView(R.id.btn_2)
    Button mBtn2;
    @BindView(R.id.btn_3)
    Button mBtn3;

    private SearchAdapter mAdapter;
    private List<String> stringList = new ArrayList<>();


    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SearchAdapter(stringList, SearchAdapter.UserOnly);
        mRv.setAdapter(mAdapter);


    }

    @OnTextChanged(R.id.xet_search)
    public void onEditTextChange(CharSequence var1, int var2, int var3, int var4) {
        Log.d(TAG, "onEditTextChange: " + var1);
    }


    @Override
    public void initData() {

    }

    @OnClick({R.id.btn_1, R.id.btn_2, R.id.btn_3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_1:
                break;
            case R.id.btn_2:
                break;
            case R.id.btn_3:
                break;
        }
    }
}
