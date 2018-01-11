package com.caskey.apibuilder.service;

import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.requestBody.BaseEntityDTO;

public interface CreateService<T extends BaseEntity, D extends BaseEntityDTO> extends BaseService<T, D> {

    default T create(final D entityDTO) {
        T entity = getAdapter().toEntity(entityDTO);
        entity = getRepository().save(entity);
        afterCreate(entity);
        return entity;
    }

    default D createAndGetDTO(final D entityDTO, final Long depth) {
        return getAdapter().toDTO(create(entityDTO), depth);
    }

    default void afterCreate(final T createdEntity) {
        //Not required, but able to be overridden
    }

}
