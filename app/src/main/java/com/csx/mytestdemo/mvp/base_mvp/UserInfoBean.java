package com.csx.mytestdemo.mvp.base_mvp;

/**
 * @Created by cuishuxiang
 * @date 2018/2/1.
 *
 * user bean
 */

public class UserInfoBean {

    String name;
    String age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }


    @Override
    public String toString() {
        return getName() + "  " + getAge();
    }
}
