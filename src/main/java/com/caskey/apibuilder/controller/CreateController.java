package com.caskey.apibuilder.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.requestBody.CreateRequestBody;
import com.caskey.apibuilder.service.CreateService;
import com.caskey.apibuilder.service.registry.CreateServiceRegistry;

public interface CreateController<T extends BaseEntity, B extends CreateRequestBody>
        extends BaseController<T> {

    CreateServiceRegistry<T, B> getCreateServiceRegistry();

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody default HttpEntity<T> get(@RequestBody final B requestBody) {

        CreateService<T, B> getService = getCreateServiceRegistry().getService(getEntityType());
        if (getService != null) {
            return new ResponseEntity<>(getService.create(requestBody), HttpStatus.OK);
        }
        return new ResponseEntity<>((T) null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
