package com.csx.mytestdemo.line_connect_view;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mlibrary.dialog.CommonDialog;
import com.csx.mlibrary.utils.ToastUtils;
import com.csx.mytestdemo.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Created by cuishuxiang
 * @date 2018/4/16.
 * @description: 连线View
 */

public class ContactActivity extends BaseActivity {
    private static final String TAG = "ContactActivity";
    @BindView(R.id.connect_view)
    ConnectLineView mConnectView;
    @BindView(R.id.loading_view)
    ImageView mLoadingView;

    AnimationDrawable mAnimationDrawable;
    @BindView(R.id.show_single_choose_btn)
    Button mShowSingleChooseBtn;

    CommonDialog mSingleDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_contact;
    }

    @Override
    public void initView() {
        mConnectView.setMaxLinesNum(1);

        mConnectView.setConnectCallBack(new ConnectLineView.ConnectCallBack() {
            @Override
            public void onFailConnect(String failInfo) {
                Log.d(TAG, "onFailConnect: " + failInfo);
            }

            @Override
            public void onSucceedConnect(List<ConnectLineView.LinesData> linesDataList) {
                Log.d(TAG, "onSucceedConnect: " + linesDataList.toString());
                for (int i = 0; i < linesDataList.size(); i++) {

                    int startPosition = linesDataList.get(i).getStartPosition() + 1;
                    int endPosition = linesDataList.get(i).getEndPosition() + 1;
                    ToastUtils.showShortToast("答案为：" + startPosition + " -->" + endPosition);
                }

            }
        });

//        mLoadingView.setImageResource(R.drawable.loading_anim);
        //loading 帧动画
        mAnimationDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.loading_anim);

        mLoadingView.setImageDrawable(mAnimationDrawable);

        mAnimationDrawable.start();
    }

    @Override
    public void initData() {
        View singleChooseView = LayoutInflater.from(this).inflate(R.layout.dialog_single_choose, null);
        final RadioGroup radiogroup = singleChooseView.findViewById(R.id.radiogroup);
//        RadioButton a_rb = singleChooseView.findViewById(R.id.a_rb);
//        RadioButton b_rb = singleChooseView.findViewById(R.id.b_rb);
//        RadioButton c_rb = singleChooseView.findViewById(R.id.c_rb);
//        RadioButton d_rb = singleChooseView.findViewById(R.id.d_rb);
        Button submit_btn = singleChooseView.findViewById(R.id.submit_btn);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkId = radiogroup.getCheckedRadioButtonId();
                switch (checkId) {
                    case R.id.a_rb:
                        Log.d(TAG, "onClick: 选择 a");
                        break;
                    case R.id.b_rb:
                        Log.d(TAG, "onClick: 选择 b");
                        break;
                    case R.id.c_rb:
                        Log.d(TAG, "onClick: 选择 c");
                        break;
                    case R.id.d_rb:
                        Log.d(TAG, "onClick: 选择 d");
                        break;
                }
            }
        });
//        a_rb.isChecked()
        mSingleDialog = new CommonDialog.Builder(this)
                .setContentView(singleChooseView)
                .setCancelable(true)
                .create();

    }

    @OnClick(R.id.show_single_choose_btn)
    public void onViewClicked() {
        mSingleDialog.show();
    }
}
