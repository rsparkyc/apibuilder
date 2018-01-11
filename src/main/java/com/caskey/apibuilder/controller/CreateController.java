package com.caskey.apibuilder.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.requestBody.BaseEntityDTO;
import com.caskey.apibuilder.service.CreateService;
import com.caskey.apibuilder.service.registry.CreateServiceRegistry;

public interface CreateController<T extends BaseEntity, D extends BaseEntityDTO>
        extends BaseController<T> {

    CreateServiceRegistry getCreateServiceRegistry();

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody default HttpEntity<T> get(@RequestBody final D entityDTO) {

        CreateService<T, D> getService = getCreateServiceRegistry().getService(getEntityType());
        if (getService != null) {
            return new ResponseEntity<>(getService.create(entityDTO), HttpStatus.OK);
        }
        return new ResponseEntity<>((T) null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
