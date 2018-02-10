package com.caskey.apibuilder.service;

import java.lang.reflect.Type;

import com.caskey.apibuilder.RegistryWrapper;
import com.caskey.apibuilder.adapter.BaseEntityAdapter;
import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.exception.MissingRepositoryException;
import com.caskey.apibuilder.repository.BaseEntityRepository;
import com.caskey.apibuilder.requestBody.BaseEntityDTO;
import com.caskey.apibuilder.util.ReflectionUtil;

public interface BaseService<T extends BaseEntity, D extends BaseEntityDTO> {

    RegistryWrapper<T, D> getRegistryWrapper();

    default BaseEntityAdapter<T, D> getAdapter() {
        return getRegistryWrapper().getAdapterRegistry().getAdapter(getEntityType());
    }

    default BaseEntityRepository<T> getRepository() {
        BaseEntityRepository<T> repository =
                getRegistryWrapper().getRepositoryRegistry().getRepository(getEntityType());

        if (repository == null) {
            throw new MissingRepositoryException(
                    "Could not find correct entity repository for " + getEntityType().getTypeName());
        }

        return repository;
    }

    default Type getEntityType() {
        return ReflectionUtil.getEntityTypeFromClass(getClass());
    }

}
