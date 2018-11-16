package com.csx.mytestdemo.bottom_dialog;

import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mlibrary.dialog.CommonDialog;
import com.csx.mlibrary.widget.CountDownTextView;
import com.csx.mytestdemo.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Create by cuishuxiang
 *
 * @date: on 2018/7/26
 * @description:
 */

public class BottomDialogActivity extends BaseActivity {
    private static final String TAG = "BottomDialogActivity";
    @BindView(R.id.show_dialog_btn)
    Button mShowDialogBtn;
    @BindView(R.id.show_popup_btn)
    Button mShowPopupBtn;

    View mZhanweiView;
    @BindView(R.id.time_down)
    CountDownTextView mTimeDown;

    private CommonDialog mCommonDialog;
    private View mView;

    private PopupWindow mPopupWindow;

    @Override
    public int getLayoutId() {
        return R.layout.activity_bottom_dialog;
    }

    @Override
    public void initView() {
        mView = LayoutInflater.from(this).inflate(R.layout.dialog_text, null);

        mZhanweiView = mView.findViewById(R.id.zhanwei_view);
        mZhanweiView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopupWindow != null)
                    mPopupWindow.dismiss();
            }
        });
//        mCommonDialog = new CommonDialog.Builder(this)
//                .setContentView(mView)
//                .fullWidth()
//                .showFromTop(true)
//                .create();

        initPopupWindow();


        mTimeDown.setNormalText("获取验证码")
                .setCountDownText("重新获取(", ")")
                .setCloseKeepCountDown(true)//关闭页面保持倒计时开关
                .setCountDownClickable(true)//倒计时期间点击事件是否生效开关
                .setShowFormatTime(true)//是否格式化时间
                .setOnCountDownFinishListener(new CountDownTextView.OnCountDownFinishListener() {
                    @Override
                    public void onFinish() {
                        Toast.makeText(BottomDialogActivity.this, "倒计时完毕", Toast.LENGTH_SHORT).show();
                    }
                })
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(BottomDialogActivity.this, "短信已发送", Toast.LENGTH_SHORT).show();
                        mTimeDown.startCountDown(10);
                    }
                });

    }

    private void initPopupWindow() {

        mPopupWindow = new PopupWindow(mView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setFocusable(true);
        mPopupWindow.setAnimationStyle(R.style.popwin_anim_style);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                                              @Override
                                              public void onDismiss() {
                                                  WindowManager.LayoutParams params = getWindow().getAttributes();
                                                  params.alpha = 1f;
                                                  getWindow().setAttributes(params);
                                              }
                                          }
        );


    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.show_dialog_btn, R.id.show_popup_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.show_dialog_btn:
                if (!mCommonDialog.isShowing())
                    mCommonDialog.show();
                else
                    mCommonDialog.dismiss();
                break;
            case R.id.show_popup_btn:
//                WindowManager.LayoutParams params = getWindow().getAttributes();
//                params.alpha = 0.7f;
//                getWindow().setAttributes(params);
                mPopupWindow.showAsDropDown(mShowPopupBtn, 0, 2);
                break;
        }
    }
}
