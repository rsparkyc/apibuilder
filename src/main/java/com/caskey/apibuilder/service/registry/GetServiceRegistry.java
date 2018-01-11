package com.caskey.apibuilder.service.registry;

import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.requestBody.BaseEntityDTO;
import com.caskey.apibuilder.service.GetService;

public class GetServiceRegistry<T extends BaseEntity, D extends BaseEntityDTO>
        extends ServiceRegistry<T, D, GetService<T, D>> {

    public GetServiceRegistry(final GetService<T, D>[] services) {
        super(services);
    }

    @Override
    protected String getServiceFriendlyName() {
        return "get";
    }
}
