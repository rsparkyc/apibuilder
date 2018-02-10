package com.caskey.apibuilder.service;

import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.requestBody.BaseEntityDTO;

public interface ListService<T extends BaseEntity, D extends BaseEntityDTO> extends BaseService<T, D> {

    default Iterable<T> listAll() {
        return listAll(false);
    }

    default Iterable<T> listAll(final boolean includeArchived) {
        if (includeArchived) {
            return getRepository().findAll();
        }
        return getRepository().findByArchived(false);
    }

    default Iterable<D> listAllDTOs(final Integer depth) {
        return listAllDTOs(depth, false);
    }

    default Iterable<D> listAllDTOs(final Integer depth, final boolean includeArchived) {
        return getAdapter().toDTOs(listAll(includeArchived), depth);
    }
}
