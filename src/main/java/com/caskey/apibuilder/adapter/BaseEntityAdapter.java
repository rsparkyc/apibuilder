package com.caskey.apibuilder.adapter;

import com.caskey.apibuilder.entity.BaseEntity;
import com.caskey.apibuilder.requestBody.CreateRequestBody;

public abstract class BaseEntityAdapter<T extends BaseEntity, B extends CreateRequestBody> {

    public abstract T toEntity(final B createRequestBody);

}
