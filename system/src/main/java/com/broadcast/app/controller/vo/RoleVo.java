package com.broadcast.app.controller.vo;


import com.broadcast.app.entity.PermissionTree;

import java.util.List;

public class RoleVo extends BaseVo{

    private String id;

    private String rolename;

    private List<PermissionTree> ptree;

    public List<PermissionTree> getPtree() {
        return ptree;
    }

    public void setPtree(List<PermissionTree> ptree) {
        this.ptree = ptree;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
}
