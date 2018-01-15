package com.caskey.apibuilder.adapter.registry;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caskey.apibuilder.adapter.BaseEntityAdapter;
import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.exception.MissingAdapterException;
import com.caskey.apibuilder.requestBody.BaseEntityDTO;
import com.caskey.apibuilder.util.ReflectionUtil;

public class AdapterRegistry {

    private static final Logger logger = LoggerFactory.getLogger(AdapterRegistry.class);

    private final Map<Type, BaseEntityAdapter<BaseEntity, BaseEntityDTO>> registrationMap = new HashMap<>();

    public AdapterRegistry(final BaseEntityAdapter<BaseEntity, BaseEntityDTO>[] adapters) {
        for (BaseEntityAdapter<BaseEntity, BaseEntityDTO> adapter : adapters) {
            Type entityType = ReflectionUtil.getEntityTypeFromClass(adapter.getClass());
            Type dtoType = ReflectionUtil.getDTOTypeFromClass(adapter.getClass());
            registrationMap.put(entityType, adapter);
            registrationMap.put(dtoType, adapter);
        }
    }

    public <T extends BaseEntity, D extends BaseEntityDTO> BaseEntityAdapter<T, D> getAdapter(
            final Type entityType) {
        if (registrationMap.containsKey(entityType)) {
            //noinspection unchecked
            return (BaseEntityAdapter<T, D>) registrationMap.get(entityType);
        }
        String message = "The entity adapter for " + entityType.getTypeName() + " was missing.";
        logger.warn(message);

        throw new MissingAdapterException(message);

    }

}
