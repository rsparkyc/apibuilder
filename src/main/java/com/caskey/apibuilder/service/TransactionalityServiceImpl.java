package com.caskey.apibuilder.service;

import java.util.function.Supplier;

import org.springframework.transaction.annotation.Transactional;

/**
 * In order to do hibernate transactions, we must use an interface that has a method annotated
 * with @Transactional We can use this method to inject a transaction wrapper wherever we need it.
 * <p>
 * Note: If using this method, be sure that no method up the call stack is currently using a @Transactional
 * annotation in its declaration
 */
public class TransactionalityServiceImpl implements TransactionalityService {

    /**
     * The transaction wrapper function.
     *
     * @param supplier the operation to perform in a transaction
     * @return whatever you want it to return
     */
    @Override
    @Transactional
    public <T> T withTransaction(final Supplier<T> supplier) {
        return supplier.get();
    }

    /**
     * The transaction wrapper function.
     *
     * @param runnable the operation to perform in a transaction
     */
    @Override
    @Transactional
    public void withTransaction(final Runnable runnable) {
        runnable.run();
    }
}

