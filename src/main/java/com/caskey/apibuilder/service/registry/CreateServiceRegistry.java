package com.caskey.apibuilder.service.registry;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.requestBody.CreateRequestBody;
import com.caskey.apibuilder.service.ICreateService;

public class CreateServiceRegistry<T extends BaseEntity, B extends CreateRequestBody> {
    private final Map<Type, ICreateService<T, B>> registrationMap = new HashMap<>();

    public CreateServiceRegistry(final ICreateService<T, B>[] services) {
        for (ICreateService<T, B> service : services) {
            Type entityType = ((ParameterizedType) service.getClass().getGenericInterfaces()[0])
                    .getActualTypeArguments()[0];
            registrationMap.put(entityType, service);
        }
    }

    public ICreateService<T, B> getService(final Type entityType) {
        if (registrationMap.containsKey(entityType)) {
            return registrationMap.get(entityType);
        }
        return null;
    }

}
