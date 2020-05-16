package com.linwei.buriedpointlibrary.logic.bind;

import com.squareup.javapoet.CodeBlock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

public class BindGroupedClasses {

    private HashMap<TypeElement, List<CodeBlock.Builder>> bindMaps = new HashMap<>();

    private BindClasses mBindClasses;

    public BindGroupedClasses(){
        mBindClasses = new BindClasses();
    }


    /**
     * ViewBind注解信息，根据TypeElement进行存储
     *
     * @param element
     */
    public void parseBindView(Element element) {
        ElementKind kind = element.getKind();
        if (kind == ElementKind.FIELD) {
            CodeBlock.Builder builder = mBindClasses.parseBindView(element);
            if(builder!=null) {
                saveCodeBlockData(element, builder);
            }
        }
    }

    /**
     * onClick注解信息，根据TypeElement进行存储
     *
     * @param element
     */
    public void parseListenerView(Element element) {
        ElementKind kind = element.getKind();
        if (kind == ElementKind.METHOD) {
            ArrayList<CodeBlock.Builder> builder = mBindClasses.parseListenerView(element);
            if(builder!=null) {
                saveCodeBlockData(element, builder);
            }
        }
    }


    /**
     * 判断Map信息，并根据TypeElement类型进行存储
     *
     * @param element
     */
    private void saveCodeBlockData(Element element, ArrayList<CodeBlock.Builder> codeBlockList) {
        TypeElement typeElement = (TypeElement) element.getEnclosingElement();
        //判断是否存在TypeElement类型List数据
        List<CodeBlock.Builder> codeBlockLists = bindMaps.get(typeElement);
        if (codeBlockLists == null) {
            codeBlockLists = new ArrayList<>();
        }
        //保存CodeBlock数据
        if (codeBlockList != null) {
            codeBlockLists.addAll(codeBlockList);
        }

        bindMaps.put(typeElement, codeBlockLists);
    }

    /**
     * 判断Map信息，并根据TypeElement类型进行存储
     *
     * @param element
     */
    private void saveCodeBlockData(Element element, CodeBlock.Builder codeBlock) {
        TypeElement typeElement = (TypeElement) element.getEnclosingElement();
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
        TypeElement typeElement = (TypeElement) element.getEnclosingElement();
        //判断是否存在TypeElement类型List数据
        List<CodeBlock.Builder> codeBlockLists = bindMaps.get(typeElement);
        if (codeBlockLists != null) {
            bindMaps.remove(typeElement);
        }
    }

    /**
     * 清楚所有数据
     */
    public void deleteAll(){
        bindMaps.clear();
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
