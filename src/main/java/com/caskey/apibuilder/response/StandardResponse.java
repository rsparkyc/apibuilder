package com.caskey.apibuilder.response;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StandardResponse {
    private final static Logger logger = LoggerFactory.getLogger(StandardResponse.class);

    private String message;
    private List<String> errors;
    private boolean successful;

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(final List<String> errors) {
        this.errors = errors;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(final boolean successful) {
        this.successful = successful;
    }

    public static StandardResponse successful() {
        StandardResponse successfulResponse = new StandardResponse();
        successfulResponse.setSuccessful(true);
        return successfulResponse;
    }

    public static StandardResponse successful(final String message) {
        StandardResponse successfulResponse = StandardResponse.successful();
        successfulResponse.setMessage(message);
        return successfulResponse;
    }

    public static StandardResponse unsuccessful(final String message) {
        logger.warn("Returning unsuccessful response: " + message);
        StandardResponse response = new StandardResponse();
        response.setSuccessful(false);
        response.setMessage(message);
        response.addError(message);
        return response;
    }

    public void addError(final String errorMessage) {
        if (errors == null) {
            errors = new ArrayList<>();
        }

        errors.add(errorMessage);
    }
}
