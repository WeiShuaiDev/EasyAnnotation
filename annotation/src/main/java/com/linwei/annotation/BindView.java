package com.linwei.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: LW
 * @Time: 2020/4/28
 * @Description: ButterKnife中View绑定注解
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.FIELD})
public @interface BindView {
    int value();  //View id
}
