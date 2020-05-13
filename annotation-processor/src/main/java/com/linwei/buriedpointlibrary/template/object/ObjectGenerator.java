package com.linwei.buriedpointlibrary.template.object;

import com.linwei.buriedpointlibrary.factory.ObjectFactoryGroupedClasses;
import com.linwei.buriedpointlibrary.utils.ProcessorUtils;

import javax.annotation.processing.ProcessingEnvironment;

/**
 * @Author: WS
 * @Time: 2020/5/13
 * @Description: Object对象生成模板类
 */
public interface ObjectGenerator {

    /**
     * 根据{@link ObjectFactoryGroupedClasses} 配置，生产对象工厂类
     *
     * @param object         父类标识
     * @param groupedClasses 生成ObjectFactory工厂类配置信息
     * @param processorUtils 注解处理器工具类
     * @param processingEnv
     */
    void generator(String object,
                   ObjectFactoryGroupedClasses groupedClasses,
                   ProcessorUtils processorUtils,
                   ProcessingEnvironment processingEnv);
}
