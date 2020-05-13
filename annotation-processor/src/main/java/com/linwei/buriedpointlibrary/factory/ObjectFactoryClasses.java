package com.linwei.buriedpointlibrary.factory;

import com.linwei.annotation.ObjectFactory;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;

/**
 * @Author: WS
 * @Time: 2020/5/12
 * @Description: TypeElements包装类，
 */
public class ObjectFactoryClasses {
    private TypeElement mTypeElement;
    private String mQualifiedName;
    private String mSimpleName;
    private String mKey;

    public ObjectFactoryClasses(TypeElement typeElement) {
        this.mTypeElement = typeElement;

        ObjectFactory factory = mTypeElement.getAnnotation(ObjectFactory.class);
        this.mKey = factory.key();

        if ("".equals(mKey)) {
            throw new IllegalArgumentException(
                    String.format("key() in @%s for class %s is null or empty! that's not allowed",
                            ObjectFactory.class.getSimpleName(), mTypeElement.getQualifiedName().toString()));
        }

        try {
            Class<?> clazz = factory.type();
            mQualifiedName = clazz.getCanonicalName();
            mSimpleName = clazz.getSimpleName();
        } catch (MirroredTypeException mte) {
            DeclaredType classTypeMirror = (DeclaredType) mte.getTypeMirror();
            TypeElement classTypeElement = (TypeElement) classTypeMirror.asElement();
            mQualifiedName = classTypeElement.getQualifiedName().toString();
            mSimpleName = classTypeElement.getSimpleName().toString();
        }
    }

    /**
     * 获取在{@link ObjectFactory#key()}指定的类型合法全名
     *
     * @return
     */
    public String getQualifiedName() {
        return mQualifiedName;
    }

    /**
     * 获取在 {@link ObjectFactory#key()} 中指定的类型的简单名字
     *
     * @return qualified name
     */
    public String getSimpleName() {
        return mSimpleName;
    }

    /**
     * 返回TypeElement
     *
     * @return
     */
    public TypeElement getTypeElement() {
        return mTypeElement;
    }

    /**
     * 获取对象标识
     *
     * @return
     */
    public String getKey() {
        return mKey;
    }

}
