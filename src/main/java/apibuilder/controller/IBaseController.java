package apibuilder.controller;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import apibuilder.entity.BaseEntity;

interface IBaseController<T extends BaseEntity> {
    default Type getEntityType() {
        Type entityType = ((ParameterizedType) getClass()
                .getGenericInterfaces()[0]).getActualTypeArguments()[0];
        return entityType;
    }

}
