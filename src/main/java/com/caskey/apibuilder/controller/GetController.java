package com.caskey.apibuilder.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.requestBody.BaseEntityDTO;
import com.caskey.apibuilder.service.GetService;

public interface GetController<T extends BaseEntity, D extends BaseEntityDTO> extends BaseController<T, D> {

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    default HttpEntity<D> get(@PathVariable final Long id, @RequestParam(required = false) Integer depth) {
        GetService<T, D> getService =
                getRegistryWrapper().getGetServiceRegistry().getService(getEntityType());
        D byId = getService.getDTOById(id, depth);
        HttpStatus httpStatus = byId == null ? HttpStatus.NOT_FOUND : HttpStatus.OK;

        return new ResponseEntity<>(byId, httpStatus);

    }
}
