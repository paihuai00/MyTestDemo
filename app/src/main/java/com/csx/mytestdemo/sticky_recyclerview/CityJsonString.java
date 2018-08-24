package com.csx.mytestdemo.sticky_recyclerview;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

/**
 * Create by cuishuxiang
 *
 * @date: on 2018/6/19
 * @description:  存放 城市 json
 */

public class CityJsonString {
    public static CityBean getCityBean(Context context) {
        try {
            InputStream inputStream = context.getAssets().open("CityJson");
            int length = inputStream.available();
            byte[] bytes = new byte[length];
            inputStream.read(bytes);

            String jsonString = new String(bytes, "utf-8");

            Gson gson = new Gson();

            CityBean cityBean = gson.fromJson(jsonString, CityBean.class);

            return cityBean;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
