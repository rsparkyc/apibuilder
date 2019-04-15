package com.caskey.apibuilder.service;

import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.requestBody.BaseEntityDTO;

public interface GetService<T extends BaseEntity, D extends BaseEntityDTO> extends BaseService<T, D> {
    default T getById(final Long id) {
        return getRepository().getOne(id);
    }

    default D getDTOById(final Long id, final Integer depth) {
        return getAdapter().toDTO(getById(id), depth, true);
    }
}
