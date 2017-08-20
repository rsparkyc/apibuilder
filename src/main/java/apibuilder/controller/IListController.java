package apibuilder.controller;

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

public interface IListController<T extends BaseEntity> extends IBaseController<T> {

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody default HttpEntity<Iterable<T>> list() {
        IListService<T> listService = ListServiceFactory.getService(getEntityType());
        if (listService != null) {
            return new ResponseEntity<>(listService.listAll(), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ArrayList<T>(), HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
