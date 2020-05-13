package com.linwei.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: WS
 * @Time: 2020/5/12
 * @Description: 对象注解
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface ObjectFactory {

    Class type(); //父类名

    String key();  //标识
}
