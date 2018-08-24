package com.csx.mytestdemo.banner_;

import android.view.View;
import android.widget.Toast;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.R;
import com.csx.mytestdemo.banner_.bannerl_ib.MZBannerView;
import com.csx.mytestdemo.banner_.bannerl_ib.holder.MZHolderCreator;
import com.csx.mytestdemo.banner_.bannerl_ib.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Create by cuishuxiang
 *
 * @date: on 2018/8/24
 * @description:
 */
public class BannerActivity extends BaseActivity {
    @BindView(R.id.bann)
    MZBannerView mBanner;

    @Override
    public int getLayoutId() {
        return R.layout.activity_banner;
    }

    @Override
    public void initView() {
        mBanner.setIndicatorRes(R.drawable.img_carousel_2, R.drawable.img_carousel_1);//设置指示器

        //banner  点击事件
        mBanner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int position) {
                Toast.makeText(BannerActivity.this, "点击了：" + position, Toast.LENGTH_SHORT).show();
            }
        });

        List<BannerBean> bannerBeans = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            BannerBean bean = new BannerBean();
            bean.setUrl("https://ss0.baidu.com/94o3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=10b374237f0e0cf3bff748fb3a47f23d/adaf2edda3cc7cd90df1ede83401213fb80e9127.jpg");
            bean.setTitle("这是：" + i);
            bean.setPicUrl("");
            bean.setAdShow(false);
            bannerBeans.add(bean);
        }
        mBanner.setPages(bannerBeans, new MZHolderCreator() {
            @Override
            public MZViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });
    }

    @Override
    public void initData() {

    }
}
