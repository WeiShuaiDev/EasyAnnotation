package com.linwei.buriedpointlibrary.template;

import com.linwei.buriedpointlibrary.config.Constant;
import com.linwei.buriedpointlibrary.utils.ProcessorUtils;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

/**
 * @Author: LW
 * @Time: 2020/4/30
 * @Description: Activity跳转起始模板生成
 */
public class ActivityEnterGenerator implements Generator {


    /**
     * @param clazzFlag        类
     * @param variableElements 字段
     * @param processorUtils   工具
     * @param processingEnv
     */
    @Override
    public void generator(String clazzType,
                          List<VariableElement> variableElements,
                          ExecutableElement executableElement,
                          ProcessorUtils processorUtils,
                          ProcessingEnvironment processingEnv) {

        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(Constant.METHOD_ENTER_ACTIVITY)
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class);


        methodBuilder.addParameter(Object.class, "context");
        methodBuilder.addStatement("android.content.Intent intent = new android.content.Intent()");
        for (VariableElement element : variableElements) {
            //Element 只是一种语言元素，本身并不包含信息，所以我们这里获取TypeMirror
            TypeMirror typeMirror = element.asType();
            //获取注解字段变量类型
            TypeName typeName = TypeName.get(typeMirror);
            //获取注解字段变量名称
            String fieldName = element.getSimpleName().toString();

            methodBuilder.addParameter(typeName, fieldName);
            methodBuilder.addStatement("intent.putExtra(\"" + fieldName + "\"," + fieldName + ")");

        }

        if (!processorUtils.isNotEmpty(clazzType)) {
            processingEnv.getMessager().printMessage(
                    Diagnostic.Kind.ERROR,
                    "IntentClass注解定义不明确,无法进行界面跳转!"
            );
        }

        String clazz = clazzType.substring(clazzType.lastIndexOf("_")+1);

        methodBuilder.addStatement("intent.setClass((android.content.Context)context, " + clazz + ".class)");
        methodBuilder.addStatement("((android.content.Context)context).startActivity(intent)");


        TypeElement typeElement = (TypeElement)
                executableElement.getEnclosingElement();

        processorUtils.writeToFile(clazzType,
                processorUtils.getPackageName(typeElement),
                methodBuilder.build(), processingEnv, null);

    }
}
