package apibuilder.repository.factory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import apibuilder.entity.BaseEntity;

import org.springframework.data.repository.CrudRepository;

public class RepositoryFactory {
    private static final Map<Type, CrudRepository<? extends BaseEntity, Long>>
            registrationMap = new HashMap<>();

    public static <T extends BaseEntity> CrudRepository<T, Long> getRepository(final Type entityType) {
        if (registrationMap.containsKey(entityType)) {
            //noinspection unchecked
            return (CrudRepository<T, Long>) registrationMap.get(entityType);
        }
        return null;

    }

    public static <T extends BaseEntity> void registerRepositories(final CrudRepository<T, Long>[] repos) {
        for (CrudRepository<T, Long> repo : repos) {
            Type entityType = ((ParameterizedType) ((Class) repo.getClass().getGenericInterfaces()[0])
                    .getGenericInterfaces()[0]).getActualTypeArguments()[0];
            registrationMap.put(entityType, repo);
        }
    }
}
