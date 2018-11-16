package com.csx.mlibrary.skin;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.csx.mlibrary.skin.attrs.SkinAttr;
import com.csx.mlibrary.skin.attrs.SkinType;
import com.csx.mlibrary.skin.attrs.SkinView;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by cuishuxiang
 *
 * @date: on 2018/10/23
 * @description: 皮肤属性解析的支持类
 */
public class SkinSupport {
    private static final String TAG = "SkinSupport";

    /**
     * 获取 attrs 属性
     * @param context
     * @param attrs
     * @return
     */
    public static List<SkinAttr> getSkinAttrs(Context context, AttributeSet attrs) {
        //需要把 background src  textColor 从 attrs 中解析
        List<SkinAttr> mSkinAttrs = new ArrayList<>();

        //获取长度
        int attrsLength = attrs.getAttributeCount();
        for (int index = 0; index < attrsLength; index++) {
            //获取名称
            String attrName = attrs.getAttributeName(index);
            //value
            String attrValue = attrs.getAttributeValue(index);

            SkinType skinType = getSkinType(attrName);
            if (skinType != null) {
                //资源名称（这里的 attrValue 都是类似“@13441122”，需要转换）
                String resName = getResName(context, attrValue);

                if (TextUtils.isEmpty(resName)) {
                    continue;
                }
                Log.d(TAG, "resName =  "+resName);
                SkinAttr skinAttr = new SkinAttr(resName, skinType);
                mSkinAttrs.add(skinAttr);
            }
        }

        return null;
    }

    /**
     * 获取资源名称()
     *
     * @param context
     * @param attrValue
     * @return
     */
    private static String getResName(Context context, String attrValue) {
        if (attrValue.startsWith("@")){
            attrValue = attrValue.substring(1);

            int resId = Integer.parseInt(attrValue);

            return context.getResources().getResourceEntryName(resId);
        }
        return null;
    }

    private static SkinType getSkinType(String attrName) {
        SkinType[] skinTypes = SkinType.values();//这里包含，background src  textColor
        for (SkinType skinType : skinTypes) {
            if (skinType.getResName().equals(attrName)) {
                return skinType;
            }
        }
        return null;
    }
}
