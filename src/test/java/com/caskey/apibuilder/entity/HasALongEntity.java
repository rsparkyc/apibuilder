package com.caskey.apibuilder.entity;

public class HasALongEntity extends HasAEntity<Long> {
    private Long something;

    @Override public Long getSomething() {
        return something;
    }

    @Override public void setSomething(final Long item) {
        this.something = item;

    }
}
