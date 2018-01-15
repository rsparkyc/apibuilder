package com.caskey.apibuilder.dto;

import com.caskey.apibuilder.requestBody.BaseEntityDTO;

public abstract class HasADTO<T> extends BaseEntityDTO {
    private T something;

    public T getSomething() {
        return something;
    }

    public void setSomething(final T something) {
        this.something = something;
    }
}
