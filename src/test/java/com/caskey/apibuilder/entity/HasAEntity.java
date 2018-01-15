package com.caskey.apibuilder.entity;

public abstract class HasAEntity<T> extends BaseEntity {
    public abstract T getSomething();

    public abstract void setSomething(T item);

}
