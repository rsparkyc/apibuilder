package com.caskey.apibuilder.service.registry;

import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.requestBody.BaseEntityDTO;
import com.caskey.apibuilder.service.UpdateService;

public class UpdateServiceRegistry<T extends BaseEntity, D extends BaseEntityDTO>
        extends ServiceRegistry<T, D, UpdateService<T, D>> {
    public UpdateServiceRegistry(final UpdateService<T, D>[] services) {
        super(services);
    }

    @Override
    protected String getServiceFriendlyName() {
        return "update";
    }
}
