package com.caskey.apibuilder.service.registry;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.requestBody.EntityDTO;
import com.caskey.apibuilder.service.CreateService;

public class CreateServiceRegistry {
    private final Map<Type, CreateService<BaseEntity, EntityDTO>> registrationMap = new HashMap<>();

    public CreateServiceRegistry(final CreateService<BaseEntity, EntityDTO>[] services) {
        for (CreateService<BaseEntity, EntityDTO> service : services) {
            Type entityType = ((ParameterizedType) service.getClass().getGenericInterfaces()[0])
                    .getActualTypeArguments()[0];
            registrationMap.put(entityType, service);
        }
    }

    public <T extends BaseEntity, D extends EntityDTO> CreateService<T, D> getService(final Type entityType) {
        if (registrationMap.containsKey(entityType)) {
            //noinspection unchecked
            return (CreateService<T, D>) registrationMap.get(entityType);
        }
        return null;
    }

}
