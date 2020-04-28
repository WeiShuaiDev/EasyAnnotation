package com.linwei.buriedpointlibrary;

import com.google.auto.service.AutoService;
import com.linwei.annotation.Point;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

//@SupportedAnnotationTypes({"com.linwei.buriedpointlibrary.Point"})
//@AutoService(javax.annotation.processing.Processor.class)
//@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class BuriedPointProcessor extends AbstractProcessor {
    /**
     * 做一些初始化工作，注释处理工具框架调用了这个方法，
     * 当我们使用这个方法的时候会给我们传递一个 ProcessingEnvironment
     * 类型的实参。
     * 如果在同一个对象多次调用此方法，则抛出IllegalStateException异常
     *
     * @param processingEnvironment 这个参数里面包含了很多工具方法
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {

        /**
         * 返回用来在元素上进行操作的某些工具方法的实现
         */
        Elements es = processingEnvironment.getElementUtils();
        /**
         * 返回用来创建新源、类或辅助文件的Filer
         */
        Filer filer = processingEnvironment.getFiler();
        /**
         * 返回用来在类型上进行操作的某些实用工具方法的实现
         */
        Types types = processingEnvironment.getTypeUtils();
        /**
         * 这是提供给开发者日志工具，我们可以用来报告错误和警告以及提示信息
         * 注意 message 使用后并不会结束过程
         * Kind 参数表示日志级别
         */
        Messager messager = processingEnvironment.getMessager();
        messager.printMessage(Diagnostic.Kind.NOTE, "例如当默认值为空则提示一个错误");
        /**
         * 返回任何生成的源和类文件应该符合的源版本
         */
        SourceVersion version = processingEnvironment.getSourceVersion();

        super.init(processingEnvironment);
    }

    /**
     * 注解的处理逻辑
     *
     * @param set
     * @param roundEnvironment
     * @return 如果返回true 不要求后续Processor处理它们，反之，则继续执行处理
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        /**
         * TypeElement 这表示一个类或者接口元素集合
         * 常用方法不多，TypeMirror getSuperclass()返回直接超类
         *
         * 详细介绍下 RoundEnvironment 这个类
         * 常用方法：
         * boolean errorRaised() 如果在以前的处理round中发生错误，则返回true
         * Set<? extends Element> getElementsAnnotatedWith(Class<? extends Annotation> a)
         * 这里的 a 即你自定义的注解class类，返回使用给定注解类型注解的元素的集合
         * Set<? extends Element> getElementsAnnotatedWith(TypeElement a)
         *
         * Element 的用法：
         * TypeMirror asType() 返回此元素定义的类型 如int
         * ElementKind getKind() 返回元素的类型 如 e.getkind() = ElementKind.FIELD 字段
         * boolean equals(Object obj) 如果参数表示与此元素相同的元素，则返回true
         * Name getSimpleName() 返回此元素的简单名称
         * List<? extends Elements> getEncloseElements 返回元素直接封装的元素
         * Element getEnclosingElements 返回此元素的最里层元素，如果这个元素是个字段等，则返回为类
         */
        Messager messager = processingEnv.getMessager();
        //第一步，根据我们自定义的注解拿到elememts set 集合
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Point.class);
        TypeElement typeElement;
        VariableElement variableElement;
        ExecutableElement executableElement;
        // 第二步： 根据 element 的类型做相应的处理
        for (Element element : elements) {
            //第三步：返回此元素定义的类型
            ElementKind kind = element.getKind();
            messager.printMessage(Diagnostic.Kind.NOTE, "kind=" + kind );
            if (kind == ElementKind.CLASS) {
                //判断该元素是否为类
                typeElement = (TypeElement) element;
                String qualifiedName = typeElement.getQualifiedName().toString();
                messager.printMessage(Diagnostic.Kind.NOTE, "Name=" + qualifiedName);
            } else if (kind == ElementKind.FIELD) {
                // 判断该元素是否为成员变量
                variableElement = (VariableElement) element;
                //获取该元素的封装类型
                typeElement = (TypeElement) variableElement.getEnclosingElement();
                String qualifiedName = typeElement.getQualifiedName().toString();
                messager.printMessage(Diagnostic.Kind.NOTE, "Name=" + qualifiedName);
            }else if(kind==ElementKind.METHOD){
                executableElement = (ExecutableElement) element;
                typeElement = (TypeElement) executableElement.getEnclosingElement();
                String qualifiedName = typeElement.getQualifiedName().toString();
                messager.printMessage(Diagnostic.Kind.NOTE, "Name=" + qualifiedName);
            }
        }
        return true;
    }


    /**
     * 指出注解处理器 处理哪种注解
     * 在 jdk1.7 中，我们可以使用注解 @SupportedAnnotationTypes()代替
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        //返回一个Set集合，集合内容为 自定义注解的包名+类名。
        LinkedHashSet<String> types = new LinkedHashSet<>();
        types.add(Point.class.getCanonicalName());
        return types;
    }

    /**
     * 指定当前注解器使用的Jdk版本
     * 在 jdk1.7 中，我们可以使用注解 @SupportedSourceVersion()代替
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
