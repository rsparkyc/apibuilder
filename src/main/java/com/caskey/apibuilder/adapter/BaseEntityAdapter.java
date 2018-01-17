package com.caskey.apibuilder.adapter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.caskey.apibuilder.RegistryWrapper;
import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.exception.MissingAdapterException;
import com.caskey.apibuilder.requestBody.BaseEntityDTO;
import com.caskey.apibuilder.util.ReflectionUtil;
import com.caskey.apibuilder.util.collector.CustomCollectors;

public abstract class BaseEntityAdapter<T extends BaseEntity, D extends BaseEntityDTO> {

    private static final Logger logger = LoggerFactory.getLogger(BaseEntityAdapter.class);

    private RegistryWrapper registryWrapper;

    @Autowired
    public void setRegistryWrapper(final RegistryWrapper registryWrapper) {
        this.registryWrapper = registryWrapper;
    }

    protected RegistryWrapper getRegistryWrapper() {
        return registryWrapper;
    }

    protected T createNewEntity() {
        return ReflectionUtil.getTypeArgumentInstance(this.getClass(), 0);
    }

    protected D createNewDTO() {
        return ReflectionUtil.getTypeArgumentInstance(this.getClass(), 1);
    }

    public final T toEntity(final D entityDTO) {
        if (entityDTO == null) {
            return null;
        }
        T entity = createNewEntity();
        doReflectiveFieldMapping(entityDTO, entity, Integer.MAX_VALUE);
        doAdditionalMapping(entityDTO, entity);
        return entity;
    }

    /**
     * Performs any additional mapping needed that the automapper couldn't do
     *
     * @param dto    the dto to map from
     * @param entity the entity to map to
     */
    public void doAdditionalMapping(final D dto, final T entity) {
    }

    /**
     * Performs any additional mapping needed that the automapper couldn't do
     *
     * @param entity the entity to map from
     * @param dto    the dto to map to
     */
    public void doAdditionalMapping(final T entity, final D dto) {
    }

    public final D toDTO(final T entity) {
        return toDTO(entity, 0);
    }

    public final D toDTO(final T entity, final Integer depth) {
        if (entity == null || !hasPermission(entity)) {
            return null;
        }
        D dto = createNewDTO();

        doReflectiveFieldMapping(entity, dto, depth);
        doAdditionalMapping(entity, dto);

        return dto;
    }

    /**
     * This method maps objects from one object to the other
     *
     * @param from
     * @param to
     */
    public final void doReflectiveFieldMapping(final Object from, final Object to, final Integer depth) {
        Class fromClass = from.getClass();
        Class toClass = to.getClass();
        doReflectiveFieldMapping(fromClass, from, toClass, to, depth);
    }

    private void doReflectiveFieldMapping(
            final Class fromClass,
            final Object from,
            final Class toClass,
            final Object to,
            final Integer depth) {
        // We're not doing any mapping on the Object level
        if (fromClass == null || toClass == null || fromClass == Object.class || toClass == Object.class) {
            return;
        }
        Arrays.stream(fromClass.getDeclaredMethods())
                .filter(method -> method.getName().startsWith("get") || method.getName().startsWith("is"))
                .forEach(fromMethod ->
                        tryToMapField(fromMethod, from, toClass, to, depth)
                );
        Class fromSuperClass = fromClass.getSuperclass();
        Class toSuperClass = toClass.getSuperclass();
        doReflectiveFieldMapping(fromSuperClass, from, toSuperClass, to, depth);
    }

