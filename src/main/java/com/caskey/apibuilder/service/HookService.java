package com.caskey.apibuilder.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;

import com.caskey.apibuilder.entity.BaseEntity;

public class HookService {

    private final static Logger logger = LoggerFactory.getLogger(HookService.class);

    private final Map<Object, LinkedList<Function<BaseEntity, BaseEntity>>> entityHooks =
            new HashMap<>();

    private final Integer noContextObject = 0;

    private Object getCurrentRequestContext() {
        try {
            return RequestContextHolder.currentRequestAttributes();
        } catch (IllegalStateException ex) {
            logger.debug("Assuming we're running this in a test context, returning null");
            return noContextObject;
        }
    }

    public void clearHooks() {
        entityHooks.put(getCurrentRequestContext(), new LinkedList<>());
    }

    public void registerHook(final Function<BaseEntity, BaseEntity> hook) {
        if (!entityHooks.containsKey(getCurrentRequestContext())) {
            clearHooks();
        }
        entityHooks.get(getCurrentRequestContext()).push(hook);
    }

    public void registerHook(final Consumer<BaseEntity> hook) {
        registerHook(entity -> {
            hook.accept(entity);
            return entity;
        });
    }

    public BaseEntity runHooks(final BaseEntity entity) {
        BaseEntity updated = entity;

        if (entityHooks.containsKey(getCurrentRequestContext())) {
            for (Function<BaseEntity, BaseEntity> fun : entityHooks.get(getCurrentRequestContext())) {
                updated = fun.apply(updated);
            }
        }

        return updated;
    }
}
