/**
 * Copyright (C) 2017 by Amobee Inc.
 * All Rights Reserved.
 */

package com.caskey.apibuilder.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.service.GetService;
import com.caskey.apibuilder.service.registry.GetServiceRegistry;

public interface GetController<T extends BaseEntity> extends BaseController<T> {

    GetServiceRegistry<T> getGetServiceRegistry();

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody default HttpEntity<T> get(@PathVariable final Long id) {

        GetService<T> getService = getGetServiceRegistry().getService(getEntityType());
        if (getService != null) {
            return new ResponseEntity<>(getService.getById(id), HttpStatus.OK);
        }
        return new ResponseEntity<>((T) null, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
