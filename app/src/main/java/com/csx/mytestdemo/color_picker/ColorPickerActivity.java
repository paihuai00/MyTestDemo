package com.csx.mytestdemo.color_picker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.R;
import com.skydoves.colorpickerpreference.ColorEnvelope;
import com.skydoves.colorpickerpreference.ColorListener;
import com.skydoves.colorpickerpreference.ColorPickerDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Created by cuishuxiang
 * @date 2018/5/18.
 * @description:
 */
public class ColorPickerActivity extends BaseActivity {
    @BindView(R.id.choose_color_btn)
    Button mChooseColorBtn;
    @BindView(R.id.root_color_ll)
    LinearLayout mRootColorLl;

    private AlertDialog mAlertDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_color_picker;
    }

    @Override
    public void initView() {
        ColorPickerDialog.Builder builder = new ColorPickerDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        builder.setTitle("ColorPicker Dialog");
        builder.setPreferenceName("MyColorPickerDialog");
        builder.setFlagView(new CustomFlag(this, R.layout.color_layout_flag));
        builder.setPositiveButton("确定", new ColorListener() {
            @Override
            public void onColorSelected(ColorEnvelope colorEnvelope) {

                mRootColorLl.setBackgroundColor(colorEnvelope.getColor());
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        mAlertDialog = builder.create();
    }

    @Override
    public void initData() {

    }


    @OnClick(R.id.choose_color_btn)
    public void onViewClicked() {
        mAlertDialog.show();

    }
}
