package com.linwei.buriedpointfactory.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: weiyun
 * @Time: 2020/4/27
 * @Description: 增加埋点注解
 */

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD,ElementType.CONSTRUCTOR})
public @interface AddPoint {
    String key() default "1";

    int message() default -1;
}
