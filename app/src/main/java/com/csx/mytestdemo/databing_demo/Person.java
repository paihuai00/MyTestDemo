package com.csx.mytestdemo.databing_demo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.view.View;

import com.android.databinding.library.baseAdapters.BR;

/**
 * date: 2019/4/9
 * create by cuishuxiang
 * description:
 */
public class Person extends BaseObservable {
    public ObservableField<Integer> age = new ObservableField<>();
    public ObservableField<String> name = new ObservableField<>();


}
