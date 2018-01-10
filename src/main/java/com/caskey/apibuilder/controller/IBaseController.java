package com.caskey.apibuilder.controller;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.caskey.apibuilder.entity.BaseEntity;

interface IBaseController<T extends BaseEntity> {
    default Type getEntityType() {
        return ((ParameterizedType) getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
    }

}
