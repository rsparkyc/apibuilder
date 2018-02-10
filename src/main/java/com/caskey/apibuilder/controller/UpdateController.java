package com.caskey.apibuilder.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.exception.MissingEntityException;
import com.caskey.apibuilder.requestBody.BaseEntityDTO;
import com.caskey.apibuilder.service.UpdateService;

public interface UpdateController<T extends BaseEntity, D extends BaseEntityDTO>
        extends BaseController<T, D> {

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody default HttpEntity<D> update(
            @PathVariable final Long id,
            @RequestBody final D entityDTO,
            @RequestParam(required = false) Integer depth) {
        UpdateService<T, D> updateService =
                getRegistryWrapper().getUpdateServiceRegistry().getService(getEntityType());
        try {
            return new ResponseEntity<>(updateService.updateAndGetDTO(id, entityDTO, depth), HttpStatus.OK);
        } catch (MissingEntityException e) {
            return new ResponseEntity<>((D) null, HttpStatus.NOT_FOUND);
        }
    }
}
