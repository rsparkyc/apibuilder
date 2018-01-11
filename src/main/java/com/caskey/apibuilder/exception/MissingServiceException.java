package com.caskey.apibuilder.exception;

public class MissingServiceException extends RuntimeException {
    public MissingServiceException(final String message) {
        super(message);
    }
}
