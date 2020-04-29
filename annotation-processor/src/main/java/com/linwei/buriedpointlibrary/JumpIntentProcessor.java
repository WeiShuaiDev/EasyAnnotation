package com.linwei.buriedpointlibrary;

import com.google.auto.service.AutoService;
import com.linwei.annotation.IntentField;
import com.linwei.annotation.Point;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

@AutoService(javax.annotation.processing.Processor.class)
public class JumpIntentProcessor extends AbstractProcessor {

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return true;
    }

    /**
     * 指出注解处理器 处理哪种注解
     * 在 jdk1.7 中，我们可以使用注解 @SupportedAnnotationTypes()代替
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        LinkedHashSet<String> types = new LinkedHashSet<>();
        types.add(IntentField.class.getCanonicalName());
        return types;
    }

    /**
     * 指定当前注解器使用的Jdk版本
     * 在 jdk1.7 中，我们可以使用注解 @SupportedSourceVersion()代替
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return super.getSupportedSourceVersion();
    }
}
