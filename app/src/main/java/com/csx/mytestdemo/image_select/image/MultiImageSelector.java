package com.csx.mytestdemo.image_select.image;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AlertDialog;


import java.util.ArrayList;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.RuntimePermissions;

/**
 * 图片选择器初始化类
 * Created by lixu on 2017/1/25.
 */
public class MultiImageSelector {

    private boolean mShowCamera = true;
    private int mMaxCount = 9;
    private int mMode = MultiImageSelectorFragment.MODE_MULTI;
    private int mRequestCode = 13579;
    private ArrayList<String> mOriginData;
    private static MultiImageSelector sSelector;

    private MultiImageSelector() {
    }

    public static MultiImageSelector create() {
        if (sSelector == null) {
            sSelector = new MultiImageSelector();

        }
        return sSelector;
    }

    public MultiImageSelector single() {
        mMode = MultiImageSelectorFragment.MODE_SINGLE;
        return sSelector;
    }

    public MultiImageSelector multi() {
        mMode = MultiImageSelectorFragment.MODE_MULTI;
        return sSelector;
    }

    public MultiImageSelector origin(ArrayList<String> images) {
        mOriginData = images;
        return sSelector;
    }

    public MultiImageSelector requestCode(int requestCode) {
        mRequestCode = requestCode;
        return sSelector;
    }

    public MultiImageSelector maxCount(int count) {
        mMaxCount = count;
        return sSelector;
    }

    public MultiImageSelector camera(boolean isShow) {
        mShowCamera = isShow;
        return sSelector;
    }

    public void start(final Activity activity) {
        MultiImageSelectorFragment.openForResult(activity, mMaxCount, mRequestCode, mMode, mShowCamera, mOriginData);
    }

}
