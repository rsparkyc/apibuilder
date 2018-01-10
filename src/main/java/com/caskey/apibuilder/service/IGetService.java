package com.caskey.apibuilder.service;

import com.caskey.apibuilder.entity.BaseEntity;

public interface IGetService<T extends BaseEntity> extends IBaseService<T> {
    default T getById(final Long id) {
        return getRepository().findOne(id);
    }

}
