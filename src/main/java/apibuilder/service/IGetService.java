package apibuilder.service;

import apibuilder.entity.BaseEntity;

public interface IGetService<T extends BaseEntity> extends IBaseService<T> {
    default T getById(final Long id) {
        return getRepository().findOne(id);
    }

}
