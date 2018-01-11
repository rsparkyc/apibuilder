package com.caskey.apibuilder.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.requestBody.BaseEntityDTO;
import com.caskey.apibuilder.service.ListService;
import com.caskey.apibuilder.service.registry.ListServiceRegistry;

public interface ListController<T extends BaseEntity, D extends BaseEntityDTO> extends BaseController<T> {

    ListServiceRegistry getListServiceRegistry();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody default HttpEntity<Iterable<D>> list() {
        ListService<T, D> listService = getListServiceRegistry().getService(getEntityType());
        return new ResponseEntity<>(listService.listAllDTOs(), HttpStatus.OK);

    }
}
