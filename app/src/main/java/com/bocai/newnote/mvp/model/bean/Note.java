package com.bocai.newnote.mvp.model.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Note  implements Serializable, Comparable<Note> {

	/**
	 * 第一版本
	 */
	private static final long serialVersionUID = 1L;
	public static transient final String TABLE_NAME = "note";
	public static transient final String ID = "_id";
	public static transient final String CATEGORY_ID = "categoryid";
	public static transient final String TITLE = "title";
	public static transient final String CONTENT = "content";
	public static transient final String LAST_EDIT_TIME = "lastedittime";
	public static transient final String IMAGE_PATH = "imagepath";
	public static transient final String AUDIO_PATH = "audiopath";
	public static transient final String VIDEO_PATH = "videopath";
	public static transient final String LOCALTION_X = "locationx";
	public static transient final String LOCALTION_Y = "locationy";
	public static transient final String LOCALTION_NAME = "locationname";
	public static transient final String CREATETIME = "createtime";

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
	private long createtime;

	public Note(int id, int categoryId, String title, String content,
			long lastEditTime, String imagepath,String audioPath, String videoPath,
			double localtionX, double localtionY, String localtionName,long createtime) {
		this.id = id;
		this.categoryId = categoryId;
		this.title = title;
		this.content = content;
		this.lastEditTime = lastEditTime;
		this.imagepath = imagepath;
		this.audioPath = audioPath;
		this.videoPath = videoPath;
		this.localtionX = localtionX;
		this.localtionY = localtionY;
		this.localtionName = localtionName;
		this.createtime = createtime;
	}



	public long getCreatetime() {
		return createtime;
	}

	public void setCreatetime(long createtime) {
		this.createtime = createtime;
	}

	public String getImagepath() {
		return imagepath;
	}

	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}


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

	public String getFormatLastEditTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss",
				Locale.CHINA);
		return format.format(new Date(lastEditTime));
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

	@Override
	public int compareTo(Note another) {
		return (int) (another.getLastEditTime() - lastEditTime);
	}


}
