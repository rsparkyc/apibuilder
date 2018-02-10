package com.caskey.apibuilder.requestBody;

import java.util.Date;

public abstract class BaseEntityDTO {
    private Long id;
    private Date createdDate;
    private Date modifiedDate;
    private String entityType;
    private boolean archived;

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

    public boolean getArchived() {
        return archived;
    }

    public void setArchived(final boolean archived) {
        this.archived = archived;
    }
}
