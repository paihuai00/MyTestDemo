package com.csx.mlibrary.http;

import java.util.HashMap;

/**
 * Create by cuishuxiang
 *
 * @date: on 2018/10/15
 * @description:
 */
public interface IHttpEngine {

    void get(String url, HashMap<String, Object> params, HttpCallBack calBack);

    void get(String url, HashMap<String, Object> params, long cacheTime, HttpCallBack calBack);

    void post(String url, HashMap<String, Object> params, HttpCallBack callBack);

    void post(String url, HashMap<String, Object> params, long cacheTime, HttpCallBack callBack);
}
