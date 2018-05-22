package com.csx.mytestdemo.photoview;

import android.os.Bundle;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.R;
import com.github.chrisbanes.photoview.PhotoView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Created by cuishuxiang
 * @date 2018/5/19.
 * @description:
 */
public class PhotoViewActivity extends BaseActivity {
    @BindView(R.id.photoview)
    PhotoView mPhotoview;

    @Override
    public int getLayoutId() {
        return R.layout.activity_photoview;
    }

    @Override
    public void initView() {
        mPhotoview.setImageDrawable(getResources().getDrawable(R.drawable.ic_girl));
    }

    @Override
    public void initData() {

    }


}
