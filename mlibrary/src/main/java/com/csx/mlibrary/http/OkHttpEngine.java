package com.csx.mlibrary.http;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.Util;

/**
 * Create by cuishuxiang
 *
 * @date: on 2018/10/15
 * @description:
 */
public class OkHttpEngine implements IHttpEngine {
    private static final String TAG = "OkHttpEngine";

    private static OkHttpEngine mOkHttpEngine;

    private OkHttpClient mOkHttpClient;
    private int cacheSize = 10 * 1024 * 1024;//10MB

    private Context mContext;

    private OkHttpEngine(Context context) {
        this.mContext = context;
        mOkHttpClient = new OkHttpClient().newBuilder()
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .cache(new Cache(context.getCacheDir(), cacheSize))
                .build();
    }

    public static OkHttpEngine getInstance(Context context) {
        mOkHttpEngine = new OkHttpEngine(context);
        return mOkHttpEngine;
    }

    @Override
    public void get(String url, HashMap<String, Object> params, HttpCallBack calBack) {
        //在这里调用OkHttp的get方法
        doGet(url, params, 0, calBack);
    }

    @Override
    public void get(String url, HashMap<String, Object> params, long cacheTime, HttpCallBack calBack) {
        //在这里调用OkHttp的get方法
        doGet(url, params, cacheTime, calBack);
    }

    //get请求
    private void doGet(String url, HashMap<String, Object> params, long cacheTime, HttpCallBack calBack) {
        String mAddParamsUrl = addParams(params, url);

        CacheControl mCacheControl = new CacheControl.Builder()
                .maxStale((int) cacheTime, TimeUnit.SECONDS)
                .build();
        Request mRequest = new Request.Builder()
                .url(mAddParamsUrl)
                .cacheControl(mCacheControl)
                .build();

        mOkHttpClient
                .newCall(mRequest)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "onFailure: ");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d(TAG, "onResponse: ");
                    }
                });
    }

    //拼接Url
    private String addParams(HashMap<String, Object> params, String url) {
        if (params != null) {
            StringBuilder mStringBuilder = new StringBuilder("?");
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                mStringBuilder.append(entry.getKey());
                mStringBuilder.append("=");
                mStringBuilder.append(entry.getValue());
                mStringBuilder.append("&");
            }
            String paramsUrl = mStringBuilder.toString();
            return url + paramsUrl.substring(0, paramsUrl.length() - 1);
        }

        return url;
    }

    @Override
    public void post(String url, HashMap<String, Object> params, HttpCallBack callBack) {
        post(url, params, 0, callBack);
    }

    @Override
    public void post(String url, HashMap<String, Object> params, long cacheTime, HttpCallBack callBack) {
        CacheControl.Builder cacheBuilder = new CacheControl.Builder()
                .maxStale((int) cacheTime, TimeUnit.SECONDS);
        CacheControl mCacheControl = cacheBuilder.build();

        Log.d(TAG, "[post:] : " + addParams(params, url));

        RequestBody mRequestBody = Util.EMPTY_REQUEST;

        if (params != null && !params.isEmpty()) {
            MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);
            for (String key : params.keySet()) {
                Object value = params.get(key);

                if (value instanceof File) {
                    builder.addFormDataPart(key, ((File) value).getName(), RequestBody.create(MediaType.parse("image/png"), (File) value));
                } else {
                    builder.addFormDataPart(key, value.toString());
                }
            }

            mRequestBody = builder.build();
        }

        Request mRequest = new Request.Builder()
                .url(url)
                .post(mRequestBody)
                .cacheControl(mCacheControl)
                .build();

        mOkHttpClient.newCall(mRequest)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "onFailure: ]");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d(TAG, "onResponse: ");
                    }
                });
    }
}
