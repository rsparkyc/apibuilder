package com.caskey.apibuilder.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.requestBody.BaseEntityDTO;
import com.caskey.apibuilder.util.ChildEntitySavingUtil;

public interface CreateService<T extends BaseEntity, D extends BaseEntityDTO> extends BaseService<T, D> {

    default T create(final T entity) {
        // for creates, we'll want to make sure we have these fields wiped out
        entity.setId(null);
        entity.setModifiedDate(null);
        entity.setCreatedDate(null);

        T theEntity = beforeCreate(entity);
        theEntity = ChildEntitySavingUtil.saveAnyChildren(theEntity, getRegistryWrapper());
        theEntity = getRepository().save(theEntity);
        theEntity = afterCreate(theEntity);
        afterSaveHooks(theEntity);
        return theEntity;
    }

    default void afterSaveHooks(final T theEntity) {
        getRegistryWrapper().getHookService().runHooks(theEntity);
    }

    default T create(final D entityDTO) {
        return create(getAdapter().toEntity(entityDTO));
    }

    default D createAndGetDTO(final D entityDTO, final Integer depth) {
        return getAdapter().toDTO(create(entityDTO), depth, false);
    }

    default T afterCreate(final T createdEntity) {
        //Not required, but able to be overridden
        return createdEntity;
    }

    default T beforeCreate(final T entity) {
        //we'll use this to make sure all child entities are saved
        return entity;
    }

    final class LogHolder { // not public
        static final Logger LOGGER = LoggerFactory.getLogger(CreateService.class);
    }

}
