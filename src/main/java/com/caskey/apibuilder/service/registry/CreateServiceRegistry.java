package com.caskey.apibuilder.service.registry;

import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.requestBody.BaseEntityDTO;
import com.caskey.apibuilder.service.CreateService;

public class CreateServiceRegistry<T extends BaseEntity, D extends BaseEntityDTO>
        extends ServiceRegistry<T, D, CreateService<T, D>> {

    public CreateServiceRegistry(final CreateService<T, D>[] services) {
        super(services);
    }

    @Override
    protected String getServiceFriendlyName() {
        return "create";
    }
}
