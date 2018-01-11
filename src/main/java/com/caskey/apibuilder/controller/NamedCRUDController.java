package com.caskey.apibuilder.controller;

import com.caskey.apibuilder.RegistryWrapper;
import com.caskey.apibuilder.entity.NamedBaseEntity;
import com.caskey.apibuilder.requestBody.NamedBaseEntityDTO;

public abstract class NamedCRUDController<T extends NamedBaseEntity, D extends NamedBaseEntityDTO>
        extends CRUDController<T, D> {
    public NamedCRUDController(final RegistryWrapper<T, D> registryWrapper) {
        super(registryWrapper);
    }
}
