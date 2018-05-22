package com.csx.mytestdemo.glide4;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Created by cuishuxiang
 * @date 2018/5/21.
 * @description: Glide4.0+
 */
public class Glide4Activity extends BaseActivity {
    private static final String TAG = "Glide4Activity";

    String imgUrl = "http://img5.adesk.com/5ab8cef4e7bce7355224a6cb?imageMogr2/thumbnail/!720x1280r/gravity/Center/crop/720x1280";
    @BindView(R.id.glide_iv)
    ImageView mGlideIv;
    ProgressDialog progressDialog;
    @BindView(R.id.show_progress_tv)
    TextView mShowProgressTv;
    @BindView(R.id.circle_progressView)
    CircleProgressView mCircleProgressView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_glide;
    }

    @Override
    public void initView() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage("加载中");
        ProgressInterceptor.addListener(imgUrl, new ProgressListener() {
            @Override
            public void onProgress(int progress) {
                Log.d(TAG, "onProgress: " + progress);
                progressDialog.setProgress(progress);
                mShowProgressTv.setText("图片加载进度：" + progress);
                mCircleProgressView.setProgress(progress);
            }
        });

        SimpleTarget<Drawable> simpleTarge = new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                progressDialog.dismiss();
                mGlideIv.setImageDrawable(resource);
                Log.d(TAG, "onResourceReady: ");
                ProgressInterceptor.removeListener(imgUrl);
            }

            @Override
            public void onStart() {
                super.onStart();
                Log.d(TAG, "onStart: ");
                progressDialog.show();
            }
        };
        GlideApp.with(this)
                .load(imgUrl)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(simpleTarge);

    }

    @Override
    public void initData() {

    }

}
