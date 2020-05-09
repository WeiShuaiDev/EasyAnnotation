package com.linwei.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: WS
 * @Time: 2020/4/27
 * @Description: 增加埋点注解
 */

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE, ElementType.METHOD,ElementType.CONSTRUCTOR})
public @interface Point {
    String key() default "";

    int message() default -1;
}
