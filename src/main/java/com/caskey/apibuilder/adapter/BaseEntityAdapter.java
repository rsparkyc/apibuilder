package com.caskey.apibuilder.adapter;

import java.util.ArrayList;
import java.util.List;

import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.requestBody.EntityDTO;

public abstract class BaseEntityAdapter<T extends BaseEntity, D extends EntityDTO> {

    public abstract T toEntity(final D entityDTO);

    public abstract D toDTO(final T entity);

    public List<D> toDTOs(final Iterable<T> entities) {
        List<D> result = new ArrayList<>();
        entities.forEach(e -> result.add(toDTO(e)));
        return result;
    }
}
