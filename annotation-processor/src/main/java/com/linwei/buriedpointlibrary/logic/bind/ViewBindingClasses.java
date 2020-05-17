package com.linwei.buriedpointlibrary.logic.bind;

import com.linwei.annotation.BindView;
import com.linwei.annotation.OnClick;
import com.squareup.javapoet.CodeBlock;

import java.util.ArrayList;

import javax.lang.model.element.Element;

class ViewBindingClasses {

    /**
     * 解析ViewBind注解信息，并转换为CodeBlock.builder数据
     * @return 构造配置信息
     */
    CodeBlock.Builder parseBindView(Element element){
        //读取变量类型
        String type = element.asType().toString();
        //读取变量名
        String name = element.getSimpleName().toString();
        //读取注解信息
        int annotationValue = element.getAnnotation(BindView.class).value();

        CodeBlock.Builder builder = CodeBlock.builder()
                .add("target.$L=",name)
                .add("($L)source.findViewById($L)",type,annotationValue);
        return builder;
    }

    /**
     * 解析OnClick注解信息,并转换为CodeBlock.builder数据
     * @param element
     * @return
     */

    ArrayList<CodeBlock.Builder> parseListenerView(Element element){
         //读取注解信息
         int[] annotationValue = element.getAnnotation(OnClick.class).value();

         //读取变量名
         String name = element.getSimpleName().toString();

         ArrayList<CodeBlock.Builder> builders = new ArrayList<>();

         for(int value:annotationValue){
             CodeBlock.Builder builder = CodeBlock.builder()
                     .add("source.findViewById($L).setOnClickListener(new android.view.View.OnClickListener() " +
                             "{ public void onClick(View v) { target.$L(v); }})", value, name);
             builders.add(builder);
         }
         return builders;
     }

}
