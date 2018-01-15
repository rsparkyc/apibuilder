package com.caskey.apibuilder.entity;

import java.util.List;

public class SomeEntity extends BaseEntity {

    private SomeOtherEntity objectX;
    private List<BaseChildEntity> children;
    private boolean awesome;
    private BaseChildEntity someUnknownChild;

    private HasAEntity iHasA;
    private List<HasAEntity> iHasSome;

    public SomeOtherEntity getObjectX() {
        return objectX;
    }

    public void setObjectX(final SomeOtherEntity objectX) {
        this.objectX = objectX;
    }

    public List<BaseChildEntity> getChildren() {
        return children;
    }

    public void setChildren(final List<BaseChildEntity> children) {
        this.children = children;
    }

    public boolean isAwesome() {
        return awesome;
    }

    public void setAwesome(final boolean awesome) {
        this.awesome = awesome;
    }

    public BaseChildEntity getSomeUnknownChild() {
        return someUnknownChild;
    }

    public void setSomeUnknownChild(final BaseChildEntity someUnknownChild) {
        this.someUnknownChild = someUnknownChild;
    }

    public HasAEntity getiHasA() {
        return iHasA;
    }

    public void setiHasA(final HasAEntity iHasA) {
        this.iHasA = iHasA;
    }

    public List<HasAEntity> getiHasSome() {
        return iHasSome;
    }

    public void setiHasSome(final List<HasAEntity> iHasSome) {
        this.iHasSome = iHasSome;
    }
}

