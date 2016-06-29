package com.bocai.newnote.mvp.model.bean;

import cn.bmob.v3.BmobObject;


public class BmobNote extends BmobObject{  //
    private int id;
    private int categoryId;
    private String title;
    private String content;
    private long lastEditTime;
    private String audioPath;
    private String imagepath;
    private String videoPath;
    private double localtionX;
    private double localtionY;
    private String localtionName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(long lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public String getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public double getLocaltionX() {
        return localtionX;
    }

    public void setLocaltionX(double localtionX) {
        this.localtionX = localtionX;
    }

    public double getLocaltionY() {
        return localtionY;
    }

    public void setLocaltionY(double localtionY) {
        this.localtionY = localtionY;
    }

    public String getLocaltionName() {
        return localtionName;
    }

    public void setLocaltionName(String localtionName) {
        this.localtionName = localtionName;
    }

}
