package apibuilder.service.factory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import apibuilder.entity.BaseEntity;
import apibuilder.service.IListService;

public class ListServiceFactory {

    private static final Map<Type, IListService<? extends BaseEntity>>
            registrationMap = new HashMap<>();

    public static <T extends BaseEntity> IListService<T> getService(final Type entityType) {
        if (registrationMap.containsKey(entityType)) {
            //noinspection unchecked
            return (IListService<T>) registrationMap.get(entityType);
        }
        return null;

    }

    public static <T extends BaseEntity> void registerServices(final IListService<T>[] services) {
        for (IListService listService : services) {
            Type entityType = ((ParameterizedType) listService.getClass().getGenericInterfaces()[0])
                    .getActualTypeArguments()[0];
            registrationMap.put(entityType, listService);
        }
    }

}
