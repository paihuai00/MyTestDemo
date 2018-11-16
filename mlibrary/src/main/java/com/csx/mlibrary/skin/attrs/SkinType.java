package com.csx.mlibrary.skin.attrs;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.csx.mlibrary.skin.SkinManager;
import com.csx.mlibrary.skin.SkinResource;

/**
 * Create by cuishuxiang
 *
 * @date: on 2018/10/23
 * @description:
 */
public enum SkinType {

    TEXT_COLOR("textColor") {
        @Override
        public void skin(View view, String resName) {
            SkinResource skinResource = getSkinResource();
            //有可能为null
            ColorStateList colorStateList = skinResource.getColorByName(resName);
            if (colorStateList != null) {
                TextView textView = (TextView) view;
                textView.setTextColor(colorStateList);
            } else {
                return;
            }
        }
    }, BACKGROUND("background") {
        @Override
        public void skin(View view, String resName) {
            SkinResource skinResource = getSkinResource();
            //有可能为null
            Drawable drawable = skinResource.getDrawableByName(resName);
            if (drawable != null) {
                ImageView imageView = (ImageView) view;
                imageView.setBackground(drawable);
            }

            //背景有可能是 颜色color
            ColorStateList colorStateList = skinResource.getColorByName(resName);
            if (colorStateList != null) {
                view.setBackgroundColor(colorStateList.getDefaultColor());
            }
        }
    }, SRC("src") {
        @Override
        public void skin(View view, String resName) {
            //资源设置
            SkinResource skinResource = getSkinResource();
            Drawable drawable = skinResource.getDrawableByName(resName);
            if (drawable != null) {
                ImageView imageView = (ImageView) view;
                imageView.setImageDrawable(drawable);
            }
        }
    };

    private String mResName;

    private SkinType(String resName) {
        this.mResName = resName;
    }

    public abstract void skin(View view, String resName);

    public String getResName() {
        return mResName;
    }

    public SkinResource getSkinResource() {
        return SkinManager.getInstance().getSkinResource();
    }
}
