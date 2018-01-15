package com.caskey.apibuilder.entity;

public abstract class BaseChildEntity extends BaseEntity {
    private String commonChildField;

    public String getCommonChildField() {
        return commonChildField;
    }

    public void setCommonChildField(final String commonChildField) {
        this.commonChildField = commonChildField;
    }
}
