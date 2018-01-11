package com.caskey.apibuilder.exception;

public class MissingRepositoryException extends RuntimeException {
    public MissingRepositoryException(final String message) {
        super(message);
    }
}
