package com.caskey.apibuilder.exception;

import java.util.HashMap;
import java.util.Map;

public class ValidationException extends Exception {
    private final Map<String, String> failures = new HashMap<>();

    public ValidationException(final String field, final String reason) {
        super("There was a problem validating the field " + field + ": " + reason);
        withFailure(field, reason);
    }

    public ValidationException withFailure(final String field, final String reason) {
        failures.put(field, reason);
        return this;
    }

    public Map<String, String> getFailures() {
        return failures;
    }
}
