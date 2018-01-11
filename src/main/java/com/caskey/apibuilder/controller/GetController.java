package com.caskey.apibuilder.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.caskey.apibuilder.adapter.BaseEntityAdapter;
import com.caskey.apibuilder.adapter.registry.AdapterRegistry;
import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.requestBody.BaseEntityDTO;
import com.caskey.apibuilder.service.GetService;
import com.caskey.apibuilder.service.registry.GetServiceRegistry;

public interface GetController<T extends BaseEntity, D extends BaseEntityDTO> extends BaseController<T> {

    GetServiceRegistry getGetServiceRegistry();

    AdapterRegistry getAdapterRegistry();

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody default HttpEntity<D> get(@PathVariable final Long id) {

        GetService<T> getService = getGetServiceRegistry().getService(getEntityType());
        if (getService != null) {
            T byId = getService.getById(id);

            BaseEntityAdapter<T, D> adapter =
                    getAdapterRegistry().getAdapter(getEntityType());
            D dto = adapter.toDTO(byId);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        return new ResponseEntity<>((D) null, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
