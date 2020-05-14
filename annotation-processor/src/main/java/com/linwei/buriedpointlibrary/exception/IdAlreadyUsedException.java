package com.linwei.buriedpointlibrary.exception;

import com.linwei.buriedpointlibrary.logic.object.ObjectFactoryClasses;

/**
 * @Author: WS
 * @Time: 2020/5/12
 * @Description: 对象重复实例化异常
 */
public class IdAlreadyUsedException extends RuntimeException {
    private ObjectFactoryClasses mClasses;

    public IdAlreadyUsedException() {
    }

    public IdAlreadyUsedException(ObjectFactoryClasses classes) {
        super(classes.getQualifiedName() + "is already used!");
        this.mClasses = classes;
    }

    public ObjectFactoryClasses getExisting() {
        return mClasses;
    }
}
