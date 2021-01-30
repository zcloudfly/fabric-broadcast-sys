package com.broadcast.app.entity;

import java.io.Serializable;
import java.util.List;

/**
 * (Org)实体类
 *
 * @author makejava
 * @since 2021-01-26 18:47:27
 */
public class Org implements Serializable {
    private static final long serialVersionUID = -86412803607716324L;

    private String orgid;

    private String orgname;

    private String orgparid;

    private String procode;

    private String area;

    private String level;



    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public String getOrgparid() {
        return orgparid;
    }

    public void setOrgparid(String orgparid) {
        this.orgparid = orgparid;
    }

    public String getProcode() {
        return procode;
    }

    public void setProcode(String procode) {
        this.procode = procode;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

}