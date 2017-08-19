package apibuilder.service.factory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import apibuilder.entity.BaseEntity;
import apibuilder.service.IGetService;

public class GetServiceFactory {
    private static final Map<Type, IGetService<? extends BaseEntity>>
            registrationMap = new HashMap<>();

    public static <T extends BaseEntity> IGetService<T> getService(final Type entityType) {
        if (registrationMap.containsKey(entityType)) {
            //noinspection unchecked
            return (IGetService<T>) registrationMap.get(entityType);
        }
        return null;

    }

    public static <T extends BaseEntity> void registerServices(final IGetService<T>[] services) {
        for (IGetService getService : services) {
            Type entityType = ((ParameterizedType) getService.getClass().getGenericInterfaces()[0])
                    .getActualTypeArguments()[0];
            registrationMap.put(entityType, getService);
        }
    }
}
