package com.linwei.buriedpointlibrary.logic.intent;

import com.linwei.annotation.IntentMethod;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;

public class JumpIntentGroupedClasses {
    private JumpIntentClasses mJumpIntentClasses;

    //保存指定类型,所有VariableElement数据
    private Map<String, List<VariableElement>> mVariableElementLists = new HashMap<>();

    //保存指定类型，对应ExecutableElement数据
    private Map<String, ExecutableElement> mExecutableElementLists = new HashMap<>();

    public JumpIntentGroupedClasses(){
        mJumpIntentClasses=new JumpIntentClasses();
    }

    /**
     * 获取ExecutableElement集合数据
     * @return
     */
    public Map<String,ExecutableElement> getExecutableElementLists(){
        return mExecutableElementLists;
    }

    /**
     * 获取VariableElement集合数据
     * @return
     */
    public Map<String,List<VariableElement>> getVariableElementLists(){
        return mVariableElementLists;
    }

    /**
     * 获取注解信息
     * @param fieldElement
     * @param methodElement
     */
    public void loanAnnotation(Set<? extends Element> fieldElement, Set<? extends Element> methodElement){

        ExecutableElement executableElement = null;
        String methodValue = "";

        for (Element method : methodElement) {
            ElementKind methodKind = method.getKind();
            if (methodKind == ElementKind.METHOD) {
                //判断Elements类型，并获取Method参数值
                executableElement = (ExecutableElement) method;
                methodValue = executableElement.getAnnotation(IntentMethod.class).value();
            }

            //主要获取ElementType 是不是null，即class，interface，enum或者注解类型
            Element typeElement = method.getEnclosingElement();
            //判断方法参数，classFlag跳转标识判断
            if (!"".equals(methodValue)) {
                //如果mVariableElementLists的key不存在，则添加一个key
                if (mVariableElementLists.get(methodValue) == null) {
                    mVariableElementLists.put(methodValue, new LinkedList<VariableElement>());
                }

                mExecutableElementLists.put(methodValue, executableElement);

                //获取对应标识中成员变量注解信息,并进行判断
                mVariableElementLists=mJumpIntentClasses.loadFieldAnnotation(mVariableElementLists,fieldElement, methodValue);

                if (mVariableElementLists.get(methodValue).size() <= 0) {
                    //获取对应标识方法参数注解信息,并进行判断
                    mVariableElementLists= mJumpIntentClasses.loanParameterAnnotation(mVariableElementLists,executableElement, methodValue);
                }
            }
        }
    }
}
