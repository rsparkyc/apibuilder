package com.caskey.apibuilder.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectionUtil {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);

    private ReflectionUtil() {
    }

    private static Type getTypeAtGenericIndex(final Class clazz, int argumentIndex) {
        Type genericSuperclass = clazz.getGenericSuperclass();
        ParameterizedType pType;
        if (genericSuperclass instanceof ParameterizedType) {
            // this class extends another class which is parameterized,
            // so we'll assume we can use its types to pull out the entity class
            pType = (ParameterizedType) genericSuperclass;
        } else {
            pType = (ParameterizedType) clazz.getGenericInterfaces()[0];
        }

        Type type = pType.getActualTypeArguments()[argumentIndex];
        return type;
    }

    public static <T> T getTypeArgumentInstance(final Class clazz, int argumentIndex) {
        Type type = getTypeAtGenericIndex(clazz, argumentIndex);
        try {
            return (T) ((Class) type).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("Could not create instance of " + ((Class) type).getSimpleName(), e);
            return null;
        }
    }

    public static Type getEntityTypeFromClass(final Class clazz) {
        return getTypeAtGenericIndex(clazz, 0);
    }

    public static Type getDTOTypeFromClass(final Class clazz) {
        return getTypeAtGenericIndex(clazz, 1);
    }

}
