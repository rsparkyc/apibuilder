package com.caskey.apibuilder.dto;

import com.caskey.apibuilder.requestBody.BaseEntityDTO;

public abstract class BaseChildDTO extends BaseEntityDTO {
    private String commonChildField;

    public String getCommonChildField() {
        return commonChildField;
    }

    public void setCommonChildField(final String commonChildField) {
        this.commonChildField = commonChildField;
    }
}
