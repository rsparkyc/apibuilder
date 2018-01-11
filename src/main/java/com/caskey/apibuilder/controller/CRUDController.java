package com.caskey.apibuilder.controller;

import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.requestBody.BaseEntityDTO;
import com.caskey.apibuilder.service.registry.CreateServiceRegistry;
import com.caskey.apibuilder.service.registry.GetServiceRegistry;
import com.caskey.apibuilder.service.registry.ListServiceRegistry;
import com.caskey.apibuilder.service.registry.UpdateServiceRegistry;

public abstract class CRUDController<T extends BaseEntity, D extends BaseEntityDTO> implements
        CreateController<T, D>, GetController<T, D>, UpdateController<T, D>, ListController<T, D> {

    private final ListServiceRegistry listServiceRegistry;
    private final GetServiceRegistry getServiceRegistry;
    private final UpdateServiceRegistry updateServiceRegistry;
    private final CreateServiceRegistry createServiceRegistry;

    public CRUDController(
            final ListServiceRegistry listServiceRegistry,
            final GetServiceRegistry getServiceRegistry,
            final UpdateServiceRegistry updateServiceRegistry,
            final CreateServiceRegistry createServiceRegistry) {
        this.listServiceRegistry = listServiceRegistry;
        this.getServiceRegistry = getServiceRegistry;
        this.updateServiceRegistry = updateServiceRegistry;
        this.createServiceRegistry = createServiceRegistry;
    }

    @Override
    public ListServiceRegistry getListServiceRegistry() {
        return listServiceRegistry;
    }

    @Override
    public GetServiceRegistry getGetServiceRegistry() {
        return getServiceRegistry;
    }

    @Override
    public UpdateServiceRegistry getUpdateServiceRegistry() {
        return updateServiceRegistry;
    }

    @Override
    public CreateServiceRegistry getCreateServiceRegistry() {
        return createServiceRegistry;
    }
}
