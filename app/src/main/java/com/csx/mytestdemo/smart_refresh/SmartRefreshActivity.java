package com.csx.mytestdemo.smart_refresh;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.OnTwoLevelListener;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import butterknife.BindView;

/**
 * Create by cuishuxiang
 *
 * @date: on 2018/7/26
 * @description:
 */

public class SmartRefreshActivity extends BaseActivity {
    private static final String TAG = "SmartRefreshActivity";
    @BindView(R.id.classics)
    ClassicsHeader mClassics;
    @BindView(R.id.header)
    TwoLevelHeader mHeader;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.second_floor)
    ImageView floor;

    @Override
    public int getLayoutId() {
        return R.layout.activity_smart_refresh;
    }

    @Override
    public void initView() {
        mRefreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener(){
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(2000);
            }
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                Toast.makeText(getBaseContext(),"触发刷新事件",Toast.LENGTH_SHORT).show();
                refreshLayout.finishRefresh(2000);
            }
            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {

//                floor.setTranslationY(Math.min(offset - floor.getHeight() + toolbar.getHeight(), refreshLayout.getLayout().getHeight() - floor.getHeight()));
            }
        });

        mHeader.setOnTwoLevelListener(new OnTwoLevelListener() {
            @Override
            public boolean onTwoLevel(@NonNull RefreshLayout refreshLayout) {
//                Toast.makeText(getBaseContext(),"触发二楼事件",Toast.LENGTH_SHORT).show();
////                floor.findViewById(R.id.secondfloor_content).animate().alpha(1).setDuration(2000);
//                refreshLayout.getLayout().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        mHeader.finishTwoLevel();
////                        root.findViewById(R.id.secondfloor_content).animate().alpha(0).setDuration(1000);
//                    }
//                },5000);

                return true;//true 将会展开二楼状态 false 关闭刷新
            }
        });
    }

    @Override
    public void initData() {

    }
}
