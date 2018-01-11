package com.caskey.apibuilder.adapter;

import com.caskey.apibuilder.entity.NamedBaseEntity;
import com.caskey.apibuilder.requestBody.NamedBaseEntityDTO;

public abstract class NamedBaseEntityAdapter<T extends NamedBaseEntity, D extends NamedBaseEntityDTO>
        extends BaseEntityAdapter<T, D> {

    @Override
    public void mapFromDtoToEntity(final D dto, final T entity) {
        mapFromDTOToEntityExceptName(dto, entity);
        entity.setName(dto.getName());
    }

    protected abstract void mapFromDTOToEntityExceptName(final D dto, final T entity);

    @Override
    public void mapFromEntityToDTO(final T entity, final D dto, final Long depth) {
        mapFromEntityToDTOExceptName(entity, dto, depth);
        dto.setName(entity.getName());
    }

    protected abstract void mapFromEntityToDTOExceptName(final T entity, final D dto, final Long depth);
}
