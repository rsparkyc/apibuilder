package com.caskey.apibuilder.repository;

import java.util.Iterator;

import org.springframework.data.repository.NoRepositoryBean;

import com.caskey.apibuilder.entity.NamedBaseEntity;

@NoRepositoryBean
public interface NamedSearchingRepository<T extends NamedBaseEntity> extends BaseEntityRepository<T> {
    T findByName(final String name);

    Iterable<T> findByNameContaining(final String name);

    default T findFirstByNameContaining(final String name) {
        Iterator<T> iterator = findByNameContaining(name).iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }
}
