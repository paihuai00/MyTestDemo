package com.csx.mytestdemo.net_change.net_change;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * date: 2019/4/12
 * create by cuishuxiang
 * description:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface NetWork {
    NetType netType() default NetType.AUTO;
}
