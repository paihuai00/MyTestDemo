package com.csx.mytestdemo.skin;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mlibrary.skin.BaseSkinActivity;
import com.csx.mlibrary.skin.SkinManager;
import com.csx.mytestdemo.R;

import java.io.File;
import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Create by cuishuxiang
 *
 * @date: on 2018/10/22
 * @description:
 */
public class SkinActivity extends BaseSkinActivity {
    private static final String TAG = "SkinActivity";

    @BindView(R.id.btn_change)
    Button mBtnChange;
    @BindView(R.id.iv_girl)
    ImageView mIvGirl;
    @BindView(R.id.root)
    LinearLayout mRoot;
    private boolean isChange = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_skin;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {


    }

    @OnClick(R.id.btn_change)
    public void onClick() {
        if (!isChange) {

            SkinManager.getInstance().loadSkin(Environment.getExternalStorageDirectory() + File.separator + "skin_app.skin");

        } else {
            mIvGirl.setImageResource(R.drawable.ic_girl);
        }

        isChange = !isChange;
    }

    private void changeSkin(String skinPath) {
        int result = SkinManager.getInstance().loadSkin(skinPath);
    }

    private void recoverSkin() {
        int result = SkinManager.getInstance().recoverSkin();
    }
}
