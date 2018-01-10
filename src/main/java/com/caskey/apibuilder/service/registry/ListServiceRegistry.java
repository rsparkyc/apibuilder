package com.caskey.apibuilder.service.registry;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.service.ListService;

public class ListServiceRegistry<T extends BaseEntity> {
    private final Map<Type, ListService<T>> registrationMap = new HashMap<>();

    public ListServiceRegistry(final ListService<T>[] services) {
        for (ListService<T> service : services) {
            Type entityType = ((ParameterizedType) service.getClass().getGenericInterfaces()[0])
                    .getActualTypeArguments()[0];
            registrationMap.put(entityType, service);
        }
    }

    public ListService<T> getService(final Type entityType) {
        if (registrationMap.containsKey(entityType)) {
            return registrationMap.get(entityType);
        }
        return null;
    }

}
