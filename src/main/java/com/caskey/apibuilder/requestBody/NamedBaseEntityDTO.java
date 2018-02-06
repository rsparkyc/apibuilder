package com.caskey.apibuilder.requestBody;

public abstract class NamedBaseEntityDTO extends BaseEntityDTO {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
