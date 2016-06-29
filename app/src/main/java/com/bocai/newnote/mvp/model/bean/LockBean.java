package com.bocai.newnote.mvp.model.bean;

public class LockBean {//可以数据绑定 dataBinding

    private String warn;
    private int color;

    public LockBean(String name ) {
        this.warn = name;
    }

    public String getWarn() {
        return warn;
    }

    public void setWarn(String warn) {
        this.warn = warn;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return this.color;
    }

}
