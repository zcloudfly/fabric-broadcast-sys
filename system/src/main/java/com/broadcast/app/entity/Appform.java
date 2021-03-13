package com.broadcast.app.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * (Appform)实体类
 *
 * @author makejava
 * @since 2021-01-29 15:13:59
 */
public class Appform implements Serializable {
    private static final long serialVersionUID = -66283659881143834L;

    private String id;

    private String infotype;

    private String title;

    private String starttime;

    private String endtime;

    private String checkuser;

    private String checkuserid;

    private String distorg;

    private String distorgid;

    private String descr;

    private String senduser;

    private String senduserid;
    private String sts;

    private Date createtime;

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getSts() {
        return sts;
    }

    public void setSts(String sts) {
        this.sts = sts;
    }

    private List<Attach> files;

    public List<Attach> getFiles() {
        return files;
    }

    public void setFiles(List<Attach> files) {
        this.files = files;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInfotype() {
        return infotype;
    }

    public void setInfotype(String infotype) {
        this.infotype = infotype;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getCheckuser() {
        return checkuser;
    }

    public void setCheckuser(String checkuser) {
        this.checkuser = checkuser;
    }

    public String getCheckuserid() {
        return checkuserid;
    }

    public void setCheckuserid(String checkuserid) {
        this.checkuserid = checkuserid;
    }

    public String getDistorg() {
        return distorg;
    }

    public void setDistorg(String distorg) {
        this.distorg = distorg;
    }

    public String getDistorgid() {
        return distorgid;
    }

    public void setDistorgid(String distorgid) {
        this.distorgid = distorgid;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getSenduser() {
        return senduser;
    }

    public void setSenduser(String senduser) {
        this.senduser = senduser;
    }

    public String getSenduserid() {
        return senduserid;
    }

    public void setSenduserid(String senduserid) {
        this.senduserid = senduserid;
    }

}