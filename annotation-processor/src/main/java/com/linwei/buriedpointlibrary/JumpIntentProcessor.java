package com.linwei.buriedpointlibrary;

import com.linwei.annotation.IntentMethod;
import com.linwei.annotation.IntentField;
import com.linwei.buriedpointlibrary.logic.intent.JumpIntentGroupedClasses;
import com.linwei.buriedpointlibrary.template.intent.ActivityEnterGenerator;
import com.linwei.buriedpointlibrary.template.intent.ActivityOutGenerator;
import com.linwei.buriedpointlibrary.template.intent.ActivityGenerator;
import com.linwei.buriedpointlibrary.utils.ProcessorUtils;

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

    private List<ActivityGenerator>  mGenerators;

    private JumpIntentGroupedClasses mJumpIntentGroupedClasses;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        //注解处理器工具类
        mProcessorUtils = ProcessorUtils.getInstance(processingEnv);
        //解析注解信息类
        mJumpIntentGroupedClasses = new JumpIntentGroupedClasses();
        //生产模板对象
        initGenerated();
    }


    /**
     * 初始化Generated
     */
    private void initGenerated() {
        mGenerators = new LinkedList<>();
        mGenerators.add(new ActivityEnterGenerator());
        mGenerators.add(new ActivityOutGenerator());
    }


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        //获取IntentMethod跳转方法所有注解信息
        Set<? extends Element> methodElement = roundEnv.getElementsAnnotatedWith(IntentMethod.class);
        //获取IntentField跳转成员变量所有注解信息
        Set<? extends Element> fieldElement = roundEnv.getElementsAnnotatedWith(IntentField.class);

        //解析注解信息
        mJumpIntentGroupedClasses.loanAnnotation(fieldElement,methodElement);

        Map<String, ExecutableElement> executableElementLists = mJumpIntentGroupedClasses.getExecutableElementLists();

        Map<String, List<VariableElement>> variableElementLists = mJumpIntentGroupedClasses.getVariableElementLists();


        //根据获取注解信息，进行代码文件生成
        ExecutableElement executableElement;
        for (Map.Entry<String, List<VariableElement>> entry : variableElementLists.entrySet()) {
            if (!mProcessorUtils.isNotEmpty(entry.getKey())) continue;
            executableElement = executableElementLists.get(entry.getKey());
            for (ActivityGenerator generator : mGenerators) {
                generator.generator(entry.getKey(), entry.getValue(),
                        executableElement, mProcessorUtils, processingEnv);
            }
        }

        //process()多次调用处理，所以配置信息及时释放
        variableElementLists.clear();
        executableElementLists.clear();
        return true;
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
