package com.caskey.apibuilder.repository.registry;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.repository.BaseEntityRepository;
import com.caskey.apibuilder.util.ReflectionUtil;

public class RepositoryRegistry {

    private static final Logger logger = LoggerFactory.getLogger(RepositoryRegistry.class);

    private final Map<Class<? extends BaseEntityRepository<?>>, Optional<BaseEntityRepository<?>>>
            repositoryMap;

    public RepositoryRegistry(final BaseEntityRepository<?>... repositories) {
        repositoryMap = new HashMap<>();
        for (BaseEntityRepository<?> r : repositories) {
            Class<?>[] interfaces = r.getClass().getInterfaces();
            for (Class<?> theInterface : interfaces) {
                if (BaseEntityRepository.class.isAssignableFrom(theInterface)) {
                    @SuppressWarnings("unchecked")
                    Class<? extends BaseEntityRepository<?>> repositoryClass =
                            (Class<? extends BaseEntityRepository<?>>) theInterface
                                    .asSubclass(BaseEntityRepository.class);
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

    public <T extends BaseEntity> BaseEntityRepository<T> getRepository(final Type entityType) {
        Optional<Class<? extends BaseEntityRepository<?>>> first = repositoryMap.keySet().stream()
                .filter(repoClass ->
                        ReflectionUtil.getEntityTypeFromClass(repoClass) == entityType).findFirst();
        if (first.isPresent()) {
            BaseEntityRepository<T> repository = (BaseEntityRepository<T>) getRepository(first.get());
            return repository;
        }
        return null;
    }

    public <R extends BaseEntityRepository> R getRepository(final Class<R> repositoryClass) {
        @SuppressWarnings({ "unchecked", "SuspiciousMethodCalls" })
        R ret = (R) repositoryMap.get(repositoryClass)
                .orElseThrow(() -> new NoSuchElementException(repositoryClass.getName()));
        return ret;
    }

    public <T extends BaseEntity> T save(final T entity) {
        return getRepository(entity.getClass()).save(entity);
    }

}
