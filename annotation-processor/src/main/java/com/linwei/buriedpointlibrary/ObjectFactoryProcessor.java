package com.linwei.buriedpointlibrary;

import com.linwei.annotation.ObjectFactory;
import com.linwei.buriedpointlibrary.factory.ObjectFactoryClasses;
import com.linwei.buriedpointlibrary.utils.ProcessorUtils;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

/**
 * @Author: WS
 * @Time: 2020/5/12
 * @Description: 实现对象工厂模式
 */
public class ObjectFactoryProcessor extends AbstractProcessor {
    private ProcessorUtils mProcessorUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mProcessorUtils = ProcessorUtils.getInstance(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> factoryElements =
                roundEnvironment.getElementsAnnotatedWith(ObjectFactory.class);
        for (Element element : factoryElements) {
            ElementKind kind = element.getKind();
            if (kind == ElementKind.CLASS) {
                ObjectFactoryClasses classes = new ObjectFactoryClasses((TypeElement) element);
                if (checkValidClass(classes)) {

                }
            }
        }
        return true;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_7;
    }


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        LinkedHashSet<String> types = new LinkedHashSet<>(1);
        types.add(ObjectFactory.class.getCanonicalName());
        return types;
    }

    /**
     * 类型校验
     *
     * @param classs
     * @return
     */
    private boolean checkValidClass(ObjectFactoryClasses classs) {
        return false;
    }
}
