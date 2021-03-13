package com.broadcast.app.controller.vo;

public class PlstVo {
//"[{"name":"面试.txt","staopdate":"2021-03-20 00:00:00","startdate":"2021-03-02 00:00:00","type":"pic","hashid":"QmbRXAYpbLUWr9rSnJGc1wdqdpwnivcZZMy2hMvAW6ZYEn"}]"
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
