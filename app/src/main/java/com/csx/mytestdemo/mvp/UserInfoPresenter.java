package com.csx.mytestdemo.mvp;

import com.csx.mytestdemo.mvp.base_mvp.UserInfoBean;

/**
 * @Created by cuishuxiang
 * @date 2018/2/1.
 */

public class UserInfoPresenter implements UserInfoContract.Presenter {

    private UserInfoContract.View mView;
    private UserInfoContract.Model mModel;

    public UserInfoPresenter(UserInfoContract.View view, UserInfoContract.Model model) {
        mView = view;
        mModel = model;
    }

    public void requestUserInfo() {
        UserInfoBean userInfoBean = mModel.returnUserInfoBean();
        if (userInfoBean != null) {
            mView.responseUserInfo(userInfoBean);
        }

    }

}
