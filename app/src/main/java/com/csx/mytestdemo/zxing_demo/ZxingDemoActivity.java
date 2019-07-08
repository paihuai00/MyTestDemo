package com.csx.mytestdemo.zxing_demo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.MainActivity;
import com.csx.mytestdemo.R;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * date: 2019/3/29
 * create by cuishuxiang
 * description:
 *
 *
 * 1,https://github.com/zxing/zxing  二维码扫描
 * 2,https://github.com/yipianfengye/android-zxingLibrary
 *
 *
 * demo Application中执行初始化操作  ZXingLibrary.initDisplayOpinion(this);
 */
public class ZxingDemoActivity extends BaseActivity {

    @BindView(R.id.btn_zxing)
    Button mBtnZxing;


    private int REQUEST_CODE = 1;
    @Override
    public int getLayoutId() {
        return R.layout.activity_zxing;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.btn_zxing)
    public void onViewClicked() {
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
