package com.caskey.apibuilder.adapter.registry;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.caskey.apibuilder.adapter.BaseEntityAdapter;
import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.requestBody.CreateRequestBody;

public class AdapterRegistry<T extends BaseEntity, B extends CreateRequestBody> {

    private final Map<Type, BaseEntityAdapter<T, B>> registrationMap = new HashMap<>();

    public AdapterRegistry(final BaseEntityAdapter<T, B>[] adapters) {
        for (BaseEntityAdapter<T, B> adapter : adapters) {
            Type entityType = ((ParameterizedType) adapter.getClass().getGenericSuperclass())
                    .getActualTypeArguments()[0];
            registrationMap.put(entityType, adapter);
        }
    }

    public BaseEntityAdapter<T, B> getAdapter(final Type entityType) {
        if (registrationMap.containsKey(entityType)) {
            return registrationMap.get(entityType);
        }
        return null;
    }

}
