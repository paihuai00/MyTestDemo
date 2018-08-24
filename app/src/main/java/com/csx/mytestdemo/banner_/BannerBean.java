package com.csx.mytestdemo.banner_;

/**
 * Description:  轮播图bean
 * Author:zhangrui
 * Date:2018/6/25
 */
public class BannerBean {
    private String title;
    private String url;//跳转 url
    private boolean adShow;
    private String picUrl;//图片url

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isAdShow() {
        return adShow;
    }

    public void setAdShow(boolean adShow) {
        this.adShow = adShow;
    }
}
