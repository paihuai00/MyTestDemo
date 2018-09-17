package com.csx.mytestdemo.coordinate_layout;

import android.content.Context;
import android.widget.TextView;

import com.csx.mytestdemo.R;
import com.csx.mytestdemo.coordinate_layout.recyclerView.BaseRecyclerAdapter;
import com.csx.mytestdemo.coordinate_layout.recyclerView.BaseRecyclerHolder;

import java.util.List;

/**
 * Create by cuishuxiang
 *
 * @date: on 2018/8/31
 * @description:
 */
public class RvAdapter extends BaseRecyclerAdapter<String> {
    public RvAdapter(Context context, List<String> strings) {
        super(context, R.layout.item_string, strings);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, String item, int position) {
        TextView tv=holder.getView(R.id.item_tv);
        tv.setText(item);
    }
}
