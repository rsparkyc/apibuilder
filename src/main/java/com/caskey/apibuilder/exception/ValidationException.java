package com.caskey.apibuilder.exception;

import java.util.HashMap;
import java.util.Map;

public class ValidationException extends Exception {
    private final Map<String, String> failures;

    public ValidationException() {
        failures = new HashMap<>();
    }

    public ValidationException withFailure(final String field, final String reason) {
        failures.put(field, reason);
        return this;
    }

    public Map<String, String> getFailures() {
        return failures;
    }
}
