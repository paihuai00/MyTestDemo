package com.csx.mlibrary.skin.attrs;

import android.view.View;

import java.util.List;

/**
 * Create by cuishuxiang
 *
 * @date: on 2018/10/23
 * @description:
 */
public class SkinView {
    private View mView;


    private List<SkinAttr> mSkinAttrs;

    public SkinView(View view, List<SkinAttr> skinAttrs) {
        this.mView = view;
        this.mSkinAttrs = skinAttrs;
    }

    public void skin() {
        for (SkinAttr skinAttr : mSkinAttrs) {
            skinAttr.skin(mView);
        }
    }
}
