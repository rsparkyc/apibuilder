package com.caskey.apibuilder.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.caskey.apibuilder.exception.ValidationException;
import com.caskey.apibuilder.response.StandardResponse;
import com.caskey.apibuilder.util.ControllerResponseUtil;

public abstract class ErrorHandlingController {

    private final static Logger logger = LoggerFactory.getLogger(ErrorHandlingController.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public HttpEntity<StandardResponse> handleError(final HttpServletRequest req, final Exception ex) {
        if (ex instanceof ValidationException) {
            return handleValidationException((ValidationException) ex);
        }
        if (ex.getCause() != null && ex.getCause() instanceof ValidationException) {
            return handleValidationException((ValidationException) ex.getCause());
        }
        logger.warn("Handing bad response", ex);
        return ControllerResponseUtil.buildHttpEntity(
                StandardResponse.unsuccessful("There was a problem: " + ex.getMessage()),
                HttpStatus.BAD_REQUEST);

    }

    private HttpEntity<StandardResponse> handleValidationException(final ValidationException ex) {

        logger.warn("Handing validation error response", ex);
        StandardResponse unsuccessful =
                StandardResponse.unsuccessful("There was an error validating the given input.");

        ex.getFailures().forEach((field, reason) ->
                unsuccessful.addError("There was a problem with the field \"" + field + "\": " + reason));

        return ControllerResponseUtil.buildHttpEntity(unsuccessful, HttpStatus.BAD_REQUEST);

    }
}
