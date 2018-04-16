package com.csx.mytestdemo.connect_view;

import java.util.List;

/**
 * @Created by cuishuxiang
 * @date 2018/4/16.
 * @description: 存储连线数据的bean
 *
 * mStringList：连线的数据
 * mode：当前的类型（0：问题  1：答案）
 *
 */

public class ConnectData {
    List<String> mStringList;
    int mode;

    public List<String> getStringList() {
        return mStringList;
    }

    public void setStringList(List<String> stringList) {
        mStringList = stringList;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}
