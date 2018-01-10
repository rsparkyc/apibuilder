package com.caskey.apibuilder.controller;

import java.util.ArrayList;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.service.ListService;
import com.caskey.apibuilder.service.registry.ListServiceRegistry;

public interface ListController<T extends BaseEntity> extends BaseController<T> {

    ListServiceRegistry<T> getListServiceRegistry();

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody default HttpEntity<Iterable<T>> list() {
        ListService<T> listService = getListServiceRegistry().getService(getEntityType());
        if (listService != null) {
            return new ResponseEntity<>(listService.listAll(), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ArrayList<T>(), HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
