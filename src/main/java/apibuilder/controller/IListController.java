package apibuilder.controller;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

import apibuilder.entity.BaseEntity;
import apibuilder.service.IListService;
import apibuilder.service.factory.ListServiceFactory;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

public interface IListController<T extends BaseEntity> {

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody default HttpEntity<Iterable<T>> list() {
        Type entityType = ((ParameterizedType) getClass()
                .getGenericInterfaces()[0]).getActualTypeArguments()[0];

        IListService<T> listService = ListServiceFactory.getListService(entityType);
        if (listService != null) {
            return new ResponseEntity<>(listService.listAll(), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ArrayList<T>(), HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
