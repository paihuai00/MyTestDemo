package com.csx.mytestdemo.banner_;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.R;
import com.csx.mytestdemo.banner_.bannerl_ib.MZBannerView;
import com.csx.mytestdemo.banner_.bannerl_ib.holder.MZHolderCreator;
import com.csx.mytestdemo.banner_.bannerl_ib.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Create by cuishuxiang
 *
 * @date: on 2018/8/24
 * @description:
 */
public class BannerActivity extends BaseActivity {
    @BindView(R.id.bann)
    MZBannerView mBanner;
    @BindView(R.id.tv)
    TextView mTv;
    @BindView(R.id.reveal_btn)
    Button mRevealBtn;

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


    @OnClick({R.id.tv, R.id.reveal_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv:
                if (mTv.getAnimation() != null)
                    mTv.getAnimation().reset();
                RotateAnimation mRotateAnimation = new RotateAnimation(-90, -60, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                mRotateAnimation.setDuration(2000);
                mRotateAnimation.setFillAfter(true);//true  固定到旋转后的位置
                mTv.startAnimation(mRotateAnimation);
                break;
            case R.id.reveal_btn:
                int centerX = mRevealBtn.getWidth() / 2;
                int centerY = mRevealBtn.getHeight() / 2;

                //判断api是否大于21
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Animator mAnimator = ViewAnimationUtils.createCircularReveal(mRevealBtn, centerX, centerY, Math.max(centerX, centerY), 0);
                    mAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            //这里可以监听到动画完成的事件；
                            //可以在这里进行gone的操作
                        }
                    });
                    mAnimator.start();
                }


                break;
        }
    }
}
