package com.csx.mytestdemo.my_butterknife.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Create by cuishuxiang
 *
 * @date: on 2018/10/9
 * @description:
 */
@Target(ElementType.FIELD) //Annotation
@Retention(RetentionPolicy.RUNTIME)//编译时注解(什么时候生效)
public @interface ViewById {
    //
    int value();
}
