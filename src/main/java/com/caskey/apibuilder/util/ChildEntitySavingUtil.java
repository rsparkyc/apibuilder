package com.caskey.apibuilder.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;

import com.caskey.apibuilder.RegistryWrapper;
import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.exception.MissingEntityException;
import com.caskey.apibuilder.service.CreateService;
import com.caskey.apibuilder.service.UpdateService;

public class ChildEntitySavingUtil {
    private final static Logger logger = LoggerFactory.getLogger(ChildEntitySavingUtil.class);

    private ChildEntitySavingUtil() {

    }

    public static <T extends BaseEntity> T saveAnyChildren(
            final T entity,
            final RegistryWrapper registryWrapper) {
        Class<? extends BaseEntity> entityClass = entity.getClass();

        Method[] methods = entityClass.getMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("get")) {
                Class<?> returnType = method.getReturnType();
                //check assignability
                if (BaseEntity.class.isAssignableFrom(returnType)) {
                    try {
                        BaseEntity childEntity = (BaseEntity) method.invoke(entity);

                        if (childEntity != null) {
                            if (childEntity.getId() == null) {
                                logger.debug("Child entity has ID of null, we'll need to call create on it as"
                                        + " well");

                                // This entity needs to be saved, let's use its createService to save it

                                CreateService childEntityCreateService =
                                        (CreateService) registryWrapper.getCreateServiceRegistry()
                                                .getService(childEntity.getClass());

                                // This should update the object itself, so we shouldn't need to deal with the
                                // return type

                                //noinspection unchecked
                                childEntityCreateService.create(childEntity);
                            } else {
                                logger.debug("Child entity has ID, we'll need to call update on it as well");

                                // This entity needs to be saved, let's use its updateService to save it

                                UpdateService childEntityUpdateService =
                                        (UpdateService) registryWrapper.getUpdateServiceRegistry()
                                                .getService(childEntity.getClass());

                                // This should update the object itself, so we shouldn't need to deal with the
                                // return type

                                //noinspection unchecked
                                try {
                                    childEntityUpdateService.update(childEntity);
                                } catch (AccessDeniedException ex) {
                                    logger.info(
                                            "The current user did not have the necessary permissions to "
                                                    + "update the object for this method: "
                                                    + method.getName(),
                                            ex);
                                }
                            }
                        }

                    } catch (InvocationTargetException | IllegalAccessException | MissingEntityException e) {
                        logger.warn("Could not invoke child entity getter (" + method.getName() + ")", e);
                    }

                }

            }
        }

        return entity;
    }

}
