package com.csx.mytestdemo.drag_edittext;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Created by cuishuxiang
 * @date 2018/5/16.
 * @description: 可以拖动/缩放的EditText
 */
public class DragEditTextActivity extends BaseActivity {

    @BindView(R.id.screen_btn)
    Button mScreenBtn;
    @BindView(R.id.show_view_img)
    ImageView mShowViewImg;
    @BindView(R.id.root_view_rl)
    RelativeLayout mRootViewRl;

    @Override
    public int getLayoutId() {
        return R.layout.activity_drag_et;
    }

    @Override
    public void initView() {
        mScreenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = getCacheBitmapFromView(mRootViewRl);
                mShowViewImg.setImageBitmap(bitmap);
            }
        });
    }

    @Override
    public void initData() {

    }

    /**
     * 获取一个 View 的缓存视图
     * (前提是这个View已经渲染完成显示在页面上)
     *
     * @param view
     * @return
     */
    public Bitmap getCacheBitmapFromView(View view) {
        final boolean drawingCacheEnabled = true;
        view.setDrawingCacheEnabled(drawingCacheEnabled);
        view.buildDrawingCache(drawingCacheEnabled);
        final Bitmap drawingCache = view.getDrawingCache();
        Bitmap bitmap;
        if (drawingCache != null) {
            bitmap = Bitmap.createBitmap(drawingCache);
            view.setDrawingCacheEnabled(false);
        } else {
            bitmap = null;
        }
        return bitmap;
    }

}
