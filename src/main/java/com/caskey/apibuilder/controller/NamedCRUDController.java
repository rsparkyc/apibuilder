package com.caskey.apibuilder.controller;

import com.caskey.apibuilder.entity.NamedBaseEntity;
import com.caskey.apibuilder.requestBody.NamedBaseEntityDTO;
import com.caskey.apibuilder.service.registry.CreateServiceRegistry;
import com.caskey.apibuilder.service.registry.GetServiceRegistry;
import com.caskey.apibuilder.service.registry.ListServiceRegistry;
import com.caskey.apibuilder.service.registry.UpdateServiceRegistry;

public abstract class NamedCRUDController<T extends NamedBaseEntity, D extends NamedBaseEntityDTO>
        extends CRUDController<T, D> {
    public NamedCRUDController(
            final ListServiceRegistry listServiceRegistry,
            final GetServiceRegistry getServiceRegistry,
            final UpdateServiceRegistry updateServiceRegistry,
            final CreateServiceRegistry createServiceRegistry) {
        super(listServiceRegistry, getServiceRegistry, updateServiceRegistry, createServiceRegistry);
    }
}
