package com.caskey.apibuilder.controller;

import java.lang.reflect.Type;

import com.caskey.apibuilder.RegistryWrapper;
import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.requestBody.BaseEntityDTO;
import com.caskey.apibuilder.util.ReflectionUtil;

interface BaseController<T extends BaseEntity, D extends BaseEntityDTO> {

    RegistryWrapper<T, D> getRegistryWrapper();

    default Type getEntityType() {
        return ReflectionUtil.getEntityTypeFromClass(getClass());
    }

}
