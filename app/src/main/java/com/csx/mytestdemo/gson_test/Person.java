package com.csx.mytestdemo.gson_test;

import com.google.gson.annotations.Expose;

/**
 * @Created by cuishuxiang
 * @date 2018/3/19.
 * @description:
 */

public class Person {

    /**
     * name : Ravi Tamada
     * email : ravi8x@gmail.com
     * phone : {"home":"08947 000000","mobile":"9999999999"}
     */
    @Expose
    private String name;
    @Expose
    private String email;
    private PhoneBean phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PhoneBean getPhone() {
        return phone;
    }

    public void setPhone(PhoneBean phone) {
        this.phone = phone;
    }

    public static class PhoneBean {
        /**
         * home : 08947 000000
         * mobile : 9999999999
         */

        private String home;
        private String mobile;

        public String getHome() {
            return home;
        }

        public void setHome(String home) {
            this.home = home;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                '}';
    }
}
