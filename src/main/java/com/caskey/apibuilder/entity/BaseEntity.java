package com.caskey.apibuilder.entity;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@MappedSuperclass
public class BaseEntity {

    /**
     * An auto-generated ID field
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The date this entity was created
     */
    @Column
    private Date createdDate;

    /**
     * The date this entity was last modified
     */
    @Column
    private Date modifiedDate;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @PrePersist
    protected void onCreate() throws Exception {
        this.createdDate = this.modifiedDate = new Date();
    }

    @PreUpdate
    protected void onUpdate() throws Exception {
        this.modifiedDate = new Date();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BaseEntity baseEntity = (BaseEntity) o;

        return Objects.equals(getId(), baseEntity.getId()) &&
                Objects.equals(getCreatedDate(), baseEntity.getCreatedDate()) &&
                Objects.equals(getModifiedDate(), baseEntity.getModifiedDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEntityType(), getId(), getCreatedDate(), getModifiedDate());
    }

    public String getEntityType() {
        return this.getClass().getSimpleName();
    }
}
