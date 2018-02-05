package com.csx.mytestdemo.drag_recyclerview;

import android.app.Service;
import android.graphics.Canvas;
import android.os.Vibrator;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Created by cuishuxiang
 * @date 2018/2/5.
 * 可以拖动，滑动删除的rv  使用：BRVAH框架
 * https://www.jianshu.com/p/b343fcff51b0
 *
 * 1，adapter需要继承 BaseItemDraggableAdapter
 * 2, 拖拽，滑动的添加，见下面 具体方法；
 * 3，
 */

public class BravhDragFragment extends BaseFragment {
    private static final String TAG = BravhDragFragment.class.getSimpleName().toString();

    @BindView(R.id.drag_rv)
    RecyclerView mDragRv;
    private DragBrvahRvAdapter mDragRvAdapter;

    private ItemTouchHelper mItemTouchHelper;
    private ItemDragAndSwipeCallback mItemDragAndSwipeCallback;


    List<String> mStringList;

    @Override
    public int getLayoutRes() {
        return R.layout.fg_drag_brvah;
    }

    @Override
    protected void initView() {
        mStringList = new ArrayList<>();
        mStringList.add("使用BRVAH框架，实现拖拽，滑动删除功能！");
        for (int i = 0; i < 20; i++) {
            mStringList.add("This is : " + i);
        }
        mDragRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mDragRvAdapter = new DragBrvahRvAdapter(R.layout.item_drag_rv, mStringList);

        //item点击事件
        initAdapterItemListener();

        mItemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mDragRvAdapter);
        mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
        //设置滑动的方向
        mItemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START | ItemTouchHelper.END);
        mItemTouchHelper.attachToRecyclerView(mDragRv);

        initAdapterDragListener();

        initAdapterSwipeListener();

        mDragRv.setAdapter(mDragRvAdapter);
    }


    /**
     * item 滑动相关
     */
    private void initAdapterSwipeListener() {
        //设置可以滑动
        mDragRvAdapter.enableSwipeItem();
        //滑动监听
        mDragRvAdapter.setOnItemSwipeListener(new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.d(TAG, "onItemSwipeStart: ");
            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.d(TAG, "clearView: ");

            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.d(TAG, "onItemSwiped: ");
            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
                Log.d(TAG, "onItemSwipeMoving: ");
            }
        });
    }

    /**
     * item 拖拽相关
     * 开始拖拽，添加一个震动；需要声明权限
     */
    private void initAdapterDragListener() {
        mDragRvAdapter.enableDragItem(mItemTouchHelper);
        mDragRvAdapter.setOnItemDragListener(new OnItemDragListener() {
            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.d(TAG, "onItemDragStart: ");
                //获取系统震动服务
                Vibrator vib = (Vibrator) getActivity().getSystemService(Service.VIBRATOR_SERVICE);
                //震动70毫秒
                vib.vibrate(70);
            }

            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
                //source 是移动前； target 是移动后
                Log.d(TAG, "move from: " + source.getAdapterPosition() + " to: " + target.getAdapterPosition());
            }

            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.d(TAG, "onItemDragEnd: ");
                BaseViewHolder baseViewHolder = (BaseViewHolder) viewHolder;

                baseViewHolder.setText(R.id.item_tv, "滑动到了：" + pos);

            }
        });
    }

    private void initAdapterItemListener() {
        mDragRvAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d(TAG, "onItemClick: " + position);
                ToastUtils.showShortToast("onItemClick: " + position);
            }
        });

    }
}
