package com.linwei.buriedpointlibrary.template;

import com.linwei.buriedpointlibrary.config.Constant;
import com.linwei.buriedpointlibrary.utils.ProcessorUtils;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

/**
 * @Author: LW
 * @Time: 2020/5/4
 * @Description: Activity跳转终点模板生成
 */
public class ActivityOutGenerator implements Generator {

    @Override
    public void generator(String clazzType,
                          List<VariableElement> variableElements,
                          ExecutableElement executableElement,
                          ProcessorUtils processorUtils,
                          ProcessingEnvironment processingEnv) {
        System.out.println("ActivityOutGenerator");
        //存储成员变量信息
        ArrayList<FieldSpec> listField = new ArrayList<>();

        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(Constant.METHOD_OUT_ACTIVITY)
                .addModifiers(Modifier.PUBLIC)
                .returns(Object.class);


        if (!processorUtils.isNotEmpty(clazzType)) {
            processingEnv.getMessager().printMessage(
                    Diagnostic.Kind.ERROR,
                    "IntentClass注解定义不明确,无法进行界面跳转!"
            );
        }
        //获取目标对象
        String next = clazzType.substring(clazzType.lastIndexOf("_") + 1);

        methodBuilder.addParameter(Object.class,"activity");
        methodBuilder.addStatement(next+" nextActivity = ("+next+") activity");
        methodBuilder.addStatement("android.content.Intent intent=nextActivity.getIntent()");

        for(VariableElement element :variableElements){
            //Element 只是一种语言元素，本身并不包含信息，所以我们这里获取TypeMirror
            TypeMirror typeMirror = element.asType();
            //获取注解字段变量类型
            TypeName typeName = TypeName.get(typeMirror);
            //获取注解字段变量名称
            String fieldName = element.getSimpleName().toString();
            //注解类型转换为Intent传输类型
            String intentTypeName = processorUtils.getIntentTypeName(typeName.toString());
            //创建成员变量
            FieldSpec fieldSpec = FieldSpec.builder(typeName,fieldName)
                    .addModifiers(Modifier.PUBLIC)
                    .build();
            listField.add(fieldSpec);

            if(processorUtils.isElementNoDefaultValue(typeName.toString())){
                methodBuilder.addStatement("this."+fieldName+"=intent.get"+intentTypeName+"Extra(\""+fieldName+"\")");
            }else{
                if(intentTypeName==null){
                    processingEnv.getMessager().printMessage(
                            Diagnostic.Kind.ERROR,
                            "the type:" + element.asType().toString() + " is not support"
                    );
                }else{
                    String defaultValue="default"+fieldName;
                    if("".equals(intentTypeName)){
                        //序列化数据获取
                        methodBuilder.addStatement("this."+fieldName+"=("+typeName+")intent.getSerializableExtra(\"" + fieldName + "\")");
                    }else{
                        methodBuilder.addParameter(typeName,defaultValue);
                        methodBuilder.addStatement("this."+ fieldName +"= intent.get"
                                + intentTypeName + "Extra(\"" + fieldName + "\", " + defaultValue + ")");
                    }
                }
            }
        }
        methodBuilder.addStatement("return this");

        TypeElement typeElement = (TypeElement)
                executableElement.getEnclosingElement();

        processorUtils.writeToFile(clazzType,
                processorUtils.getPackageName(typeElement),
                methodBuilder.build(), processingEnv, listField);
    }
}
