package com.csx.mytestdemo.image_select.image;

/**
 * 图片实体
 * Created by lixu on 2017/1/25.
 */
public class ImageSelectorImage {
    public String path;
    public String name;
    public long time;

    public ImageSelectorImage(String path, String name, long time){
        this.path = path;
        this.name = name;
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        try {
            ImageSelectorImage other = (ImageSelectorImage) o;
            return this.path.equalsIgnoreCase(other.path);
        }catch (ClassCastException e){
            e.printStackTrace();
        }
        return super.equals(o);
    }
}
