package com.linwei.buriedpointlibrary.template.bind;

import com.linwei.buriedpointlibrary.config.Constant;
import com.linwei.buriedpointlibrary.utils.ProcessorUtils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * @Author: WS
 * @Time: 2020/5/13
 * @Description: ViewBind生成模板代码接口
 */
public class BindGenerator {

    /**
     *
     * @param typeElement
     * @param codeBlocks  配置信息
     * @param processorUtils
     * @param processingEnv
     */
    public void generator(TypeElement typeElement,
                          List<CodeBlock.Builder> codeBlocks,
                          ProcessorUtils processorUtils,
                          ProcessingEnvironment processingEnv) {
        //获取类名
        String className = typeElement.getSimpleName().toString();

        //根据TypeElement类型，获取类型名
        TypeName typeName = TypeName.get(typeElement.asType());
        if(typeName instanceof ParameterizedTypeName){
            typeName=((ParameterizedTypeName)typeName).rawType;
        }

        //配置构造函数，并设置参数
        MethodSpec.Builder methodBuilder = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(typeName,"target",Modifier.FINAL)
                .addParameter(ClassName.get("android.view", "View"),
                        "source",Modifier.FINAL);

        //配置构造函数内容信息
        for(CodeBlock.Builder builder:codeBlocks){
            methodBuilder.addStatement(builder.build());
        }

        processorUtils.writeToFile( className+ Constant.VIEW_BINDING_SUFFIX,
                processorUtils.getPackageName(typeElement),
                methodBuilder.build(), processingEnv, null);



    }
}
