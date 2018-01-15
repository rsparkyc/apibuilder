package com.caskey.apibuilder.service;

import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.requestBody.BaseEntityDTO;

public interface CreateService<T extends BaseEntity, D extends BaseEntityDTO> extends BaseService<T, D> {

    default T create(final D entityDTO) {
        T entity = getAdapter().toEntity(entityDTO);

        // for creates, we'll want to make sure we have these fields wiped out
        entity.setId(null);
        entity.setModifiedDate(null);
        entity.setCreatedDate(null);

        beforeCreate(entity);
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

    default void beforeCreate(final T entity) {
        //we'll use this to make sure all child entities are saved
    }

}
