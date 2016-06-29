package com.bocai.newnote.mvp.model.bean;

public class AboutDB {//可以数据绑定 dataBinding
    private String version;

    public AboutDB(String version) {
        this.version = version;
    }

    public String getVersion() {
        return "V"+this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
