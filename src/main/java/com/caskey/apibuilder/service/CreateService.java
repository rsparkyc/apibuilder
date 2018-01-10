package com.caskey.apibuilder.service;

import com.caskey.apibuilder.adapter.BaseEntityAdapter;
import com.caskey.apibuilder.adapter.registry.AdapterRegistry;
import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.requestBody.CreateRequestBody;

public interface CreateService<T extends BaseEntity, B extends CreateRequestBody> extends BaseService<T> {

    AdapterRegistry<T, B> getAdapterRegistry();

    default T create(final B createRequestBody) {
        BaseEntityAdapter<T, B> adapter = getAdapterRegistry().getAdapter(getEntityType());
        if (adapter != null) {
            T entity = adapter.toEntity(createRequestBody);
            return getRepository().save(entity);
        }

        //WARN
        return null;
    }
}
