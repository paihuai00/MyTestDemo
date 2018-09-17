package com.csx.mytestdemo.transform_anim;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.widget.ImageView;
import android.widget.Switch;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.R;
import com.csx.mytestdemo.glide4.GlideApp;

import butterknife.BindView;

/**
 * Create by cuishuxiang
 *
 * @date: on 2018/8/25
 * @description:
 *
 * <item name="android:windowContentTransitions">true</item>
 */
public class TransformActivity extends BaseActivity {

    @BindView(R.id.iv_transform)
    ImageView mIvTransform;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch (getIntent().getStringExtra("flag")) {
            case "explode":
                //分解转场动画
                getWindow().setEnterTransition(new Explode().setDuration(2000));
                getWindow().setExitTransition(new Explode().setDuration(2000));
                break;
            case "slide":
                getWindow().setEnterTransition(new Slide().setDuration(2000));
                getWindow().setExitTransition(new Slide().setDuration(2000));
                break;
            case "fade":
                getWindow().setEnterTransition(new Fade().setDuration(2000));
                getWindow().setExitTransition(new Fade().setDuration(2000));
                break;

        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_transform;
    }

    @Override
    public void initView() {
        GlideApp.with(
                this)
                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535188137800&di=be6fc584f0d456a844f9e3461b1c1d50&imgtype=0&src=http%3A%2F%2Fe.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2Fb151f8198618367a2e8a46ee23738bd4b31ce586.jpg")
                .into(mIvTransform);


    }

    @Override
    public void initData() {

    }
}
