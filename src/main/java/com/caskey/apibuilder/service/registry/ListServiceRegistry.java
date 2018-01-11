package com.caskey.apibuilder.service.registry;

import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.requestBody.BaseEntityDTO;
import com.caskey.apibuilder.service.ListService;

public class ListServiceRegistry<T extends BaseEntity, D extends BaseEntityDTO>
        extends ServiceRegistry<T, D, ListService<T, D>> {
    public ListServiceRegistry(final ListService<T, D>[] services) {
        super(services);
    }

    @Override
    protected String getServiceFriendlyName() {
        return "list";
    }
}
