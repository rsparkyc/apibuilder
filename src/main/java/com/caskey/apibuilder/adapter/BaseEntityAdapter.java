package com.caskey.apibuilder.adapter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.caskey.apibuilder.RegistryWrapper;
import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.requestBody.BaseEntityDTO;
import com.caskey.apibuilder.util.ReflectionUtil;

public abstract class BaseEntityAdapter<T extends BaseEntity, D extends BaseEntityDTO> {

    private static final Logger logger = LoggerFactory.getLogger(BaseEntityAdapter.class);

    private RegistryWrapper registryWrapper;

    @Autowired
    public void setRegistryWrapper(final RegistryWrapper registryWrapper) {
        this.registryWrapper = registryWrapper;
    }

    protected RegistryWrapper getRegistryWrapper() {
        return registryWrapper;
    }

    protected T createNewEntity() {
        return ReflectionUtil.getTypeArgumentInstance(this.getClass(), 0);
    }

    protected D createNewDTO() {
        return ReflectionUtil.getTypeArgumentInstance(this.getClass(), 1);
    }

    public final T toEntity(final D entityDTO) {
        if (entityDTO == null) {
            return null;
        }
        T entity = createNewEntity();
        entity.setId(entityDTO.getId());
        // I don't want the ability to change fields like dates or entityType just because the DTO said so
        mapFromDtoToEntity(entityDTO, entity);
        return entity;
    }

    public final D toDTO(final T entity) {
        return toDTO(entity, 0L);
    }

    public final D toDTO(final T entity, final Long depth) {
        if (entity == null) {
            return null;
        }
        D dto = createNewDTO();
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setModifiedDate(entity.getModifiedDate());
        dto.setEntityType(entity.getEntityType());
        mapFromEntityToDTO(entity, dto, depth);
        return dto;
    }

    public abstract void mapFromDtoToEntity(final D entityDTO, final T entity);

    public abstract void mapFromEntityToDTO(final T entity, final D dto, final Long depth);

    public final List<D> toDTOs(final Iterable<T> entities) {
        return toDTOs(entities, 0L);
    }

    public final List<D> toDTOs(final Iterable<T> entities, final Long depth) {
        List<D> result = new ArrayList<>();
        entities.forEach(e -> result.add(toDTO(e, depth)));
        return result;
    }

    protected long getNextDepth(final Long depth) {
        if (depth != null && depth > 0) {
            return depth - 1;
        }
        return 0L;
    }

    protected boolean shouldProcessDeeper(final Long depth) {
        return (depth != null && depth > 0);
    }
}
