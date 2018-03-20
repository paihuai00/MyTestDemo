package com.csx.mytestdemo.gson_test;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Created by cuishuxiang
 * @date 2018/3/19.
 * @description: gson 注解
 */

public class GsonActivity extends BaseActivity {
    private static final String TAG = "GsonActivity";
    @BindView(R.id.show_gson_tv)
    TextView mShowGsonTv;


    private String gsonString = "{\n" +
            "    \"name\" : \"Ravi Tamada\", \n" +
            "    \"email\" : \"ravi8x@gmail.com\",\n" +
            "    \"phone\" : {\n" +
            "        \"home\" : \"08947 000000\",\n" +
            "        \"mobile\" : \"9999999999\"\n" +
            "    }\n" +
            "    \n" +
            "}";

    @Override
    public int getLayoutId() {
        return R.layout.activity_gson;
    }

    @Override
    public void initView() {
//        Gson gson = new Gson();

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        Person person = gson.fromJson(gsonString, Person.class);

        Log.d(TAG, "initView: " + person.getName());

        mShowGsonTv.setText(person.toString());
    }

    @Override
    public void initData() {

    }

}
