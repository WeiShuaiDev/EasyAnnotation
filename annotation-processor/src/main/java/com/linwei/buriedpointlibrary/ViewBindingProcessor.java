package com.linwei.buriedpointlibrary;

import com.linwei.annotation.BindView;
import com.linwei.annotation.OnClick;
import com.linwei.buriedpointlibrary.logic.bind.BindGroupedClasses;
import com.linwei.buriedpointlibrary.template.bind.BindGenerator;
import com.linwei.buriedpointlibrary.utils.ProcessorUtils;
import com.squareup.javapoet.CodeBlock;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

/**
 * @Author: WS
 * @Time: 2020/5/13
 * @Description: View绑定，事件绑定
 */
public class ViewBindingProcessor extends AbstractProcessor {

    private ProcessorUtils mProcessorUtils;

    private BindGroupedClasses mBindGroupedClasses;

    private BindGenerator mBindGenerator;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        //注解处理器工具类
        mProcessorUtils = ProcessorUtils.getInstance(processingEnvironment);
        //解析注解信息类
        mBindGroupedClasses = new BindGroupedClasses();
        //生产模板对象
        mBindGenerator = new BindGenerator();

    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        //获取@BindView注解信息
        Set<? extends Element> bindViewElements = roundEnvironment.getElementsAnnotatedWith(BindView.class);
        //获取@OnClick注解信息
        Set<? extends Element> onClickElements = roundEnvironment.getElementsAnnotatedWith(OnClick.class);

        //校验@BindView，声明变量public View v
        for (Element fieldElement : bindViewElements) {
            if(checkValidFiled(fieldElement))
                mBindGroupedClasses.parseBindView(fieldElement);
        }

        //校验@OnClick,声明方法public void onClick（View view）{}
        for (Element executableElement : onClickElements) {
            if (checkMethodElement(executableElement))
                mBindGroupedClasses.parseListenerView(executableElement);
        }

        try {
            //解析注解配置信息，并生成写入文件
            HashMap<TypeElement, List<CodeBlock.Builder>> bindMaps = mBindGroupedClasses.getBindMaps();
            if(bindMaps.size()>0) {
                for (Map.Entry<TypeElement, List<CodeBlock.Builder>> entry : bindMaps.entrySet()) {
                    mBindGenerator.generator(entry.getKey(), entry.getValue(), mProcessorUtils, processingEnv);
                }

                mBindGroupedClasses.deleteAll();
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        LinkedHashSet<String> type = new LinkedHashSet<>(2);
        type.add(BindView.class.getCanonicalName());
        type.add(OnClick.class.getCanonicalName());
        return type;
    }

    /**
     * 校验字段，格式：public View v;
     * @param element
     * @return
     */
    private boolean checkValidFiled(Element element){
        if(element==null) return false;
        //判断变量访问修饰符
        if(!element.getModifiers().contains(Modifier.PUBLIC)){
            mProcessorUtils.eLog("The field $s not public",
                    element.getSimpleName());
            return false;
        }
        return true;
    }

    /**
     * 校验方法,格式: public void onClick(View v){}
     * @param element
     * @return
     */
    private boolean checkMethodElement(Element element){
        if(element==null) return false;
        ExecutableElement executableElement=(ExecutableElement)element;
        //判断方法访问修饰符
        if(!executableElement.getModifiers().contains(Modifier.PUBLIC)){
            mProcessorUtils.eLog("The method $s not public",
                    element.getSimpleName());
            return false;
        }
        //判断方法的返回类型
        TypeMirror returnType = executableElement.getReturnType();
        if(returnType.getKind()!= TypeKind.VOID){
            mProcessorUtils.eLog("The  method $s return type not void",
                    element.getSimpleName());
            return false;
        }
        //判断方法返回参数
        List<? extends VariableElement> parameters = executableElement.getParameters();
        if(parameters.size()!=1){
            mProcessorUtils.eLog("The  method $s parameter size entry",
                    element.getSimpleName());
            return  false;
        }

        return true;
    }
}
