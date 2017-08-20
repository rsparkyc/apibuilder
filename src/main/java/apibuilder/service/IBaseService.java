package apibuilder.service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import apibuilder.entity.BaseEntity;
import apibuilder.repository.factory.RepositoryFactory;

import org.springframework.data.repository.CrudRepository;

interface IBaseService<T extends BaseEntity> {

    // IDEA: have a static factory that has singleton ServiceFactories injected into a
    // Map<ServiceType, ServiceFactory>.  Perhaps we can get the proper service factory from that by
    // passing in the serviceType?


    default CrudRepository<T, Long> getRepository() {
        Type entityType = ((ParameterizedType) getClass()
                .getGenericInterfaces()[0]).getActualTypeArguments()[0];

        CrudRepository<T, Long> repository = RepositoryFactory.getRepository(entityType);

        if (repository == null) {
            throw new RuntimeException("Could not find correct entity repository");
        }

        return repository;

    }
}
