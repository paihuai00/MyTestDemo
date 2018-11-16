package com.csx.mlibrary.skin;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

import com.csx.mlibrary.skin.attrs.SkinView;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Create by cuishuxiang
 *
 * @date: on 2018/10/23
 * @description:
 */
public class SkinManager {

    private static SkinManager mSkinManager;
    private Context mContext;
    private SkinResource mSkinResource;

    private Map<Activity, List<SkinView>> mSkinViews = new HashMap<>();


    static {
        mSkinManager = new SkinManager();
    }

    public static SkinManager getInstance() {
        return mSkinManager;
    }

    public void init(Context context) {
        this.mContext = context.getApplicationContext();
    }

    //加载皮肤
    public int loadSkin(String skinPath) {
        //初始化资源
        mSkinResource = new SkinResource(mContext, skinPath);

        //改变皮肤
        for (Map.Entry<Activity,List<SkinView>> entry : mSkinViews.entrySet()){
            List<SkinView> skinViewList = entry.getValue();
            for (SkinView skinView : skinViewList) {
                skinView.skin();
            }
        }
        return 0;
    }

    //恢复默认
    public int recoverSkin() {
        return 0;
    }

    public List<SkinView> getSkinViews(Activity activity) {
        return mSkinViews.get(activity);
    }

    /**
     * 注册
     * @param activity
     * @param skinViewList
     */
    public void register(Activity activity, List<SkinView> skinViewList) {
        mSkinViews.put(activity, skinViewList);
    }

    //获取当前皮肤资源管理
    public SkinResource getSkinResource() {
        return mSkinResource;
    }
}
