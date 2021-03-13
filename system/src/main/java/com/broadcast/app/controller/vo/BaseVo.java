package com.broadcast.app.controller.vo;

public class BaseVo {

    private int offset;
    private int limit;
    private int currentPage;
    private int pagesize;
    private int total;
    public int getOffset() {
        this.offset=getCurrentPage()>0?(getCurrentPage()-1)*getPagesize():0;
        return offset;
    }
    public void setOffset(int offset) {
        this.offset = offset;
    }
    public int getLimit() {
        this.limit=getOffset()+getPagesize()*(getCurrentPage()>0?1:0);
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
