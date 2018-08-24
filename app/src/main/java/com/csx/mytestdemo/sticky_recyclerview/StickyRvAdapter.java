package com.csx.mytestdemo.sticky_recyclerview;

import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.csx.mytestdemo.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import butterknife.OnTextChanged;

/**
 * Create by cuishuxiang
 *
 * @date: on 2018/6/19
 * @description:
 */

public class StickyRvAdapter extends BaseQuickAdapter<CityBean.DataBean, BaseViewHolder> {

    public StickyRvAdapter(@Nullable List<CityBean.DataBean> data) {
        super(R.layout.item_string, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CityBean.DataBean item) {
        helper.setText(R.id.item_tv, item.getProvincename());
    }

}
