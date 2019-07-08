package com.csx.mytestdemo.databing_demo;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.csx.mytestdemo.R;
import com.csx.mytestdemo.databinding.ActivityDataDemoBinding;

/**
 * date: 2019/4/9
 * create by cuishuxiang
 * description:
 */
public class DataBindingActivity extends Activity {

    ActivityDataDemoBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 这里的binging 类，根据layout文件的命名生成！
         */
        binding = DataBindingUtil.setContentView(this, R.layout.activity_data_demo);
        Person p = new Person();
        p.name.set("名字111");
        p.age.set(2);
        binding.setPerson(p);
        binding.setClick(this);

    }

    public void onButtenClick(View view) {
        Person p = new Person();
        p.age.set((int) System.currentTimeMillis());
        p.name.set("" + System.currentTimeMillis());
        binding.setPerson(p);
    }


}
