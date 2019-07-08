package com.csx.mytestdemo.service_test;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

/**
 * date: 2019/4/18
 * create by cuishuxiang
 * description:
 */
public class Person2 implements Parcelable {
    private String name;
    private int age;

    public Person2() {

    }

    protected Person2(Parcel in) {
        name = in.readString();
        age = in.readInt();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static final Creator<Person2> CREATOR = new Creator<Person2>() {
        @Override
        public Person2 createFromParcel(Parcel in) {
            return new Person2(in);
        }

        @Override
        public Person2[] newArray(int size) {
            return new Person2[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person2 person2 = (Person2) o;
        return age == person2.age &&
                Objects.equals(name, person2.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
