package com.csx.mytestdemo.view_touch_scroll;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Created by cuishuxiang
 * @date 2018/3/5.
 * Touch滑动冲突实践
 * <p>
 * 竖向Scroll 和 ListView的冲突解决
 */

public class TouchScrollActivity extends BaseActivity {
    private static final String TAG = "NineDotActivity";
    @BindView(R.id.scroll_root_view)
    LinearLayout mScrollRootView;
    @BindView(R.id.scroll_lv)
    ListView mScrollLv;

    @Override
    public int getLayoutId() {
        return R.layout.activity_touch_scroll;
    }

    @Override
    public void initView() {
        List<String> mDataList = new ArrayList<>();
        for (int j = 0; j < 20; j++) {
            mDataList.add("This is page  current index = " + j);
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mDataList);

        mScrollLv.setAdapter(arrayAdapter);


    }

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
