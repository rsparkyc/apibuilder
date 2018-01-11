package com.caskey.apibuilder.controller;

import java.lang.reflect.Type;

import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.util.ReflectionUtil;

interface BaseController<T extends BaseEntity> {
    default Type getEntityType() {
        return ReflectionUtil.getEntityTypeFromClass(getClass());
    }

}
