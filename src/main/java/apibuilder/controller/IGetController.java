/**
 * Copyright (C) 2017 by Amobee Inc.
 * All Rights Reserved.
 */

package apibuilder.controller;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import apibuilder.entity.BaseEntity;
import apibuilder.service.IGetService;
import apibuilder.service.factory.GetServiceFactory;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

public interface IGetController<T extends BaseEntity> {
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody default HttpEntity<T> get(@PathVariable final Long id) {
        Type entityType = ((ParameterizedType) getClass()
                .getGenericInterfaces()[0]).getActualTypeArguments()[0];

        IGetService<T> getService = GetServiceFactory.getService(entityType);
        if (getService != null) {
            return new ResponseEntity<>(getService.getById(id), HttpStatus.OK);
        }
        return new ResponseEntity<>((T) null, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
