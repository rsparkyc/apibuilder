package com.caskey.apibuilder.service.registry;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.exception.MissingServiceException;
import com.caskey.apibuilder.requestBody.BaseEntityDTO;
import com.caskey.apibuilder.service.BaseService;
import com.caskey.apibuilder.util.ReflectionUtil;

public abstract class ServiceRegistry<T extends BaseEntity, D extends BaseEntityDTO,
        S extends BaseService<T, D>> {
    private final Map<Type, Optional<S>> registrationMap = new HashMap<>();

    public ServiceRegistry(final S[] services) {
        for (S service : services) {
            Type entityType = ReflectionUtil.getEntityTypeFromClass(service.getClass());
            registrationMap.put(entityType, Optional.of(service));
        }
    }

    public S getService(final Type entityType) {
        MissingServiceException ex = new MissingServiceException(
                "The " + getServiceFriendlyName() + " service for " + entityType
                        + " was missing.");

        if (registrationMap.containsKey(entityType)) {
            //noinspection unchecked
            return registrationMap.get(entityType).orElseThrow(() -> ex);
        }
        throw ex;
    }

    protected abstract String getServiceFriendlyName();
}
