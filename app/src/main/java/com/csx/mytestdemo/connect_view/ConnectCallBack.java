package com.csx.mytestdemo.connect_view;

import java.util.List;

/**
 * @Created by cuishuxiang
 * @date 2018/4/16.
 * @description:
 */

public interface ConnectCallBack {
    //连线失败的回调
    String onFailConnect();

    //连线成功的回调
    List<ConnectData> onSucceedConnect();

}
