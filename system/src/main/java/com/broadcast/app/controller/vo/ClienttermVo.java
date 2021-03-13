package com.broadcast.app.controller.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * (Clientterm)实体类
 *
 * @author makejava
 * @since 2021-02-02 21:33:42
 */
public class ClienttermVo implements Serializable {
    private static final long serialVersionUID = -18436861669300336L;

    private String clientid;

    private String orgid;

    private int offset;
    private int limit;
    private int currentPage;
    private int pagesize;
    private int total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

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

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }
}