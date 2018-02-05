package com.csx.mytestdemo.mvp;

import android.widget.TextView;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.R;
import com.csx.mytestdemo.mvp.base_mvp.UserInfoBean;

import butterknife.BindView;

/**
 * @Created by cuishuxiang
 * @date 2018/2/1.
 */

public class MvpActivity extends BaseActivity implements UserInfoContract.View {
    @BindView(R.id.show_user_info)
    TextView mShowUserInfo;

    private UserInfoPresenter mPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mvp;
    }

    @Override
    public void initView() {
        mPresenter = new UserInfoPresenter(this, new UserInfoModel());

        mPresenter.requestUserInfo();
    }

    @Override
    public void initData() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void responseUserInfo(UserInfoBean userInfoBean) {
        mShowUserInfo.setText(userInfoBean.toString());

    }

}
