package apibuilder.service;

import apibuilder.entity.BaseEntity;

public interface ICreateService<T extends BaseEntity> {
    T create(final T entity);

}
