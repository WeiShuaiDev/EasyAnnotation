package com.linwei.buriedpointlibrary;

import com.linwei.annotation.ObjectFactory;
import com.linwei.buriedpointlibrary.exception.IdAlreadyUsedException;
import com.linwei.buriedpointlibrary.logic.object.ObjectFactoryClasses;
import com.linwei.buriedpointlibrary.logic.object.ObjectFactoryGroupedClasses;
import com.linwei.buriedpointlibrary.template.object.ObjectFactoryGenerator;
import com.linwei.buriedpointlibrary.utils.ProcessorUtils;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

/**
 * @Author: WS
 * @Time: 2020/5/12
 * @Description: 实现对象工厂模式
 */
public class ObjectFactoryProcessor extends AbstractProcessor {
    private ProcessorUtils mProcessorUtils;
    private LinkedHashMap<String, ObjectFactoryGroupedClasses> mGroupedClassesMap;
    private ObjectFactoryGenerator mGenerator;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        //注解处理器工具类
        mProcessorUtils = ProcessorUtils.getInstance(processingEnv);
        //根据ObjectFactory.object()类型，存储不同的子类
        mGroupedClassesMap = new LinkedHashMap<>();
        //生产模板对象
        mGenerator = new ObjectFactoryGenerator();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> factoryElements =
                roundEnvironment.getElementsAnnotatedWith(ObjectFactory.class);
        for (Element element : factoryElements) {
            ObjectFactoryClasses classes = null;
            try {
                ElementKind kind = element.getKind();
                if (kind == ElementKind.CLASS) {
                    classes = new ObjectFactoryClasses((TypeElement) element);
                    if (!checkValidClass(classes)) {
                        return true; //检查不符合要求，进行错误日志输出
                    }

                    ObjectFactoryGroupedClasses groupedClasses = mGroupedClassesMap.get(classes.getQualifiedName());
                    if (groupedClasses == null) {
                        groupedClasses = new ObjectFactoryGroupedClasses();
                        mGroupedClassesMap.put(classes.getQualifiedName(), groupedClasses);
                    }

                    groupedClasses.addObjectFactoryClasses(classes);

                }
            } catch (IllegalArgumentException e) {
                mProcessorUtils.eLog(e.getMessage());
                return true;
            } catch (IdAlreadyUsedException e) {
                ObjectFactoryClasses existing = e.getExisting();
                assert classes != null;
                mProcessorUtils.eLog(
                        "Conflict: The class %s is annotated with @%s with id ='%s' but %s already uses the same id",
                        classes.getQualifiedName(), ObjectFactory.class.getSimpleName(),
                        existing.getTypeElement().getQualifiedName().toString());
                return true;
            }
        }

        try {
            //生成模板代码
            for (String objects : mGroupedClassesMap.keySet()) {
                mGenerator.generator(objects, mGroupedClassesMap.get(objects), mProcessorUtils, processingEnv);
            }
            //process()多次调用处理，所以配置信息及时释放
            mGroupedClassesMap.clear();
        } catch (Exception e) {
            mProcessorUtils.eLog(e.getMessage());
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
     * @param classes
     * @return
     */
    private boolean checkValidClass(ObjectFactoryClasses classes) {
        TypeElement typeElement = classes.getTypeElement();

        //检查该类限定符
        if (!typeElement.getModifiers().contains(Modifier.PUBLIC)) {
            mProcessorUtils.eLog("The class $s not public",
                    classes.getQualifiedName());
            return false;
        }

        //检查是否是一个抽象类
        if (typeElement.getModifiers().contains(Modifier.ABSTRACT)) {
            mProcessorUtils.eLog("The class $s is abstract," +
                            "You can't annotate abstract classes with @%s",
                    classes.getQualifiedName(), ObjectFactory.class.getSimpleName());
            return false;
        }

        //检查继承关系，必须是@ObjectFactory.object()类型子类
        TypeElement superTypeElement = mProcessorUtils.processorElementUtils().
                getTypeElement(classes.getQualifiedName());
        //检查是否为接口类型
        if (superTypeElement.getKind() == ElementKind.INTERFACE) {
            if (!typeElement.getInterfaces().contains(superTypeElement.asType())) {
                mProcessorUtils.eLog("The class %s annotated with @%s must implement the interface %s",
                        typeElement.getQualifiedName().toString(), ObjectFactory.class.getSimpleName(),
                        classes.getQualifiedName());
                return false;
            } else {
                TypeElement currentClass = typeElement;
                while (true) {
                    TypeMirror superclassType = currentClass.getSuperclass();
                    if (superclassType.getKind() == TypeKind.NONE) {
                        // 到达了基本类型(java.lang.Object), 所以退出
                        mProcessorUtils.eLog("The class %s annotated with @%s must inherit from %s",
                                typeElement.getQualifiedName().toString(), ObjectFactory.class.getSimpleName(),
                                classes.getQualifiedName());
                        return false;
                    }

                    if (superclassType.toString().equals(classes.getQualifiedName())) {
                        break;
                    }

                    currentClass = (TypeElement) mProcessorUtils.processorTypeUtils().asElement(superclassType);
                }
            }
        }

        for (Element element : typeElement.getEnclosedElements()) {
            if (element.getKind() == ElementKind.CONSTRUCTOR) {
                ExecutableElement constructorElement = (ExecutableElement) element;
                if (constructorElement.getParameters().size() == 0 && constructorElement.getModifiers().contains(Modifier.PUBLIC)) {
                    return true;
                }
            }
        }

        // 没有找到默认构造函数
        mProcessorUtils.eLog("The class %s must provide an public empty default constructor",
                classes.getQualifiedName());

        return false;
    }
}
