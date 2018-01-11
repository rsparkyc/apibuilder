package com.caskey.apibuilder.adapter.registry;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.caskey.apibuilder.adapter.BaseEntityAdapter;
import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.requestBody.BaseEntityDTO;

public class AdapterRegistry {

    private final Map<Type, BaseEntityAdapter<BaseEntity, BaseEntityDTO>> registrationMap = new HashMap<>();

    public AdapterRegistry(final BaseEntityAdapter<BaseEntity, BaseEntityDTO>[] adapters) {
        for (BaseEntityAdapter<BaseEntity, BaseEntityDTO> adapter : adapters) {
            Type entityType = ((ParameterizedType) adapter.getClass().getGenericSuperclass())
                    .getActualTypeArguments()[0];
            registrationMap.put(entityType, adapter);
        }
    }

    public <T extends BaseEntity, D extends BaseEntityDTO> BaseEntityAdapter<T, D> getAdapter(
            final Type entityType) {
        if (registrationMap.containsKey(entityType)) {
            //noinspection unchecked
            return (BaseEntityAdapter<T, D>) registrationMap.get(entityType);
        }
        return null;
    }

}
