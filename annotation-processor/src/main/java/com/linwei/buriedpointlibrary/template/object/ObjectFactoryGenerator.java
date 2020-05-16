package com.linwei.buriedpointlibrary.template.object;

import com.linwei.buriedpointlibrary.config.Constant;
import com.linwei.buriedpointlibrary.logic.object.ObjectFactoryClasses;
import com.linwei.buriedpointlibrary.logic.object.ObjectFactoryGroupedClasses;
import com.linwei.buriedpointlibrary.utils.ProcessorUtils;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import java.util.LinkedHashMap;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * @Author: WS
 * @Time: 2020/5/13
 * @Description: Factory工厂类模板生成
 */
public class ObjectFactoryGenerator {

    public void generator(String clazzType,
                          ObjectFactoryGroupedClasses groupedClasses,
                          ProcessorUtils processorUtils,
                          ProcessingEnvironment processingEnv) {

        if ("".equals(clazzType)) {
            throw new NullPointerException(
                    String.format("getQualifiedName()  is null or empty! that's not allowed"));
        }

        TypeElement superTypeElement = processorUtils.processorElementUtils().
                getTypeElement(clazzType);
        String classes = superTypeElement.getSimpleName().toString();

        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(Constant.METHOD_FACTORY)
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.get(superTypeElement.asType()));

        //根据FactoryObject.key()获取对象
        methodBuilder.addParameter(String.class, "id");

        //LinkedHashMap<K,V>,K：注解标识，V：使用注解对应对象信息
        LinkedHashMap<String, ObjectFactoryClasses> classesMaps =
                groupedClasses.getObjectFactoryClassesMaps();
        TypeElement typeElement = null;
        for (String key : classesMaps.keySet()) {
            typeElement = classesMaps.get(key).getTypeElement();
            methodBuilder.addStatement("if( \"" + key + "\".equals(id)){");
            methodBuilder.addStatement("return new " + typeElement.getQualifiedName().toString() + "()");
            methodBuilder.addStatement("}");
        }

        methodBuilder.addStatement("return null");

        if (typeElement != null) {
            processorUtils.writeToFile(classes + Constant.FACTORY_SUFFIX,
                    processorUtils.getPackageName(typeElement),
                    methodBuilder.build(), processingEnv, null);
        }
    }
}
