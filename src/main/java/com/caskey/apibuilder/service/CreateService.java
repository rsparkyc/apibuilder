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

        entity = beforeCreate(entity);
        entity = getRepository().save(entity);
        entity = afterCreate(entity);
        return entity;
    }

    default D createAndGetDTO(final D entityDTO, final Integer depth) {
        return getAdapter().toDTO(create(entityDTO), depth);
    }

    default T afterCreate(final T createdEntity) {
        //Not required, but able to be overridden
        return createdEntity;
    }

    default T beforeCreate(final T entity) {
        //we'll use this to make sure all child entities are saved
        return entity;
    }

}
