package com.caskey.apibuilder.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ReflectionUtil {
    private ReflectionUtil() {
    }

    public static Type getEntityTypeFromClass(final Class clazz) {
        Type genericSuperclass = clazz.getGenericSuperclass();
        ParameterizedType pType;
        if (genericSuperclass instanceof ParameterizedType) {
            // this class extends another class which is parameterized,
            // so we'll assume we can use its types to pull out the entity class
            pType = (ParameterizedType) genericSuperclass;
        } else {
            pType = (ParameterizedType) clazz.getGenericInterfaces()[0];
        }

        Type entityType = pType.getActualTypeArguments()[0];
        return entityType;
    }

}
