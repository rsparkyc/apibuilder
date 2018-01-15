package com.caskey.apibuilder.entity;

public class SomeOtherEntity extends NamedBaseEntity {
    private String someStringField;

    public String getSomeStringField() {
        return someStringField;
    }

    public void setSomeStringField(final String someStringField) {
        this.someStringField = someStringField;
    }
}
