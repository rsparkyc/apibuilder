package com.caskey.apibuilder.service.registry;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.service.GetService;

public class GetServiceRegistry {
    private final Map<Type, GetService<BaseEntity>> registrationMap = new HashMap<>();

    public GetServiceRegistry(final GetService<BaseEntity>[] services) {
        for (GetService<BaseEntity> service : services) {
            Type entityType = ((ParameterizedType) service.getClass().getGenericInterfaces()[0])
                    .getActualTypeArguments()[0];
            registrationMap.put(entityType, service);
        }
    }

    public <T extends BaseEntity> GetService<T> getService(final Type entityType) {
        if (registrationMap.containsKey(entityType)) {
            return (GetService<T>) registrationMap.get(entityType);
        }
        return null;
    }

}
