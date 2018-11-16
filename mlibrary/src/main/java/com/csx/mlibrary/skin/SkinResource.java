package com.csx.mlibrary.skin;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Method;

/**
 * Create by cuishuxiang
 *
 * @date: on 2018/10/23
 * @description:
 */
public class SkinResource {
    //资源都是通过该对象获取
    private Resources mSkinResources;
    //skinPath资源的包名
    private String packageName;

    public SkinResource(Context context, String skinPath) {
        try {
            Resources systemResources = context.getResources();
            //创建 AssetManager 系统隐藏{@hide}了该构造方法；
            //需要使用反射
            AssetManager mAssetManager = AssetManager.class.newInstance();
            //添加本地 资源，需要反射拿到该方法 (addAssetPath)
            Method mMethod = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
//            Log.d(TAG, "onClick: Environment.getExternalStorageDirectory() + File.separator + \"skin_app.com.csx.mlibrary.skin\" = " + Environment.getExternalStorageDirectory() + File.separator + "skin_app.com.csx.mlibrary.skin");
            mMethod.invoke(mAssetManager, Environment.getExternalStorageDirectory() + File.separator + "skin_app.skin");

            mSkinResources = new Resources(mAssetManager, systemResources.getDisplayMetrics(), systemResources.getConfiguration());

            //获取 skinPath 的包名
            packageName = context.getPackageManager().getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES).applicationInfo.packageName;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param resName
     * @return
     */
    public Drawable getDrawableByName(String resName) {
        try {

            int resId = mSkinResources.getIdentifier(resName, "drawable", packageName);
            Drawable drawable = mSkinResources.getDrawable(resId);
            return drawable;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ColorStateList getColorByName(String resName) {
        try {

            int resId = mSkinResources.getIdentifier(resName, "color", packageName);
            ColorStateList colorStateList = mSkinResources.getColorStateList(resId);
            return colorStateList;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
