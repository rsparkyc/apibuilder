package com.caskey.apibuilder.service;

import com.caskey.apibuilder.adapter.BaseEntityAdapter;
import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.exception.MissingEntityException;
import com.caskey.apibuilder.requestBody.BaseEntityDTO;

public interface UpdateService<T extends BaseEntity, D extends BaseEntityDTO> extends BaseService<T, D> {

    default T update(final D entityDTO) throws MissingEntityException {
        BaseEntityAdapter<T, D> adapter = getAdapterRegistry().getAdapter(getEntityType());

        T entity = getRepository().findOne(entityDTO.getId());
        if (entity == null) {
            throw new MissingEntityException();
        }

        adapter.mapFromDtoToEntity(entityDTO, entity);

        entity = getRepository().save(entity);
        afterUpdate(entity);
        return entity;
    }

    default D updateAndGetDTO(final D entityDTO) throws MissingEntityException {
        T entity = update(entityDTO);
        return getAdapter().toDTO(entity);
    }

    default T update(final T entity) throws MissingEntityException {
        if (entity == null) {
            throw new MissingEntityException();
        }
        T updatedEntity = getRepository().save(entity);
        afterUpdate(updatedEntity);
        return updatedEntity;
    }

    default void afterUpdate(final T updatedEntity) {
        //Not required, but able to be overridden
    }
}
