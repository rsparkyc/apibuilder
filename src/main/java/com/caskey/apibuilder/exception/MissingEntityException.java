package com.caskey.apibuilder.exception;

public class MissingEntityException extends Throwable {
    public MissingEntityException() {
        super("An attempt was made to update a null entity.");
    }
}
