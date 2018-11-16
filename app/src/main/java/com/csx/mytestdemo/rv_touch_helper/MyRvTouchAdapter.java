package com.csx.mytestdemo.rv_touch_helper;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.csx.mytestdemo.R;

import java.util.List;

/**
 * Create by cuishuxiang
 *
 * @date: on 2018/11/16
 * @description:
 */
public class MyRvTouchAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public MyRvTouchAdapter( @Nullable List<String> data) {
        super(R.layout.item_string, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.item_tv, item);
    }
}
