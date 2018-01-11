package com.caskey.apibuilder.service.registry;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.exception.MissingServiceException;
import com.caskey.apibuilder.requestBody.BaseEntityDTO;
import com.caskey.apibuilder.service.ListService;

public class ListServiceRegistry {
    private final Map<Type, ListService<BaseEntity, BaseEntityDTO>> registrationMap = new HashMap<>();

    public ListServiceRegistry(final ListService<BaseEntity, BaseEntityDTO>[] services) {
        for (ListService<BaseEntity, BaseEntityDTO> service : services) {
            Type entityType = ((ParameterizedType) service.getClass().getGenericInterfaces()[0])
                    .getActualTypeArguments()[0];
            registrationMap.put(entityType, service);
        }
    }

    public <T extends BaseEntity, D extends BaseEntityDTO> ListService<T, D> getService(
            final Type entityType) {
        if (registrationMap.containsKey(entityType)) {
            //noinspection unchecked
            return (ListService<T, D>) registrationMap.get(entityType);
        }
        throw new MissingServiceException("The List service for " + entityType + " was missing.");
    }

}
