package com.broadcast.app.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * (Clientterm)实体类
 *
 * @author makejava
 * @since 2021-02-02 21:33:42
 */
public class Clientterm implements Serializable {
    private static final long serialVersionUID = -18436861669300336L;

    private String clientid;

    private String orgid;

    private Date createtime;

    private String blockpeers;

    private String clientversion;

    private String ipfspeers;


    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getBlockpeers() {
        return blockpeers;
    }

    public void setBlockpeers(String blockpeers) {
        this.blockpeers = blockpeers;
    }

    public String getClientversion() {
        return clientversion;
    }

    public void setClientversion(String clientversion) {
        this.clientversion = clientversion;
    }

    public String getIpfspeers() {
        return ipfspeers;
    }

    public void setIpfspeers(String ipfspeers) {
        this.ipfspeers = ipfspeers;
    }

}