package com.caskey.apibuilder.service.registry;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.requestBody.BaseEntityDTO;
import com.caskey.apibuilder.service.CreateService;

public class CreateServiceRegistry {
    private final Map<Type, CreateService<BaseEntity, BaseEntityDTO>> registrationMap = new HashMap<>();

    public CreateServiceRegistry(final CreateService<BaseEntity, BaseEntityDTO>[] services) {
        for (CreateService<BaseEntity, BaseEntityDTO> service : services) {
            Type entityType = ((ParameterizedType) service.getClass().getGenericInterfaces()[0])
                    .getActualTypeArguments()[0];
            registrationMap.put(entityType, service);
        }
    }

    public <T extends BaseEntity, D extends BaseEntityDTO> CreateService<T, D> getService(
            final Type
                    entityType) {
        if (registrationMap.containsKey(entityType)) {
            //noinspection unchecked
            return (CreateService<T, D>) registrationMap.get(entityType);
        }
        return null;
    }

}
