package com.csx.mytestdemo.mvp;

import com.csx.mytestdemo.mvp.base_mvp.UserInfoBean;

/**
 * @Created by cuishuxiang
 * @date 2018/2/1.
 *
 * 个人数据，modelceng
 */

public class UserInfoModel implements UserInfoContract.Model {
    @Override
    public UserInfoBean returnUserInfoBean() {

        UserInfoBean userInfoBean = new UserInfoBean();
        userInfoBean.setAge("1");
        userInfoBean.setName("zhangsan");

        return userInfoBean;
    }

}
