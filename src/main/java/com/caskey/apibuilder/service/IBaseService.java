package com.caskey.apibuilder.service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.springframework.data.repository.CrudRepository;

import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.repository.registry.RepositoryRegistry;

interface IBaseService<T extends BaseEntity> {

    RepositoryRegistry getRepositoryRegistry();

    default CrudRepository<T, Long> getRepository() {
        CrudRepository<T, Long> repository = getRepositoryRegistry().getRepository(getEntityType());

        if (repository == null) {
            throw new RuntimeException("Could not find correct entity repository");
        }

        return repository;
    }

    default Type getEntityType() {
        Type entityType = ((ParameterizedType) getClass()
                .getGenericInterfaces()[0]).getActualTypeArguments()[0];
        return entityType;
    }

}
