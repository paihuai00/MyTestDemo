package com.csx.mytestdemo.drag_recyclerview;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.csx.mytestdemo.R;

import java.util.List;

/**
 * @Created by cuishuxiang
 * @date 2018/2/5.
 * <p>
 *
 * 可以拖动的Rv  adapter；
 *
 */

public class DragBrvahRvAdapter extends BaseItemDraggableAdapter<String, DragBrvahRvAdapter.ViewHolder> {


    public DragBrvahRvAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(ViewHolder helper, String item) {
        helper.text1.setText(item);
    }

    class ViewHolder extends BaseViewHolder {
        TextView text1;
        ImageView drag_iv;

        public ViewHolder(View view) {
            super(view);
            text1 = view.findViewById(R.id.item_tv);
            drag_iv = view.findViewById(R.id.drag_iv);
        }
    }
}

