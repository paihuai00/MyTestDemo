package com.csx.mytestdemo.view_slide_menu;

import android.os.Bundle;
import android.widget.Button;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mlibrary.utils.ToastUtils;
import com.csx.mytestdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Created by cuishuxiang
 * @date 2018/3/13.
 * @description: 仿 酷狗音乐，侧滑菜单
 */

public class SlideMenuActivity extends BaseActivity {
    @BindView(R.id.show_slide_btn)
    Button mShowSlideBtn;

    @Override
    public int getLayoutId() {
        return R.layout.activity_slide_menu;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.show_slide_btn)
    public void onViewClicked() {
        ToastUtils.showShortToast("点击 content 按钮！");
    }
}
