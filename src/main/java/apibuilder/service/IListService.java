package apibuilder.service;

import apibuilder.entity.BaseEntity;

public interface IListService<T extends BaseEntity> extends IBaseService<T> {
    default Iterable<T> listAll() {
        return getRepository().findAll();
    }
}
