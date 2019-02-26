package com.caskey.apibuilder.service;

import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * In order to do hibernate transactions, we must use an interface that has a method annotated
 * with @Transactional We can use this method to inject a transaction wrapper wherever we need it.
 * <p>
 * Note: If using this method, be sure that no method up the call stack is currently using a @Transactional
 * annotation in its declaration
 */
public class TransactionalityServiceImpl implements TransactionalityService {

    private final static Logger logger = LoggerFactory.getLogger(TransactionalityServiceImpl.class);

    /**
     * The transaction wrapper function.
     *
     * @param supplier the operation to perform in a transaction
     * @return whatever you want it to return
     */
    @Override
    @Transactional
    public <T> T withTransaction(final Supplier<T> supplier) {
        logger.info("Entering transaction");
        T t = supplier.get();
        logger.info("Exiting transaction");
        return t;
    }

    /**
     * The transaction wrapper function.
     *
     * @param runnable the operation to perform in a transaction
     */
    @Override
    @Transactional
    public void withTransaction(final Runnable runnable) {
        logger.info("Entering transaction");
        runnable.run();
        logger.info("Exiting transaction");
    }
}

