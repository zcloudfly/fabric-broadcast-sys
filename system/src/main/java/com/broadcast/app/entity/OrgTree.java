package com.broadcast.app.entity;

import java.io.Serializable;
import java.util.List;

public class OrgTree implements Serializable {

    private String id;
    private String label;
    private List<OrgTree> children;

    public List<OrgTree> getChildren() {
        return children;
    }

    public void setChildren(List<OrgTree> children) {
        this.children = children;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
