package com.caskey.apibuilder.service;

import com.caskey.apibuilder.adapter.registry.AdapterRegistry;
import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.repository.registry.RepositoryRegistry;
import com.caskey.apibuilder.requestBody.BaseEntityDTO;

public class CRUDService<T extends BaseEntity, D extends BaseEntityDTO> implements
        CreateService<T, D>, ListService<T, D>, UpdateService<T, D>, GetService<T, D> {

    private final RepositoryRegistry repositoryRegistry;
    private final AdapterRegistry adapterRegistry;

    public CRUDService(
            final RepositoryRegistry repositoryRegistry,
            final AdapterRegistry adapterRegistry) {
        this.repositoryRegistry = repositoryRegistry;
        this.adapterRegistry = adapterRegistry;
    }

    @Override
    public RepositoryRegistry getRepositoryRegistry() {
        return repositoryRegistry;
    }

    @Override
    public AdapterRegistry getAdapterRegistry() {
        return adapterRegistry;
    }
}
