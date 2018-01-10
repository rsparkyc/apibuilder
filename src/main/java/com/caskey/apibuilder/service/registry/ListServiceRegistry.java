package com.caskey.apibuilder.service.registry;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.service.ListService;

public class ListServiceRegistry {
    private final Map<Type, ListService<BaseEntity>> registrationMap = new HashMap<>();

    public ListServiceRegistry(final ListService<BaseEntity>[] services) {
        for (ListService<BaseEntity> service : services) {
            Type entityType = ((ParameterizedType) service.getClass().getGenericInterfaces()[0])
                    .getActualTypeArguments()[0];
            registrationMap.put(entityType, service);
        }
    }

    public <T extends BaseEntity> ListService<T> getService(final Type entityType) {
        if (registrationMap.containsKey(entityType)) {
            //noinspection unchecked
            return (ListService<T>) registrationMap.get(entityType);
        }
        return null;
    }

}
