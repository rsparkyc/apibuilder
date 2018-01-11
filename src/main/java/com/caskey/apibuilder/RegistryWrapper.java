package com.caskey.apibuilder;

import org.springframework.beans.factory.annotation.Autowired;

import com.caskey.apibuilder.adapter.registry.AdapterRegistry;
import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.repository.registry.RepositoryRegistry;
import com.caskey.apibuilder.requestBody.BaseEntityDTO;
import com.caskey.apibuilder.service.registry.CreateServiceRegistry;
import com.caskey.apibuilder.service.registry.GetServiceRegistry;
import com.caskey.apibuilder.service.registry.ListServiceRegistry;
import com.caskey.apibuilder.service.registry.UpdateServiceRegistry;

public class RegistryWrapper<T extends BaseEntity, D extends BaseEntityDTO> {
    private AdapterRegistry adapterRegistry;
    private RepositoryRegistry repositoryRegistry;
    private CreateServiceRegistry<T, D> createServiceRegistry;
    private GetServiceRegistry<T, D> getServiceRegistry;
    private ListServiceRegistry<T, D> listServiceRegistry;
    private UpdateServiceRegistry<T, D> updateServiceRegistry;

    @Autowired
    public void setAdapterRegistry(final AdapterRegistry adapterRegistry) {
        this.adapterRegistry = adapterRegistry;
    }

    @Autowired
    public void setRepositoryRegistry(final RepositoryRegistry repositoryRegistry) {
        this.repositoryRegistry = repositoryRegistry;
    }

    @Autowired
    public void setCreateServiceRegistry(final CreateServiceRegistry<T, D> createServiceRegistry) {
        this.createServiceRegistry = createServiceRegistry;
    }

    @Autowired
    public void setGetServiceRegistry(final GetServiceRegistry<T, D> getServiceRegistry) {
        this.getServiceRegistry = getServiceRegistry;
    }

    @Autowired
    public void setListServiceRegistry(final ListServiceRegistry<T, D> listServiceRegistry) {
        this.listServiceRegistry = listServiceRegistry;
    }

    @Autowired
    public void setUpdateServiceRegistry(final UpdateServiceRegistry<T, D> updateServiceRegistry) {
        this.updateServiceRegistry = updateServiceRegistry;
    }

    public AdapterRegistry getAdapterRegistry() {
        return adapterRegistry;
    }

    public RepositoryRegistry getRepositoryRegistry() {
        return repositoryRegistry;
    }

    public CreateServiceRegistry<T, D> getCreateServiceRegistry() {
        return createServiceRegistry;
    }

    public GetServiceRegistry<T, D> getGetServiceRegistry() {
        return getServiceRegistry;
    }

    public ListServiceRegistry<T, D> getListServiceRegistry() {
        return listServiceRegistry;
    }

    public UpdateServiceRegistry<T, D> getUpdateServiceRegistry() {
        return updateServiceRegistry;
    }
}
