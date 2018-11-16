package com.csx.mytestdemo.rv_touch_helper;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import java.util.Collections;
import java.util.List;

/**
 * Create by cuishuxiang
 *
 * @date: on 2018/11/16
 * @description:
 */
public class MyTouchHelper extends ItemTouchHelper.Callback {
    private static final String TAG = "MyTouchHelper";

    private int dragFlags;//拖拽标志
    private int swipeFlags;//滑动标志

    RecyclerView.Adapter madapter;
    private List<String> results;
    public MyTouchHelper(RecyclerView.Adapter adapter, List<String> lists) {
        this.results = lists;
        this.madapter = adapter;
    }

    /**
     * 用于设置是否处理拖拽事件和滑动事件，以及拖拽和滑动操作的方向，有以下两种情况：
     * 1,如果是列表类型的，拖拽只有ItemTouchHelper.UP、ItemTouchHelper.DOWN两个方向
     * 2,如果是网格类型的，拖拽则有UP、DOWN、LEFT、RIGHT四个方向
     * @param recyclerView
     * @param viewHolder
     * @return
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        Log.i(TAG, "getMovementFlags: ");
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            swipeFlags = 0;
        } else {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            swipeFlags = 0;
        }
        //swipeFlags 都设置为0，表示不处理滑动操作。
        return makeMovementFlags(dragFlags,swipeFlags);
    }

    /**
     * 如果我们设置了非0的dragFlags ，那么当我们长按item的时候就会进入拖拽并在拖拽过程中不断回调onMove()方法，
     * 我们就在这个方法里获取当前拖拽的item和已经被拖拽到所处位置的item的ViewHolder，
     * 有了这2个ViewHolder，我们就可以交换他们的数据集并调用
     * Adapter的notifyItemMoved方法来刷新item。
     *
     * @param recyclerView
     * @param viewHolder
     * @param target
     * @return
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        Log.i(TAG, "onMove: ");
        int fromPosition = viewHolder.getAdapterPosition();//得到拖动ViewHolder的position
        int toPosition = target.getAdapterPosition();//得到目标ViewHolder的position
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(results, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(results, i, i - 1);
            }
        }
        madapter.notifyItemMoved(fromPosition, toPosition);
        return true;

    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        Log.i(TAG, "onSwiped: direction = " + direction);
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        Log.i(TAG, "onSelectedChanged: actionState = " + actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        Log.i(TAG, "clearView: ");
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return super.isLongPressDragEnabled();
    }

    @Override
    public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int fromPos, RecyclerView.ViewHolder target, int toPos, int x, int y) {
        super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);

    }
}
