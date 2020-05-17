package com.linwei.buriedpointlibrary.logic.intent;

import com.linwei.annotation.IntentField;
import com.linwei.annotation.IntentParameter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;

public class JumpIntentClasses {

    /**
     * 获取对应标识方法参数注解信息,并进行判断
     *
     * @param executableElement
     * @param methodValue
     */
    Map<String, List<VariableElement>> loanParameterAnnotation(Map<String, List<VariableElement>> variableElementLists,
                                                               ExecutableElement executableElement, String methodValue) {
        if (executableElement != null) {
            List<? extends VariableElement> parameterElement = executableElement.getParameters();
            for (Element parameter : parameterElement) {
                IntentParameter intentParameter = parameter.getAnnotation(IntentParameter.class);
                if (intentParameter != null) {
                    String parameterValue = intentParameter.value();
                    if ("".equals(parameterValue)) continue;
                    //方法同一标识判断
                    if (parameterValue.equals(methodValue)) {
                        //IntentField是应用在一般成员变量上的注解
                        variableElementLists.get(methodValue).add((VariableElement) parameter);
                    }
                }
            }
        }
     return variableElementLists;
    }

    /**
     * 获取对应标识中成员变量注解信息,并进行判断
     *
     * @param fieldElement
     * @param methodValue
     */
    Map<String, List<VariableElement>> loadFieldAnnotation(Map<String, List<VariableElement>> variableElementLists,
                                                           Set<? extends Element> fieldElement, String methodValue) {
        if (fieldElement != null && fieldElement.size() > 0) {
            VariableElement variableElement;
            for (Element field : fieldElement) {
                ElementKind fieldKind = field.getKind();
                if (fieldKind == ElementKind.FIELD) {
                    //判断Elements类型，并获取Filed参数值
                    variableElement = (VariableElement) field;
                    String filedValue = variableElement.getAnnotation(IntentField.class).value();

                    if ("".equals(filedValue)) continue;
                    //方法同一标识判断
                    if (filedValue.equals(methodValue)) {
                        //IntentField是应用在一般成员变量上的注解
                        variableElementLists.get(methodValue).add((VariableElement) field);
                    }
                }
            }
        }
        return variableElementLists;
    }
}
