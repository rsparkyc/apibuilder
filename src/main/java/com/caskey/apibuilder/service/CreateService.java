package com.caskey.apibuilder.service;

import com.caskey.apibuilder.adapter.BaseEntityAdapter;
import com.caskey.apibuilder.adapter.registry.AdapterRegistry;
import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.requestBody.BaseEntityDTO;

public interface CreateService<T extends BaseEntity, D extends BaseEntityDTO> extends BaseService<T> {

    AdapterRegistry getAdapterRegistry();

    default T create(final D entityDTO) {
        BaseEntityAdapter<T, D> adapter = getAdapterRegistry().getAdapter(getEntityType());
        if (adapter != null) {
            T entity = adapter.toEntity(entityDTO);
            return getRepository().save(entity);
        }

        //WARN
        return null;
    }
}
