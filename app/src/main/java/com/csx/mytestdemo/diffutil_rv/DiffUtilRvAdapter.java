package com.csx.mytestdemo.diffutil_rv;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.csx.mytestdemo.R;

import java.util.List;

/**
 * Create by cuishuxiang
 *
 * @date: on 2018/6/14
 * @description:
 */

public class DiffUtilRvAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public DiffUtilRvAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.getView(R.id.item_tv).setBackgroundColor(mContext.getResources().getColor(R.color.light_orange));
        helper.setText(R.id.item_tv, item);
    }
}
