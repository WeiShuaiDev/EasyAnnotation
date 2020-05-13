package com.linwei.buriedpointlibrary;

import com.linwei.annotation.IntentMethod;
import com.linwei.annotation.IntentField;
import com.linwei.annotation.IntentParameter;
import com.linwei.buriedpointlibrary.template.activity.ActivityEnterGenerator;
import com.linwei.buriedpointlibrary.template.activity.ActivityOutGenerator;
import com.linwei.buriedpointlibrary.template.activity.ActivityGenerator;
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
 * @Author: WS
 * @Time: 2020/4/30
 * @Description: Activity跳转注解处理器
 */
public class JumpIntentProcessor extends AbstractProcessor {
    private ProcessorUtils mProcessorUtils;

    private List<ActivityGenerator> mGenerators = new LinkedList<>();

    //保存指定类型,所有VariableElement数据
    private Map<String, List<VariableElement>> mVariableElementLists = new HashMap<>();

    //保存指定类型，对应ExecutableElement数据
    private Map<String, ExecutableElement> mExecutableElementLists = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        initProcessorUtils(processingEnv);
        initGenerated();
    }

    /**
     * 初始化ProcessorUtils工具
     * @param processingEnv
     */
    private void initProcessorUtils(ProcessingEnvironment processingEnv) {
        mProcessorUtils = ProcessorUtils.getInstance(processingEnv);
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
        //获取IntentMethod跳转方法所有注解信息
        Set<? extends Element> methodElement = roundEnv.getElementsAnnotatedWith(IntentMethod.class);
        //获取IntentField跳转成员变量所有注解信息
        Set<? extends Element> fieldElement = roundEnv.getElementsAnnotatedWith(IntentField.class);

        ExecutableElement executableElement = null;
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


                //获取对应标识中成员变量注解信息,并进行判断
                loadFieldAnnotation(fieldElement, methodValue);

                if (mVariableElementLists.get(methodValue).size() <= 0) {
                    //获取对应标识方法参数注解信息,并进行判断
                    loanParameterAnnotation(executableElement, methodValue);
                }
            }
        }

        //根据获取注解信息，进行代码文件生成
        for (Map.Entry<String, List<VariableElement>> entry : mVariableElementLists.entrySet()) {
            if (!mProcessorUtils.isNotEmpty(entry.getKey())) continue;
            executableElement = mExecutableElementLists.get(entry.getKey());
            for (ActivityGenerator generator : mGenerators) {
                generator.generator(entry.getKey(), entry.getValue(),
                        executableElement, mProcessorUtils, processingEnv);
            }
        }
        return false;
    }

    /**
     * 获取对应标识方法参数注解信息,并进行判断
     *
     * @param executableElement
     * @param methodValue
     */
    private void loanParameterAnnotation(ExecutableElement executableElement, String methodValue) {
        if (executableElement != null) {
            List<? extends VariableElement> parameterElement = executableElement.getParameters();
            for (Element parameter : parameterElement) {
                IntentParameter intentParameter = parameter.getAnnotation(IntentParameter.class);
                if (intentParameter != null) {
                    String parameterValue = intentParameter.value();
                    if (!mProcessorUtils.isNotEmpty(parameterValue)) continue;
                    //方法同一标识判断
                    if (parameterValue.equals(methodValue)) {
                        //IntentField是应用在一般成员变量上的注解
                        mVariableElementLists.get(methodValue).add((VariableElement) parameter);
                    }
                }
            }
        }
    }

    /**
     * 获取对应标识中成员变量注解信息,并进行判断
     *
     * @param fieldElement
     * @param methodValue
     */
    private void loadFieldAnnotation(Set<? extends Element> fieldElement, String methodValue) {
        if (fieldElement != null && fieldElement.size() > 0) {
            VariableElement variableElement;
            for (Element field : fieldElement) {
                ElementKind fieldKind = field.getKind();
                if (fieldKind == ElementKind.FIELD) {
                    //判断Elements类型，并获取Filed参数值
                    variableElement = (VariableElement) field;
                    String filedValue = variableElement.getAnnotation(IntentField.class).value();

                    if (!mProcessorUtils.isNotEmpty(filedValue)) continue;
                    //方法同一标识判断
                    if (filedValue.equals(methodValue)) {
                        //IntentField是应用在一般成员变量上的注解
                        mVariableElementLists.get(methodValue).add((VariableElement) field);
                    }
                }
            }
        }
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
