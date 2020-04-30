package com.linwei.buriedpointlibrary.template;

import com.linwei.buriedpointlibrary.utils.ProcessorUtils;

import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;

/**
 * @Author: linwei
 * @Time: 2020/4/30
 * @Description: 生成模板代码接口
 */
public interface Generator {

    /**
     * 根据注解配置信息，{{@link VariableElement}}生成“样板”代码
     *
     * @param clazzType
     * @param variableElements
     * @param processingEnv
     */
    void generator(String clazzType,
                   List<VariableElement> variableElements,
                   ExecutableElement executableElement,
                   ProcessorUtils processorUtils,
                   ProcessingEnvironment processingEnv);
}
