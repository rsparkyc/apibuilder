package com.caskey.apibuilder.service;

import com.caskey.apibuilder.RegistryWrapper;
import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.requestBody.BaseEntityDTO;

public class CRUDService<T extends BaseEntity, D extends BaseEntityDTO> implements
        CreateService<T, D>, ListService<T, D>, UpdateService<T, D>, GetService<T, D> {

    private final RegistryWrapper<T, D> registryWrapper;

    public CRUDService(final RegistryWrapper<T, D> registryWrapper) {
        this.registryWrapper = registryWrapper;
    }

    @Override
    public RegistryWrapper<T, D> getRegistryWrapper() {
        return registryWrapper;
    }

    public T saveEntity(final T entity) {
        return getRegistryWrapper().saveEntity(entity);
    }

}
