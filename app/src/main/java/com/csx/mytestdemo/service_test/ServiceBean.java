package com.csx.mytestdemo.service_test;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * date: 2019/4/18
 * create by cuishuxiang
 * description:
 */
public class ServiceBean implements Parcelable {

    private int age;
    private String name;


    protected ServiceBean(Parcel in) {
        age = in.readInt();
        name = in.readString();
    }

    public static final Creator<ServiceBean> CREATOR = new Creator<ServiceBean>() {
        @Override
        public ServiceBean createFromParcel(Parcel in) {
            return new ServiceBean(in);
        }

        @Override
        public ServiceBean[] newArray(int size) {
            return new ServiceBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(age);
        dest.writeString(name);
    }
}
