package com.broadcast.app.entity;

import java.util.List;

public class PlayList {
    private String clientid;
    private String orgid;
    private List<ListData> data;

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

    public List<ListData> getData() {
        return data;
    }

    public void setData(List<ListData> data) {
        this.data = data;
    }
}

class ListData{
    private String name;
    private String startdate;
    private String staopdate;
    private String type;
    private String hashid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getStaopdate() {
        return staopdate;
    }

    public void setStaopdate(String staopdate) {
        this.staopdate = staopdate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHashid() {
        return hashid;
    }

    public void setHashid(String hashid) {
        this.hashid = hashid;
    }
}
