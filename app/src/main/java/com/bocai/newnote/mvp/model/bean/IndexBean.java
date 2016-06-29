package com.bocai.newnote.mvp.model.bean;

public class IndexBean {//可以数据绑定 dataBinding

    private String toolBarTitle;

    public IndexBean(String title) {
        this.toolBarTitle = title;
    }

    public void setToolBarTitle(String title) {
        this.toolBarTitle = title;
    }

    public String getToolBarTitle() {
        return this.toolBarTitle;
    }
}
