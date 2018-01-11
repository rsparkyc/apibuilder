package com.caskey.apibuilder.adapter;

import java.util.ArrayList;
import java.util.List;

import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.requestBody.BaseEntityDTO;

public abstract class BaseEntityAdapter<T extends BaseEntity, D extends BaseEntityDTO> {

    protected abstract T createNewEntity();

    protected abstract D createNewDTO();

    public final T toEntity(final D entityDTO) {
        T entity = createNewEntity();
        entity.setId(entityDTO.getId());
        entity.setCreatedDate(entityDTO.getCreatedDate());
        entity.setModifiedDate(entityDTO.getModifiedDate());
        mapFromDtoToEntity(entityDTO, entity);
        return entity;
    }

    public final D toDTO(final T entity) {
        D dto = createNewDTO();
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setModifiedDate(entity.getModifiedDate());
        mapFromEntityToDTO(entity, dto);
        return dto;
    }

    protected abstract void mapFromDtoToEntity(final D entityDTO, final T entity);

    protected abstract void mapFromEntityToDTO(final T entity, final D dto);

    public final List<D> toDTOs(final Iterable<T> entities) {
        List<D> result = new ArrayList<>();
        entities.forEach(e -> result.add(toDTO(e)));
        return result;
    }
}
