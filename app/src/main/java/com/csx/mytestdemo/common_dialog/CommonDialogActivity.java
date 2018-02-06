package com.csx.mytestdemo.common_dialog;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mlibrary.dialog.CommonDialog;
import com.csx.mlibrary.utils.ToastUtils;
import com.csx.mytestdemo.R;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Created by cuishuxiang
 * @date 2018/2/2.
 */

public class CommonDialogActivity extends BaseActivity {
    private static final String TAG = "CommonDialogActivity";
    @BindView(R.id.dialog_show_btn)
    Button mDialogShowBtn;

    @Override
    public int getLayoutId() {
        return R.layout.activity_dialog;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.dialog_show_btn)
    public void onViewClicked() {

        /**
         * 正常使用，可以链式调用；
         */
        CommonDialog commonDialog = new CommonDialog.Builder(this)
                .setCancelable(true)
                .setContentView(R.layout.dialog_common)
                .setOnClickListener(R.id.dialog_send_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.showShortToast("点击了：" + v.getId());
                    }
                })
                .fullWidth()
                .showFromBottom(true)
                .create();

        /**
         * 如果需要找到里面的某个view，需要这样调用
         */
        final EditText editText = (EditText) commonDialog.getViewById(R.id.dialog_et);

        commonDialog.setOnClickListener(R.id.dialog_send_btn, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShortToast(editText.getText().toString().trim());
            }
        });

        /**
         * 显示弹框
         */
        commonDialog.show();

    }
}
