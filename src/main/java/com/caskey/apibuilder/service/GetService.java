package com.caskey.apibuilder.service;

import com.caskey.apibuilder.entity.BaseEntity;

public interface GetService<T extends BaseEntity> extends BaseService<T> {
    default T getById(final Long id) {
        return getRepository().findOne(id);
    }

}
