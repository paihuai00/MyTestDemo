package com.csx.mytestdemo.flow_view;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mlibrary.utils.ToastUtils;
import com.csx.mytestdemo.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Created by cuishuxiang
 * @date 2018/3/22.
 * @description: 流式标签
 */

public class FlowActivity extends BaseActivity {
    private static final String TAG = "FlowActivity";

    @BindView(R.id.flow_views)
    XCFlowLayout mFlowViews;
    @BindView(R.id.flow_layout)
    TagFlowLayout mFlowLayout;

    private String mNames[] = {
            "welcome", "android", "TextView",
            "apple", "jamy", "kobe bryant",
            "jordan", "layout", "viewgroup",
            "margin", "padding", "text",
            "name", "type", "search", "logcat"
    };

    List<String> mStringList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_flow;
    }

    @Override
    public void initView() {
//        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        lp.leftMargin = 5;
//        lp.rightMargin = 5;
//        lp.topMargin = 5;
//        lp.bottomMargin = 5;
//        for (int i = 0; i < mNames.length; i++) {
//            TextView view = new TextView(this);
//            view.setText(mNames[i]);
//            view.setTextColor(Color.WHITE);
//            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_flow_view));
//            mFlowViews.addView(view, lp);
//        }
    }

    @Override
    public void initData() {
        for (int i = 0; i < 20; i++) {
            mStringList.add("string :" + i);
        }

        final LayoutInflater mInflater = LayoutInflater.from(this);
        mFlowLayout.setAdapter(new TagAdapter<String>(mStringList) {
            @Override
            public View getView(FlowLayout parent, int position, String o) {
                Log.d(TAG, "getView: " + mStringList.get(position));
                TextView textView = (TextView) mInflater.inflate(R.layout.item_flow_tv,parent, false);
                textView.setText(mStringList.get(position));
                return textView;
            }
        });

        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                ToastUtils.showShortToast(mStringList.get(position));

                return false;
            }
        });

//        mFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener()
//        {
//            @Override
//            public void onSelected(Set<Integer> selectPosSet)
//            {
//                ToastUtils.showShortToast("choose:" + selectPosSet.toString());
//
//            }
//        });
    }


}
