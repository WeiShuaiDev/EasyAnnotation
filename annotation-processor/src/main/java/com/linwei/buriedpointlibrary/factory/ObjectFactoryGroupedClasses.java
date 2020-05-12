package com.linwei.buriedpointlibrary.factory;

import com.linwei.buriedpointlibrary.exception.IdAlreadyUsedException;

import java.util.LinkedHashMap;

/**
 * @Author: WS
 * @Time: 2020/5/12
 * @Description: 保存TypeElements对象信息
 */
public class ObjectFactoryGroupedClasses {

    private LinkedHashMap<String, ObjectFactoryClasses> mObjectFactoryMaps = new
            LinkedHashMap<>();

    /**
     * 保存ObjectFactory对象
     */
    public void addObjectFactoryClasses(ObjectFactoryClasses classes) throws IdAlreadyUsedException {
        ObjectFactoryClasses objectFactoryClasses = mObjectFactoryMaps.get(classes.getKey());
        if (objectFactoryClasses != null) {
            throw new IdAlreadyUsedException(objectFactoryClasses);
        }

        mObjectFactoryMaps.put(classes.getKey(), classes);
    }

    /**
     * 获取ObjectFactory对象
     *
     * @param key
     */
    public ObjectFactoryClasses getObjectFactoryClasses(String key) {
        return mObjectFactoryMaps.get(key);
    }

}
