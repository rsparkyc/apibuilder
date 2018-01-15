package com.caskey.apibuilder.dto;

import java.util.List;

import com.caskey.apibuilder.requestBody.BaseEntityDTO;

public class SomeDTO extends BaseEntityDTO {
    private SomeOtherDTO objectX;
    private boolean awesome;
    private List<BaseChildDTO> children;
    private BaseChildDTO someUnknownChild;

    public SomeOtherDTO getObjectX() {
        return objectX;
    }

    public void setObjectX(final SomeOtherDTO objectX) {
        this.objectX = objectX;
    }

    public boolean isAwesome() {
        return awesome;
    }

    public void setAwesome(final boolean awesome) {
        this.awesome = awesome;
    }

    public List<BaseChildDTO> getChildren() {
        return children;
    }

    public void setChildren(final List<BaseChildDTO> children) {
        this.children = children;
    }

    public BaseChildDTO getSomeUnknownChild() {
        return someUnknownChild;
    }

    public void setSomeUnknownChild(final BaseChildDTO someUnknownChild) {
        this.someUnknownChild = someUnknownChild;
    }
}
