package com.csx.mytestdemo.image_select.image;

import android.text.TextUtils;

import java.util.List;

/**
 * 文件夹实体
 * Created by lixu on 2017/1/25.
 */
public class ImageSelectorFolder {
    public String name;
    public String path;
    public ImageSelectorImage cover;
    public List<ImageSelectorImage> imageSelectorImages;

    @Override
    public boolean equals(Object o) {
        try {
            ImageSelectorFolder other = (ImageSelectorFolder) o;
            return TextUtils.equals(other.path, path);
        }catch (ClassCastException e){
            e.printStackTrace();
        }
        return super.equals(o);
    }
}
