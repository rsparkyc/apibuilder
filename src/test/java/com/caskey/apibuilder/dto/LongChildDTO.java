package com.caskey.apibuilder.dto;

public class LongChildDTO extends BaseChildDTO {
    private Long uniqueLongField;

    public Long getUniqueLongField() {
        return uniqueLongField;
    }

    public void setUniqueLongField(final Long uniqueLongField) {
        this.uniqueLongField = uniqueLongField;
    }
}
