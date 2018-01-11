package com.caskey.apibuilder.service;

import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.requestBody.BaseEntityDTO;

public interface ListService<T extends BaseEntity, D extends BaseEntityDTO> extends BaseService<T, D> {
    default Iterable<T> listAll() {
        return getRepository().findAll();
    }

    default Iterable<D> listAllDTOs() {
        return getAdapter().toDTOs(getRepository().findAll());
    }
}
