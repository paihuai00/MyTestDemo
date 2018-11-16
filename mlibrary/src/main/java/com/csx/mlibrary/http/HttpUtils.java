package com.csx.mlibrary.http;

import java.util.HashMap;
import java.util.Map;

/**
 * Create by cuishuxiang
 *
 * @date: on 2018/10/15
 * @description:
 */
public class HttpUtils {
    private static IHttpEngine mHttpEngine;
    private static HttpUtils mHttpUtils;

    //请求方式
    private int mType = GET_TYPE;
    private static final int GET_TYPE = 0x01;
    private static final int POST_TYPE = 0x02;

    private static HashMap<String, Object> mParams;
    private String mUrl;
    private long mCacheTime = 0;//缓存时间(s)

    /**
     * 需要在Application中初始化
     *
     * @param httpEngine
     */
    public static void init(IHttpEngine httpEngine) {
        mHttpEngine = httpEngine;//初始化 引擎
    }

    public static HttpUtils getInstance() {
        if (mHttpEngine == null) {
            //这里如果没有调用init()设置引擎
            //1，可以抛出异常
            //2，可以设置一个默认的引擎(例如：OkHttpEngine)
            throw new NullPointerException("Please call init() first！！");
        }

        mHttpUtils = new HttpUtils();
        mParams = new HashMap<>();

        return mHttpUtils;
    }

    //设置get 、 post请求
    public HttpUtils get() {
        mType = GET_TYPE;
        return this;
    }

    public HttpUtils post() {
        mType = POST_TYPE;
        return this;
    }

    public HttpUtils setUrl(String url) {
        mUrl = url;
        return this;
    }

    public HttpUtils setCacheTime(long cacheTime) {
        mCacheTime = cacheTime;
        return this;
    }

    //添加参数
    public HttpUtils addParams(Map<String, Object> params) {
        mParams.putAll(params);
        return this;
    }

    public HttpUtils addParams(String key, Object o) {
        mParams.put(key, o);
        return this;
    }

    //执行，添加回调
    public void execute(HttpCallBack callBack) {
        switch (mType) {
            case GET_TYPE:
                get(mUrl, mParams, mCacheTime, callBack);
                break;
            case POST_TYPE:
                post(mUrl, mParams, mCacheTime, callBack);
                break;
        }
    }

    //将get方法，传递给 引擎去执行
    private void get(String url, HashMap<String, Object> params, long cacheTime, HttpCallBack callBack) {
        mHttpEngine.get(url, params, cacheTime, callBack);
    }

    //传递给 引擎去执行
    private void post(String url, HashMap<String, Object> params, long cacheTime, HttpCallBack callBack) {
        mHttpEngine.post(url, params, cacheTime, callBack);
    }

}
