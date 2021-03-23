package com.broadcast.app.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * (Permission)实体类
 *
 * @author makejava
 * @since 2021-03-14 17:10:12
 */
public class Permission implements Serializable {
    private static final long serialVersionUID = -78128492864006660L;

    private String id;
private  String url;
    private Date createtime;

    private String name;

    private String pid;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

}