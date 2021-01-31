package com.broadcast.app.entity;

import java.io.Serializable;

/**
 * (Attach)实体类
 *
 * @author makejava
 * @since 2021-01-31 12:46:39
 */
public class Attach implements Serializable {
    private static final long serialVersionUID = 115321798071491026L;

    private String id;

    private String hashid;

    private String filename;

    private String filesize;

    private String appformid;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHashid() {
        return hashid;
    }

    public void setHashid(String hashid) {
        this.hashid = hashid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public String getAppformid() {
        return appformid;
    }

    public void setAppformid(String appformid) {
        this.appformid = appformid;
    }

}