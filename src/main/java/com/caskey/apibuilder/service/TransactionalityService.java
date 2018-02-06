package com.caskey.apibuilder.service;

import java.util.function.Supplier;

public interface TransactionalityService {

    <T> T withTransaction(final Supplier<T> supplier);

    void withTransaction(final Runnable runnable);
}