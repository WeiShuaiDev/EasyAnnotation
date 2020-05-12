package com.linwei.annotation.runtime;

import android.text.TextUtils;

import com.linwei.annotation.annotation.AddPoint;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @Author: WS
 * @Time: 2020/4/27
 * @Description: 运行时解析器
 */
public class  RuntimeParser {
    /**
     * 使用到类注解 该方法只打印了 Type 类型的注解
     *
     * @param className
     * @throws ClassNotFoundException
     */
    public static void parseTypeAnnotation(String className) throws ClassNotFoundException {
        if (TextUtils.isEmpty(className)) return;
        Class clazz = Class.forName(className);
        //读取该类所有注解信息
        Annotation[] annotations = clazz.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof AddPoint) {
                AddPoint point = (AddPoint) annotation;
                System.out.println("key=" + point.key() +
                        ";message=" + point.message());
            }
        }
    }

    /**
     * 使用到构造注解，该方法只打印了 Method类型的注解
     */
    public static void  parseConstructAnnotation(){
        //获取该类所有构造对象
        Constructor[] constructors =RuntimePoint.class.getDeclaredConstructors();
        for(Constructor constructor:constructors){
            //判断该构造对象是否存在@Point注解
            boolean hasAnnotation = constructor.isAnnotationPresent(AddPoint.class);
            if (hasAnnotation) {
                AddPoint point = (AddPoint) constructor.getAnnotation(AddPoint.class);
                System.out.println("key=" + point.key() +
                        ";message=" + point.message());
            }
        }
    }

    /**
     * 使用到方法注解 该方法只打印了 Method类型的注解
     */
    public static void parseMethodAnnotation() {
        //获取该类中所有方法
        Method[] methods = RuntimePoint.class.getDeclaredMethods();
        for (Method method : methods) {
            //判断该方法是否存在@Point注解
            boolean hasAnnotation = method.isAnnotationPresent(AddPoint.class);
            if (hasAnnotation) {
                AddPoint point = method.getAnnotation(AddPoint.class);
                System.out.println("key=" + point.key() +
                        ";message=" + point.message());
            }
        }
    }
}
