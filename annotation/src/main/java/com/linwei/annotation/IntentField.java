package com.linwei.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: WS
 * @Time: 2020/4/27
 * @Description: 界面跳转变量注解
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface IntentField {

    /**
     * 目标对象标识,如{{@code MainActivity}}
     *
     * @return
     */
    String value() default "";
}
