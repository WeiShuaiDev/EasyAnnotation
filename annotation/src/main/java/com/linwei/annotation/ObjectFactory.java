package com.linwei.annotation;

/**
 * @Author: WS
 * @Time: 2020/5/12
 * @Description: 对象注解
 */
public @interface ObjectFactory {

    String objectName(); //生成类名

    String key();  //标识
}
