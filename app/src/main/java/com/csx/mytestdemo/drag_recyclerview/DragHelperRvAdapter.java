package com.csx.mytestdemo.drag_recyclerview;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.csx.mytestdemo.R;
import com.csx.mytestdemo.drag_recyclerview.helper.ItemTouchHelperAdapter;
import com.csx.mytestdemo.drag_recyclerview.helper.ItemTouchHelperViewHolder;
import com.csx.mytestdemo.drag_recyclerview.helper.OnStartDragListener;

import java.util.Collections;
import java.util.List;

/**
 * @Created by cuishuxiang
 * @date 2018/2/5.
 *
 * 使用：
 * 1，adapter需要实现ItemTouchHelperAdapter
 * 2，viewholder 需要实现 ItemTouchHelperViewHolder
 */

public class DragHelperRvAdapter extends RecyclerView.Adapter<DragHelperRvAdapter.ViewHolder>
        implements ItemTouchHelperAdapter {
    private static final String TAG = "DragHelperRvAdapter";
    private List<String> mStringList;
    private Context mContext;
    private OnStartDragListener dragStartListener;

    public DragHelperRvAdapter(List<String> stringList, Context context) {
        mStringList = stringList;
        mContext = context;
    }

    public void setOnStartDragListener(OnStartDragListener dragStartListener) {
        this.dragStartListener = dragStartListener;
    }

    /**
     * 拖动排序  使用Collections.swap ，来对list进行角标互换
     * @param fromPosition The start position of the moved item.
     * @param toPosition   Then resolved position of the moved item.
     * @return
     */
    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mStringList, fromPosition, toPosition);
        //提醒列表刷新
        notifyItemMoved(fromPosition, toPosition);
        Log.d(TAG, "onItemMove: fromPosition = " + fromPosition + " toPosition = " + toPosition);
        return true;
    }

    //滑动删除
    @Override
    public void onItemDismiss(int position) {
        mStringList.remove(position);
        notifyItemRemoved(position);
        Log.d(TAG, "onItemDismiss: 删除 = " + position + "当前剩余： " + getItemCount());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = null;
        ViewHolder mViewHolder = null;
        if (mView == null) {
            mView = LayoutInflater.from(mContext).inflate(R.layout.item_drag_rv, parent, false);
            mViewHolder = new ViewHolder(mView);
        }

        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.item_tv.setText(mStringList.get(position));

        holder.drag_iv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (dragStartListener==null) return false;

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    dragStartListener.onStartDrag(holder);
                }

                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mStringList == null ? 0 : mStringList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
        TextView item_tv;
        ImageView drag_iv;
        public ViewHolder(View itemView) {
            super(itemView);
            drag_iv = itemView.findViewById(R.id.drag_iv);
            item_tv = itemView.findViewById(R.id.item_tv);
        }

        @Override
        public void onItemSelected() {
            //设置点击时背景色
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }
}

