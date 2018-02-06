package com.caskey.apibuilder.controller;

import com.caskey.apibuilder.RegistryWrapper;
import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.requestBody.BaseEntityDTO;
import com.caskey.apibuilder.service.CRUDService;

public abstract class CRUDController<T extends BaseEntity, D extends BaseEntityDTO> implements
        CreateController<T, D>, GetController<T, D>, UpdateController<T, D>, ListController<T, D> {

    private final RegistryWrapper<T, D> registryWrapper;

    public CRUDController(final RegistryWrapper<T, D> registryWrapper) {
        this.registryWrapper = registryWrapper;
    }

    public <S extends CRUDService<T, D>> S getService() {
        //noinspection unchecked
        return (S) getRegistryWrapper().getGetServiceRegistry().getService(getEntityType());

    }

    @Override
    public RegistryWrapper<T, D> getRegistryWrapper() {
        return registryWrapper;
    }
}
