/**
 * Copyright (C) 2018 by Amobee Inc.
 * All Rights Reserved.
 */

package com.caskey.apibuilder.requestBody;

public class NamedBaseEntityDTO extends BaseEntityDTO {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
