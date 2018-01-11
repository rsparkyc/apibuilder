package com.caskey.apibuilder.service.registry;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.exception.MissingServiceException;
import com.caskey.apibuilder.requestBody.BaseEntityDTO;
import com.caskey.apibuilder.service.BaseService;

public abstract class ServiceRegistry<T extends BaseEntity, D extends BaseEntityDTO,
        S extends BaseService<T, D>> {
    private final Map<Type, S> registrationMap = new HashMap<>();

    public ServiceRegistry(final S[] services) {
        for (S service : services) {
            Type genericSuperclass = service.getClass().getGenericSuperclass();
            ParameterizedType pType;
            if (genericSuperclass instanceof ParameterizedType) {
                // this class extends another class which is parameterized,
                // so we'll assume we can use its types to pull out the entity class
                pType = (ParameterizedType) genericSuperclass;
            } else {
                pType = (ParameterizedType) service.getClass().getGenericInterfaces()[0];
            }

            Type entityType = pType.getActualTypeArguments()[0];
            registrationMap.put(entityType, service);
        }
    }

    public S getService(final Type entityType) {
        if (registrationMap.containsKey(entityType)) {
            //noinspection unchecked
            return registrationMap.get(entityType);
        }
        throw new MissingServiceException(
                "The " + getServiceFriendlyName() + " service for " + entityType + " was missing.");
    }

    protected abstract String getServiceFriendlyName();
}
