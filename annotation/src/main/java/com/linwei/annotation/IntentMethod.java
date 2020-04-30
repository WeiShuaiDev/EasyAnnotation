package com.linwei.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: linwei
 * @Time: 2020/4/30
 * @Description: 界面跳转方法标识注解
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface IntentMethod {
    /**
     * 目标对象标识,如{{@code MainActivity}}
     *
     * @return
     */
    String value() default "";
}