    private void tryToMapField(
            final Method getterMethod,
            final Object from,
            final Class toClass,
            final Object to,
            final Integer depth) {
        String setterMethodName;
        if (getterMethod.getName().startsWith("get")) {
            setterMethodName = "s" + getterMethod.getName().substring(1);
        } else if (getterMethod.getName().startsWith("is")) {
            setterMethodName = "set" + getterMethod.getName().substring(2);
        } else {
            logger.warn(
                    "found a getter of " + getterMethod.getName() + ", but could not determine setter name");
            return;
        }

        Type returnType = getterMethod.getGenericReturnType();
        try {

            // we'll use the non-generic return type to try to at least get the method
            Method setterMethod = toClass.getDeclaredMethod(setterMethodName, getterMethod.getReturnType());
            Type setterType = setterMethod.getGenericParameterTypes()[0]; //assuming this has one parameter
            if (returnType == setterType) {
                Object fromValue = getterMethod.invoke(from);
                setterMethod.invoke(to, fromValue);
            } else {
                // Looks like we're probably dealing with a parameterized type here (like a list)
                // We're only going do do this if we want to go deeper
                if (shouldProcessDeeper(depth)) {
                    Object fromValue = getterMethod.invoke(from);
                    if (fromValue != null) {
                        Class<?> nonGenericSetterType = setterMethod.getParameters()[0].getType();
                        if (nonGenericSetterType.isAssignableFrom(fromValue.getClass())) {
                            // At this point we're dealing with a valid method mapping

                            // Let's see if we're dealing with a list here
                            if (fromValue instanceof List) {

                                // TODO: can we find a way to do generics here?
                                List fromList = (List) fromValue;

                                Object collect = fromList.stream().map(item ->
                                        createAndMapObject(getNextDepth(depth), item)
                                ).collect(Collectors.toList());
                                setterMethod.invoke(to, collect);
                            }
                        }
                    }
                }
            }

        } catch (NoSuchMethodException e) {
            if (shouldProcessDeeper(depth)) {
                // At this point, we couldn't find an easy way to map methods.  We'll assuming that we're
                // trying to map different data types (liken an entity to a dto), so we'll just do a named
                // based match.  This will require looping through all the declared methods and finding
                // the right one by name.

                Method setterMethod = Arrays.stream(toClass.getMethods())
                        .filter(method -> method.getName().equals(setterMethodName))
                        .collect(CustomCollectors.singletonOrNullCollector());
                if (setterMethod != null) {
                    try {
                        Object fromValue = getterMethod.invoke(from);
                        if (fromValue != null) {
                            if (setterMethod.getParameters()[0].getType()
                                    .isAssignableFrom(fromValue.getClass())) {
                                setterMethod.invoke(to, fromValue);
                            } else {
                                Object result = createAndMapObject(getNextDepth(depth), fromValue);
                                setterMethod.invoke(to, result);
                            }
                        }
                    } catch (MissingAdapterException | IllegalAccessException | InvocationTargetException
                            ex) {
                        logger.warn("could not auto-map field");
                    }
                }
            }
            logger.trace(
                    "Could not find method \"" + setterMethodName + "\" in \"" + toClass.getSimpleName()
                            + "\"");

        } catch (InvocationTargetException | IllegalAccessException e) {
            logger.warn(
                    "Could not call setter \"" + setterMethodName + "\" in \"" + toClass.getSimpleName()
                            + "\"");
        }
    }

    @SuppressWarnings("unchecked")
    private Object createAndMapObject(final Integer nextDepth, final Object item) {
        BaseEntityAdapter adapter =
                registryWrapper.getAdapterRegistry().getAdapter(item.getClass());
        if (item instanceof BaseEntity) {
            BaseEntity baseEntity = (BaseEntity) item;
            return adapter.toDTO(baseEntity, nextDepth);
        } else if (item instanceof BaseEntityDTO) {
            return adapter.toEntity((BaseEntityDTO) item);
        }
        return null;
    }

    protected boolean hasPermission(final BaseEntity baseEntity) {
        try {
            BaseEntity one = getRegistryWrapper().getRepositoryRegistry().getRepository(baseEntity.getClass())
                    .getOne(baseEntity.getId());
            return one.getId().equals(baseEntity.getId());
        } catch (Exception ex) {
            // swallow, and return false
            return false;
        }
    }

    public final List<D> toDTOs(final Iterable<T> entities) {
        return toDTOs(entities, 0);
    }

    public final List<D> toDTOs(final Iterable<T> entities, final Integer depth) {
        List<D> result = new ArrayList<>();
        entities.forEach(e -> result.add(toDTO(e, depth)));
        return result;
    }

    private int getNextDepth(final Integer depth) {
        if (depth != null && depth > 0) {
            return Integer.min(depth, getMaxDepth()) - 1;
        }
        return 0;
    }

    private boolean shouldProcessDeeper(final Integer depth) {
        return depth != null && Integer.min(getMaxDepth(), depth) > 0;
    }

    protected int getMaxDepth() {
        return 5;
    }

}
