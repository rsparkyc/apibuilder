package com.caskey.apibuilder.repository.registry;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.CrudRepository;

import com.caskey.apibuilder.entity.BaseEntity;

public class RepositoryRegistry {

    private static final Logger logger = LoggerFactory.getLogger(RepositoryRegistry.class);

    private final Map<Class<? extends CrudRepository<?, ?>>, Optional<CrudRepository<?, ?>>> repositoryMap;
    //private final Map<Type, Optional<CrudRepository<?, ?>>> repositoryByEntityTypeMap;

    public RepositoryRegistry(final CrudRepository<?, ?>... repositories) {
        repositoryMap = new HashMap<>();
        //repositoryByEntityTypeMap = new HashMap<>();
        for (CrudRepository<?, ?> r : repositories) {
            Class<?>[] interfaces = r.getClass().getInterfaces();
            for (Class<?> theInterface : interfaces) {
                if (CrudRepository.class.isAssignableFrom(theInterface)) {
                    @SuppressWarnings("unchecked")
                    Class<? extends CrudRepository<?, ?>> repositoryClass =
                            (Class<? extends CrudRepository<?, ?>>) theInterface
                                    .asSubclass(CrudRepository.class);
                    // We update an empty value in the map if there is more than
                    // one Repository subclass that corresponds to a given interface.
                    if (repositoryMap.containsKey(theInterface)) {
                        repositoryMap.put(repositoryClass, Optional.empty());
                    } else {
                        repositoryMap.put(repositoryClass, Optional.of(r));
                    }
                }
            }
        }
    }

    public <T extends BaseEntity> CrudRepository<T, Long> getRepository(final Type entityType) {
        Optional<Class<? extends CrudRepository<?, ?>>> first = repositoryMap.keySet().stream()
                .filter(repoClass -> (((ParameterizedType) repoClass.getGenericInterfaces()[0])
                        .getActualTypeArguments()[0] == entityType)).findFirst();
        if (first.isPresent()) {
            CrudRepository<T, Long> repository = (CrudRepository<T, Long>) getRepository(first.get());
            return repository;
        }
        return null;
    }

    public <R extends CrudRepository> R getRepository(final Class<R> repositoryClass) {
        @SuppressWarnings({ "unchecked", "SuspiciousMethodCalls" })
        R ret = (R) repositoryMap.get(repositoryClass)
                .orElseThrow(() -> new NoSuchElementException(repositoryClass.getName()));
        return ret;
    }

    public <T extends BaseEntity> T save(final T entity) {
        Optional<Class<? extends CrudRepository<?, ?>>> first = repositoryMap.keySet().stream()
                .filter(repoClass -> (((ParameterizedType) repoClass.getGenericInterfaces()[0])
                        .getActualTypeArguments()[0] == entity.getClass())).findFirst();
        if (first.isPresent()) {
            CrudRepository<T, ?> repository = (CrudRepository<T, ?>) getRepository(first.get());
            return repository.save(entity);
        }
        NoSuchElementException ex =
                new NoSuchElementException("Could not find a matching repository to save the entity with.");

        logger.error("Error saving entity", ex);
        throw ex;
    }

}
