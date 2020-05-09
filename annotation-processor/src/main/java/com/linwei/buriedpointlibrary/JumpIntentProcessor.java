package com.linwei.buriedpointlibrary;

import com.google.auto.service.AutoService;
import com.linwei.annotation.IntentMethod;
import com.linwei.annotation.IntentField;
import com.linwei.buriedpointlibrary.template.ActivityEnterGenerator;
import com.linwei.buriedpointlibrary.template.ActivityOutGenerator;
import com.linwei.buriedpointlibrary.template.Generator;
import com.linwei.buriedpointlibrary.utils.ProcessorUtils;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
/**
 * @Author: LW
 * @Time: 2020/4/30
 * @Description: Activity跳转注解处理器
 */
public class JumpIntentProcessor extends AbstractProcessor {
    private ProcessorUtils mProcessorUtils;

    private List<Generator> mGenerators = new LinkedList<>();

    //保存指定类型,所有VariableElement数据
    private Map<String, List<VariableElement>> mVariableElementLists = new HashMap<>();

    //保存指定类型，对应ExecutableElement数据
    private Map<String, ExecutableElement> mExecutableElementLists = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        initProcessorUtils();
        initGenerated();
    }

    /**
     * 初始化ProcessorUtils工具
     */
    private void initProcessorUtils() {
        mProcessorUtils = ProcessorUtils.getInstance();
    }

    /**
     * 初始化Generated
     */
    private void initGenerated() {
        mGenerators.add(new ActivityEnterGenerator());
        mGenerators.add(new ActivityOutGenerator());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> methodElement = roundEnv.getElementsAnnotatedWith(IntentMethod.class);
        Set<? extends Element> fieldElement = roundEnv.getElementsAnnotatedWith(IntentField.class);

        ExecutableElement executableElement = null;
        VariableElement variableElement = null;
        String methodValue = "";

        for (Element method : methodElement) {
            ElementKind methodKind = method.getKind();
            if (methodKind == ElementKind.METHOD) {
                //判断Elements类型，并获取Method参数值
                executableElement = (ExecutableElement) method;
                methodValue = executableElement.getAnnotation(IntentMethod.class).value();
            }

            //主要获取ElementType 是不是null，即class，interface，enum或者注解类型
            Element typeElement = method.getEnclosingElement();
            //判断方法参数，classFlag跳转标识判断
            if (mProcessorUtils.isNotEmpty(methodValue)) {
                //如果mVariableElementLists的key不存在，则添加一个key
                if (mVariableElementLists.get(methodValue) == null) {
                    mVariableElementLists.put(methodValue, new LinkedList<VariableElement>());
                }

                mExecutableElementLists.put(methodValue, executableElement);

                for (Element field : fieldElement) {
                    ElementKind fieldKind = field.getKind();
                    if (fieldKind == ElementKind.FIELD) {
                        //判断Elements类型，并获取Filed参数值
                        variableElement = (VariableElement) field;
                        String filedValue = variableElement.getAnnotation(IntentField.class).value();

                        if (!mProcessorUtils.isNotEmpty(filedValue)) continue;

                        if (filedValue.equals(methodValue)) {
                            //IntentField是应用在一般成员变量上的注解
                            mVariableElementLists.get(methodValue).add((VariableElement) field);
                        }
                    }
                }
            }
        }

        //根据获取注解信息，进行代码文件生成
        for (Map.Entry<String, List<VariableElement>> entry : mVariableElementLists.entrySet()) {
            if (!mProcessorUtils.isNotEmpty(entry.getKey())) continue;
            executableElement = mExecutableElementLists.get(entry.getKey());
            for (Generator generator : mGenerators) {
                generator.generator(entry.getKey(), entry.getValue(),
                        executableElement, mProcessorUtils, processingEnv);
            }
        }
        return false;
    }

    /**
     * 指出注解处理器 处理哪种注解
     * 在 jdk1.7 中，我们可以使用注解 @SupportedAnnotationTypes()代替
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        LinkedHashSet<String> types = new LinkedHashSet<>(2);
        types.add(IntentField.class.getCanonicalName());
        types.add(IntentMethod.class.getCanonicalName());
        return types;
    }

    /**
     * 指定当前注解器使用的Jdk版本
     * 在 jdk1.7 中，我们可以使用注解 @SupportedSourceVersion()代替
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_7;
    }
}
