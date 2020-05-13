package com.linwei.buriedpointlibrary;

import com.linwei.annotation.BindView;
import com.linwei.annotation.OnClick;
import com.linwei.buriedpointlibrary.utils.ProcessorUtils;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

/**
 * @Author: WS
 * @Time: 2020/5/13
 * @Description: View绑定，事件绑定
 */
public class ViewBindingProcessor extends AbstractProcessor {
    private ProcessorUtils mProcessorUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        //注解处理器工具类
        mProcessorUtils = ProcessorUtils.getInstance(processingEnvironment);
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> bindViewElement = roundEnvironment.getElementsAnnotatedWith(BindView.class);
        Set<? extends Element> onClick = roundEnvironment.getElementsAnnotatedWith(OnClick.class);

        return false;
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
}
