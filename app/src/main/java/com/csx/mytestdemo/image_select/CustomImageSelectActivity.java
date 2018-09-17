package com.csx.mytestdemo.image_select;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.R;
import com.csx.mytestdemo.glide4.GlideApp;
import com.csx.mytestdemo.image_select.image.MultiImageSelector;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

/**
 * Create by cuishuxiang
 *
 * @date: on 2018/9/17
 * @description:
 */
@RuntimePermissions
public class CustomImageSelectActivity extends BaseActivity {
    private static final String TAG = "CustomImageSelectActivity";
    @BindView(R.id.btn_select)
    Button mBtnSelect;
    @BindView(R.id.iv_show_img)
    ImageView mIvShowImg;

    @Override
    public int getLayoutId() {
        return R.layout.activity_image_select;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
    @OnClick(R.id.btn_select)
    public void onClick() {
        MultiImageSelector
                .create()
                .single()
                .start(CustomImageSelectActivity.this);
    }

    @SuppressLint("LongLogTag")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: ");


        ArrayList<String> imageLists = (ArrayList<String>) data.getSerializableExtra("select_result");
        GlideApp.with(this)
                .load(imageLists.get(0))
                .error(R.drawable.default_error)
                .placeholder(R.drawable.default_error)
                .into(mIvShowImg);
    }


    @OnPermissionDenied({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
    void onPermissionReJect() {
        showTipsDialog(this);
    }

    /**
     * 显示提示对话框
     */
    private void showTipsDialog(final Context context) {

        new AlertDialog.Builder(context)
                .setTitle("提示信息")
                .setMessage("当前应用缺少必要权限，该功能暂时无法使用。如若需要，请单击【确定】按钮前往设置中心进行权限授权。")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings(context);
                    }
                }).show();
    }

    /**
     * 启动当前应用设置页面
     */
    private void startAppSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }
}
