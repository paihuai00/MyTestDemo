package com.csx.mytestdemo.search_demo;

import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebSettings;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.csx.mytestdemo.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * date: 2019/3/26
 * create by cuishuxiang
 * description:
 */
public class SearchAdapter extends BaseQuickAdapter<String, SearchAdapter.ViewHolder> {
    public static final int UserWithNum = 1;//带手机号
    public static final int UserWithNick = 2;//带昵称
    public static final int UserOnly = 3;//只显示用户名

    private int type = 0;

    public SearchAdapter(@Nullable List<String> data, @SearchType int type) {
        super(R.layout.item_search, data);
        this.type = type;
    }

    @Override
    protected void convert(ViewHolder helper, String item) {
        switch (type) {
            case UserOnly:
                helper.setVisible(R.id.tv_num, false);//隐藏电话号
                break;
            case UserWithNick:
                helper.setVisible(R.id.tv_num, true);//显示电话号
                helper.setText(R.id.tv_num, "11111111111111");
                break;
            case UserWithNum:
                helper.setVisible(R.id.tv_num, true);//显示电话号
                helper.setText(R.id.tv_num, "昵称：1111111");
                break;
        }

    }

    class ViewHolder extends BaseViewHolder {
        public ViewHolder(View view) {
            super(view);
        }
    }


    public void setType(@SearchType int type) {
        this.type = type;
    }

    @IntDef({SearchAdapter.UserOnly, SearchAdapter.UserWithNick, UserWithNum})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SearchType {
    }

}

