package com.linwei.buriedpointlibrary.logic.bind;

import com.squareup.javapoet.CodeBlock;

import java.lang.annotation.ElementType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

public class BindGroupedClasses {

    private HashMap<TypeElement, List<CodeBlock.Builder>> bindMaps = new HashMap<>();


    /**
     * 解析ViewBind注解信息，并转换为CodeBlock.builder数据
     *
     * @param element
     */
    public void parseBindView(Element element) {
        ElementKind kind = element.getKind();
        if (kind == ElementKind.FIELD) {
            TypeElement typeElement = (TypeElement) element.getEnclosingElement();
        }
    }

    /**
     * 解析OnClick注解信息,并转换为CodeBlock.builder数据
     *
     * @param element
     */
    public void parseListenerView(Element element) {
        ElementKind kind = element.getKind();
        if (kind == ElementKind.METHOD) {

        }
    }

    /**
     * 判断Map信息，并根据TypeElement类型进行存储
     *
     * @param element
     */
    public void saveCodeBlockData(Element element, CodeBlock.Builder codeBlock) {
        TypeElement typeElement = (TypeElement) element;
        //判断是否存在TypeElement类型List数据
        List<CodeBlock.Builder> codeBlockLists = bindMaps.get(typeElement);
        if (codeBlockLists == null) {
            codeBlockLists = new ArrayList<>();
        }
        //保存CodeBlock数据
        if (codeBlock != null) {
            codeBlockLists.add(codeBlock);
        }

        bindMaps.put(typeElement, codeBlockLists);
    }

    /**
     * 根据TypeElement类型删除List数据
     *
     * @param element
     */
    public void deleteCodeBlockData(Element element) {
        TypeElement typeElement = (TypeElement) element;
        //判断是否存在TypeElement类型List数据
        List<CodeBlock.Builder> codeBlockLists = bindMaps.get(typeElement);
        if (codeBlockLists != null) {
            bindMaps.remove(typeElement);
        }
    }


    /**
     * 生成模板代码注解
     *
     * @return
     */
    public HashMap<TypeElement, List<CodeBlock.Builder>> getBindMaps() {
        return bindMaps;
    }
}
