package com.linwei.buriedpointlibrary.logic.bind;

import com.squareup.javapoet.CodeBlock;

import java.util.HashMap;
import java.util.List;

import javax.lang.model.element.TypeElement;

public class BindGroupedClasses {

    private HashMap<TypeElement, List<CodeBlock.Builder>> bindMaps=new HashMap<>();


    /**
     * 生成模板代码注解
     * @return
     */
    public HashMap<TypeElement,List<CodeBlock.Builder>> getBindMaps(){
        return bindMaps;
    }
}
