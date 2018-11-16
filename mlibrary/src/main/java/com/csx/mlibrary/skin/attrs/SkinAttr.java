package com.csx.mlibrary.skin.attrs;

import android.view.View;

/**
 * Create by cuishuxiang
 *
 * @date: on 2018/10/23
 * @description:
 */
public class SkinAttr {
    private String mResName;
    private SkinType mSkinType;

    public SkinAttr(String resName, SkinType skinType) {
        this.mResName = resName;
        this.mSkinType = skinType;
    }

    public void skin(View view) {
        mSkinType.skin(view, mResName);
    }
}
