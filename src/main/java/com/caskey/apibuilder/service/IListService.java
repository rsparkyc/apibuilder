package com.caskey.apibuilder.service;

import com.caskey.apibuilder.entity.BaseEntity;

public interface IListService<T extends BaseEntity> extends IBaseService<T> {
    default Iterable<T> listAll() {
        return getRepository().findAll();
    }
}
