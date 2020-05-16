package com.linwei.annotation.utils;

import android.app.Activity;
import android.view.View;

/**
 * @Author: WS
 * @Time: 2020/5/17
 * @Description: 注入工具
 */
public class AnnotationUtils {

    /**
     * 初始化@BindView、@OnClick注解
     * @param activity
     */
    public static void bind(Activity activity){
        View view = activity.getWindow().getDecorView();
        bind(activity,view);
    }

    private static void bind(Object obj,View view){
        try {
            String generateClass = obj.getClass().getName() + "$ViewBinding";
            Class.forName(generateClass).getConstructor(obj.getClass(),View.class).newInstance(obj,view);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
