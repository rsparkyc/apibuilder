package com.caskey.apibuilder.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.caskey.apibuilder.response.StandardResponse;

public class ControllerResponseUtil {
    public static <T extends StandardResponse> HttpEntity<T> buildHttpEntity(
            final T response,
            final HttpStatus failedStatus) {
        HttpStatus status = response.isSuccessful() ? HttpStatus.OK : failedStatus;
        return new ResponseEntity<>(response, status);
    }

    public static <T extends StandardResponse> HttpEntity<T> buildHttpEntity(final T response) {
        return buildHttpEntity(response, HttpStatus.BAD_REQUEST);
    }

    public static <T> HttpEntity<T> buildHttpEntity(final T dto) {
        HttpStatus status = dto == null ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity<>(dto, status);
    }

    public static <T> HttpEntity<Iterable<T>> buildHttpEntities(
            final Iterable<T> dtos) {
        HttpStatus status = dtos == null ? HttpStatus.BAD_REQUEST : HttpStatus.OK;
        return new ResponseEntity<>(dtos, status);
    }

    public static HttpEntity<StandardResponse> getStandardResponseFromSuccessBoolean(
            final boolean success,
            final String errorMessage) {
        if (success) {
            return ControllerResponseUtil.buildHttpEntity(StandardResponse.successful());
        } else {
            return ControllerResponseUtil.buildHttpEntity(StandardResponse.unsuccessful(errorMessage));
        }
    }

    //TODO Method to return success for catch an exception and turn it into an unsuccessful standard response
}
