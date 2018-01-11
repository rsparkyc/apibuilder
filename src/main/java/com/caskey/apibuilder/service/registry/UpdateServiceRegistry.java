package com.caskey.apibuilder.service.registry;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.exception.MissingServiceException;
import com.caskey.apibuilder.requestBody.BaseEntityDTO;
import com.caskey.apibuilder.service.UpdateService;

public class UpdateServiceRegistry {
    private final Map<Type, UpdateService<BaseEntity, BaseEntityDTO>> registrationMap = new HashMap<>();

    public UpdateServiceRegistry(final UpdateService<BaseEntity, BaseEntityDTO>[] services) {
        for (UpdateService<BaseEntity, BaseEntityDTO> service : services) {
            Type entityType = ((ParameterizedType) service.getClass().getGenericInterfaces()[0])
                    .getActualTypeArguments()[0];
            registrationMap.put(entityType, service);
        }
    }

    public <T extends BaseEntity, D extends BaseEntityDTO> UpdateService<T, D> getService(
            final Type entityType) {
        if (registrationMap.containsKey(entityType)) {
            //noinspection unchecked
            return (UpdateService<T, D>) registrationMap.get(entityType);
        }
        throw new MissingServiceException("The Update Service for " + entityType + " was missing.");
    }
}
