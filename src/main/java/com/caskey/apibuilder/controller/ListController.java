package com.caskey.apibuilder.controller;

import java.util.ArrayList;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.caskey.apibuilder.adapter.BaseEntityAdapter;
import com.caskey.apibuilder.adapter.registry.AdapterRegistry;
import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.requestBody.BaseEntityDTO;
import com.caskey.apibuilder.service.ListService;
import com.caskey.apibuilder.service.registry.ListServiceRegistry;

public interface ListController<T extends BaseEntity, D extends BaseEntityDTO> extends BaseController<T> {

    ListServiceRegistry getListServiceRegistry();

    AdapterRegistry getAdapterRegistry();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody default HttpEntity<Iterable<D>> list() {
        ListService<T> listService = getListServiceRegistry().getService(getEntityType());
        BaseEntityAdapter<T, D> adapter = getAdapterRegistry().getAdapter(getEntityType());
        if (listService != null) {
            return new ResponseEntity<>(adapter.toDTOs(listService.listAll()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ArrayList<D>(), HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
