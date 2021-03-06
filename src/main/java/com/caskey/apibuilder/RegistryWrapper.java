package com.caskey.apibuilder;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.caskey.apibuilder.adapter.registry.AdapterRegistry;
import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.exception.MissingEntityException;
import com.caskey.apibuilder.repository.registry.RepositoryRegistry;
import com.caskey.apibuilder.requestBody.BaseEntityDTO;
import com.caskey.apibuilder.service.CreateService;
import com.caskey.apibuilder.service.HookService;
import com.caskey.apibuilder.service.TransactionalityService;
import com.caskey.apibuilder.service.UpdateService;
import com.caskey.apibuilder.service.registry.CreateServiceRegistry;
import com.caskey.apibuilder.service.registry.GetServiceRegistry;
import com.caskey.apibuilder.service.registry.ListServiceRegistry;
import com.caskey.apibuilder.service.registry.UpdateServiceRegistry;

public class RegistryWrapper<T extends BaseEntity, D extends BaseEntityDTO> {
    private final static Logger logger = LoggerFactory.getLogger(RegistryWrapper.class);

    private AdapterRegistry adapterRegistry;
    private RepositoryRegistry repositoryRegistry;
    private CreateServiceRegistry<T, D> createServiceRegistry;
    private GetServiceRegistry<T, D> getServiceRegistry;
    private ListServiceRegistry<T, D> listServiceRegistry;
    private UpdateServiceRegistry<T, D> updateServiceRegistry;

    private TransactionalityService transactionalityService;
    private HookService hookService;

    @Autowired
    private void setTransactionalityService(final TransactionalityService transactionalityService) {
        this.transactionalityService = transactionalityService;
    }

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

    @SuppressWarnings("unchecked")
    public <E extends BaseEntity> E saveEntity(final E entity) {
        if (entity.getId() != null) {
            try {
                return (E) ((UpdateService) updateServiceRegistry.getService(Hibernate.getClass(entity)))
                        .update(entity);
            } catch (MissingEntityException ex) {
                logger.error("Expected to be able to update entity with id " + entity.getId(), ex);
            }
        }
        return (E) ((CreateService)
                createServiceRegistry.getService(Hibernate.getClass(entity))).create(entity);
    }

    public TransactionalityService getTransactionalityService() {
        return transactionalityService;
    }

    public HookService getHookService() {
        return hookService;
    }

    @Autowired
    public void setHookService(final HookService hookService) {
        this.hookService = hookService;
    }
}
