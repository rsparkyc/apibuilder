package com.caskey.apibuilder.requestBody;

import java.util.Date;

// In this case, we have no nested relationships, so a BaseEntityDTO and a BaseEntity can really be
// the same thing
public abstract class BaseEntityDTO {
    private Long id;
    private Date createdDate;
    private Date modifiedDate;
    private String entityType;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(final Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(final String entityType) {
        this.entityType = entityType;
    }
}
