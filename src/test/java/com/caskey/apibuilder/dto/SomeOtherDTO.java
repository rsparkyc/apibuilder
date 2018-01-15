package com.caskey.apibuilder.dto;

import com.caskey.apibuilder.requestBody.NamedBaseEntityDTO;

public class SomeOtherDTO extends NamedBaseEntityDTO {
    private String someStringField;

    public String getSomeStringField() {
        return someStringField;
    }

    public void setSomeStringField(final String someStringField) {
        this.someStringField = someStringField;
    }
}
