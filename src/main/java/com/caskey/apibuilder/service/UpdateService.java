package com.caskey.apibuilder.service;

import java.util.Date;

import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.exception.MissingEntityException;
import com.caskey.apibuilder.requestBody.BaseEntityDTO;

public interface UpdateService<T extends BaseEntity, D extends BaseEntityDTO> extends BaseService<T, D> {

    default T update(final Long id, final D entityDTO) throws MissingEntityException {

        T entity = getRepository().findOne(id);
        if (entity == null) {
            throw new MissingEntityException();
        }
        Date originalCreatedDate = entity.getCreatedDate();

        //override any id they passed in the object, we should be using the id we passed in
        entityDTO.setId(id);

        getAdapter().doReflectiveFieldMapping(entityDTO, entity, Integer.MAX_VALUE);

        // just to make sure we didn't try to modify that with the DTO
        entity.setCreatedDate(originalCreatedDate);

        return update(entity);
    }

    default D updateAndGetDTO(final Long id, final D entityDTO, final Integer depth)
            throws MissingEntityException {
        T entity = update(id, entityDTO);
        return getAdapter().toDTO(entity, depth);
    }

    default T update(final T entity) throws MissingEntityException {
        if (entity == null) {
            throw new MissingEntityException();
        }

        beforeUpdate(entity);
        T updatedEntity = getRepository().save(entity);
        afterUpdate(updatedEntity);
        return updatedEntity;
    }

    default void beforeUpdate(final T entity) {
        //Not required, but able to be overridden
    }

    default void afterUpdate(final T updatedEntity) {
        //Not required, but able to be overridden
    }
}
