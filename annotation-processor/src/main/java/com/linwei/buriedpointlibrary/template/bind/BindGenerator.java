package com.linwei.buriedpointlibrary.template.bind;

import com.linwei.buriedpointlibrary.utils.ProcessorUtils;
import com.squareup.javapoet.CodeBlock;

import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;

/**
 * @Author: WS
 * @Time: 2020/5/13
 * @Description: ViewBind生成模板代码接口
 */
public class BindGenerator {

    public void generator(TypeElement typeElement,
                          List<CodeBlock.Builder> codeBlock,
                          ProcessorUtils processorUtils,
                          ProcessingEnvironment processingEnv) {

        //生成模板代码

    }
}
