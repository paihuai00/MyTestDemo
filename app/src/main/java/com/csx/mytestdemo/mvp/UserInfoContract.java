package com.csx.mytestdemo.mvp;

import com.csx.mytestdemo.mvp.base_mvp.BaseView;
import com.csx.mytestdemo.mvp.base_mvp.UserInfoBean;

/**
 * @Created by cuishuxiang
 * @date 2018/2/1.
 * <p>
 * mvp 策略层
 */

public interface UserInfoContract {

    //view层，返回数据
    interface View extends BaseView {
        void responseUserInfo(UserInfoBean userInfoBean);
    }


    interface Model {
        UserInfoBean returnUserInfoBean();
    }


    interface Presenter {

    }

}
