package com.broadcast.app.entity;

import java.io.Serializable;
import java.util.List;

public class PlayList  implements Serializable {
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


