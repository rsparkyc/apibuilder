package com.caskey.apibuilder.service;

import com.caskey.apibuilder.entity.BaseEntity;

public interface ListService<T extends BaseEntity> extends BaseService<T> {
    default Iterable<T> listAll() {
        return getRepository().findAll();
    }
}
