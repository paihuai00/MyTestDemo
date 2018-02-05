package com.csx.mytestdemo.drag_recyclerview;

import android.graphics.Canvas;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.csx.mlibrary.base.BaseFragment;
import com.csx.mlibrary.utils.ToastUtils;
import com.csx.mytestdemo.R;
import com.csx.mytestdemo.drag_recyclerview.helper.OnStartDragListener;
import com.csx.mytestdemo.drag_recyclerview.helper.SimpleItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Created by cuishuxiang
 * @date 2018/2/5.
 * 可以拖动，滑动删除的rv
 * https://github.com/iPaulPro/Android-ItemTouchHelper-Demo
 */

public class HelperDragFragment extends BaseFragment {
    private static final String TAG = HelperDragFragment.class.getSimpleName().toString();

    @BindView(R.id.drag_rv)
    RecyclerView mDragRv;

    List<String> mStringList;
    private DragHelperRvAdapter mDragHelperRvAdapter;

    private ItemTouchHelper mItemTouchHelper;

    @Override
    public int getLayoutRes() {
        return R.layout.fg_drag_helper;
    }

    @Override
    protected void initView() {
        mStringList = new ArrayList<>();
        mStringList.add("使用TouchHelper实现拖拽/滑动删除功能！");
        for (int i = 0; i < 20; i++) {
            mStringList.add("This is : " + i);
        }
        mDragRv.setLayoutManager(new LinearLayoutManager(getContext()));

        mDragHelperRvAdapter = new DragHelperRvAdapter(mStringList, getContext());

        mDragHelperRvAdapter.setOnStartDragListener(new OnStartDragListener() {
            @Override
            public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
                mItemTouchHelper.startDrag(viewHolder);
                Log.d(TAG, "onStartDrag: ");
            }
        });

        mDragRv.setAdapter(mDragHelperRvAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mDragHelperRvAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mDragRv);
    }

}
