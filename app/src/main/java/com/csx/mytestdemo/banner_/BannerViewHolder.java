package com.csx.mytestdemo.banner_;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.csx.mytestdemo.R;
import com.csx.mytestdemo.banner_.bannerl_ib.holder.MZViewHolder;
import com.csx.mytestdemo.glide4.GlideApp;

/**
 * Create by cuishuxiang
 *
 * @date: on 2018/8/24
 * @description: 轮播图 vh
 */
public class BannerViewHolder implements MZViewHolder<BannerBean> {
    private ImageView image, ad;
    private TextView bannerTitle;

    @Override
    public View createView(Context context) {
        View mView = LayoutInflater.from(context).inflate(R.layout.layout_banner_view, null);
        image = mView.findViewById(R.id.image);
        bannerTitle = mView.findViewById(R.id.bannerTitle);
        return mView;
    }

    @Override
    public void onBind(Context context, int position, BannerBean bannerBean) {
        GlideApp.with(context)
                .load(bannerBean.getPicUrl())
                .into(image);
        bannerTitle.setText(bannerBean.getTitle());
    }
}
