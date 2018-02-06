package com.caskey.apibuilder.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.requestBody.BaseEntityDTO;
import com.caskey.apibuilder.service.ListService;

public interface ListController<T extends BaseEntity, D extends BaseEntityDTO> extends BaseController<T, D> {

    @RequestMapping(value = { "/", "" }, method = RequestMethod.GET)
    @ResponseBody default HttpEntity<Iterable<D>> list(@RequestParam(required = false) Integer depth) {
        ListService<T, D> listService =
                getRegistryWrapper().getListServiceRegistry().getService(getEntityType());
        return new ResponseEntity<>(listService.listAllDTOs(depth), HttpStatus.OK);

    }
}
