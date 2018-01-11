package com.caskey.apibuilder.adapter;

import java.util.ArrayList;
import java.util.List;

import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.requestBody.BaseEntityDTO;

public abstract class BaseEntityAdapter<T extends BaseEntity, D extends BaseEntityDTO> {

    protected abstract T createNewEntity();

    protected abstract D createNewDTO();

    public final T toEntity(final D entityDTO) {
        if (entityDTO == null) {
            return null;
        }
        T entity = createNewEntity();
        entity.setId(entityDTO.getId());
        // I don't want the ability to change these fields just because the DTO said so
        // entity.setCreatedDate(entityDTO.getCreatedDate());
        // entity.setModifiedDate(entityDTO.getModifiedDate());
        mapFromDtoToEntity(entityDTO, entity);
        return entity;
    }

    public final D toDTO(final T entity) {
        if (entity == null) {
            return null;
        }
        D dto = createNewDTO();
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setModifiedDate(entity.getModifiedDate());
        mapFromEntityToDTO(entity, dto);
        return dto;
    }

    public abstract void mapFromDtoToEntity(final D entityDTO, final T entity);

    public abstract void mapFromEntityToDTO(final T entity, final D dto);

    public final List<D> toDTOs(final Iterable<T> entities) {
        List<D> result = new ArrayList<>();
        entities.forEach(e -> result.add(toDTO(e)));
        return result;
    }
}
