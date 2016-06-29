package com.bocai.newnote.mvp.model.bean;

import java.io.Serializable;
import java.util.List;

public class AiQinNotepad implements Serializable {


	private static final long serialVersionUID = 1L;

	private List<Category> mListCategory;

	public AiQinNotepad(List<Category> listCategory){
		this.mListCategory = listCategory;
	}
	
	public List<Category> getmListCategory() {
		return mListCategory;
	}

	public void setmListCategory(List<Category> mListCategory) {
		this.mListCategory = mListCategory;
	}

}
