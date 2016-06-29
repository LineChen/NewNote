package com.bocai.newnote.mvp.model.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bocai.newnote.mvp.model.bean.Category;
import com.bocai.newnote.mvp.model.bean.Note;


public class DatabaseOperator {

	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDatabase;

	public DatabaseOperator(Context context) {
		mDbHelper = new DatabaseHelper(context);
		mDatabase = mDbHelper.getWritableDatabase();
	}

	public long addCategory(Category category) {
		ContentValues values = new ContentValues();
		values.put(Category.CATEGORY_NAME, category.getCategoryName());
		long rowID = mDatabase.insert(Category.TABLE_NAME, null, values);
		return rowID;
	}

	public int delCategory(int categoryId) {
		int amount = mDatabase.delete(Category.TABLE_NAME, Category.ID + "= ?",
				new String[] { categoryId + "" });

		ContentValues values = new ContentValues();
		values.put(Note.CATEGORY_ID, 1);
		amount = 0;
		amount = mDatabase.update(Note.TABLE_NAME, values, Note.CATEGORY_ID
				+ " = ?", new String[] { categoryId + "" });
		return amount;
	}

	public ArrayList<Category> getCategoryList() {
		ArrayList<Category> categorys = new ArrayList<Category>();
		Cursor cursor = mDatabase.query(Category.TABLE_NAME, null, null, null,
				null, null, null);
		if (cursor.moveToNext()) {
			int indexId = cursor.getColumnIndex(Category.ID);
			int indexName = cursor.getColumnIndex(Category.CATEGORY_NAME);
			Category category;
			do {
				category = new Category();
				category.setId(cursor.getInt(indexId));
				category.setCategoryName(cursor.getString(indexName));
				category.setNotes(getNoteList(category.getId()));
				categorys.add(category);
			} while (cursor.moveToNext());
		}
		cursor.close();
		return categorys;
	}

	public long addNote(Note note) {
		ContentValues values = new ContentValues();
		values.put(Note.CATEGORY_ID, note.getCategoryId());
		values.put(Note.TITLE, note.getTitle());
		values.put(Note.CONTENT, note.getContent());
		values.put(Note.LAST_EDIT_TIME, note.getLastEditTime());
		values.put(Note.AUDIO_PATH, note.getAudioPath());
		values.put(Note.VIDEO_PATH, note.getVideoPath());
		values.put(Note.LOCALTION_X, note.getLocaltionX());
		values.put(Note.LOCALTION_Y, note.getLocaltionY());
		values.put(Note.LOCALTION_NAME, note.getLocaltionName());

		long rowID = mDatabase.insert(Note.TABLE_NAME, null, values);
		return rowID;
	}

	public int delNote(int noteId) {
		int amount = mDatabase.delete(Note.TABLE_NAME, Note.ID + "= ?",
				new String[] { noteId + "" });
		return amount;
	}

	public int delNotes(List<String> ids) {
		StringBuilder sb = new StringBuilder(Note.ID + " IN (");
		int size = ids.size();
		if (size == 0)
			return 0;
		for (int i = 0; i < size - 1; i++) {
			sb.append(ids.get(i) + ",");
		}
		sb.append(ids.get(size - 1) + ")");
		int amount = mDatabase.delete(Note.TABLE_NAME, sb.toString(), null);
		return amount;
	}

	public int updataNote(Note note) {
		ContentValues values = new ContentValues();
		values.put(Note.CATEGORY_ID, note.getCategoryId());
		values.put(Note.TITLE, note.getTitle());
		values.put(Note.CONTENT, note.getContent());
		values.put(Note.LAST_EDIT_TIME, note.getLastEditTime());
		values.put(Note.AUDIO_PATH, note.getAudioPath());
		values.put(Note.VIDEO_PATH, note.getVideoPath());
		values.put(Note.LOCALTION_X, note.getLocaltionX());
		values.put(Note.LOCALTION_Y, note.getLocaltionY());
		values.put(Note.LOCALTION_NAME, note.getLocaltionName());
		values.put(Note.CREATETIME, note.getCreatetime());
		int amount = mDatabase.update(Note.TABLE_NAME, values,
				Note.ID + " = ?", new String[] { note.getId() + "" });
		return amount;
	}



	public List<Note> getNoteListByContent(String content) {
		List<Note> notes = new ArrayList<Note>();
		Cursor cursor = mDatabase.query(Note.TABLE_NAME, null, Note.TITLE
				+ " LIKE '%"+content+"%' OR " + Note.CONTENT + " LIKE '%"+content+"%'",null, null, null,
				Note.LAST_EDIT_TIME + " DESC");
		if (cursor.moveToNext()) {
			int indexId = cursor.getColumnIndex(Note.ID);
			int indexCategoryId = cursor.getColumnIndex(Note.CATEGORY_ID);
			int indexTitle = cursor.getColumnIndex(Note.TITLE);
			int indexContent = cursor.getColumnIndex(Note.CONTENT);
			int indexLastEditTime = cursor.getColumnIndex(Note.LAST_EDIT_TIME);
			int indexImagePath = cursor.getColumnIndex(Note.IMAGE_PATH);
			int indexAudioPath = cursor.getColumnIndex(Note.AUDIO_PATH);
			int indexVedio = cursor.getColumnIndex(Note.VIDEO_PATH);
			int indexLocaltionX = cursor.getColumnIndex(Note.LOCALTION_X);
			int indexLocaltionY = cursor.getColumnIndex(Note.LOCALTION_Y);
			int indexLocaltionName = cursor.getColumnIndex(Note.LOCALTION_NAME);
			int indexCreatetime = cursor.getColumnIndex(Note.CREATETIME);
			do {
				Note note = new Note(cursor.getInt(indexId),
						cursor.getInt(indexCategoryId),
						cursor.getString(indexTitle),
						cursor.getString(indexContent),
						cursor.getLong(indexLastEditTime),
						cursor.getString(indexImagePath),
						cursor.getString(indexAudioPath),
						cursor.getString(indexVedio),
						cursor.getLong(indexLocaltionX),
						cursor.getLong(indexLocaltionY),
						cursor.getString(indexLocaltionName),
						cursor.getLong(indexCreatetime)
				);
				notes.add(note);
			} while (cursor.moveToNext());

		}
		cursor.close();
		return notes;
	}

	public List<Note> getNoteList() {
		List<Note> notes = new ArrayList<Note>();
		Cursor cursor = mDatabase.query(Note.TABLE_NAME, null, null, null,
				null, null, Note.LAST_EDIT_TIME + " DESC");
		if (cursor.moveToNext()) {
			int indexId = cursor.getColumnIndex(Note.ID);
			int indexCategoryId = cursor.getColumnIndex(Note.CATEGORY_ID);
			int indexTitle = cursor.getColumnIndex(Note.TITLE);
			int indexContent = cursor.getColumnIndex(Note.CONTENT);
			int indexLastEditTime = cursor.getColumnIndex(Note.LAST_EDIT_TIME);
			int indexImagePath = cursor.getColumnIndex(Note.IMAGE_PATH);
			int indexAudioPath = cursor.getColumnIndex(Note.AUDIO_PATH);
			int indexVedio = cursor.getColumnIndex(Note.VIDEO_PATH);
			int indexLocaltionX = cursor.getColumnIndex(Note.LOCALTION_X);
			int indexLocaltionY = cursor.getColumnIndex(Note.LOCALTION_Y);
			int indexLocaltionName = cursor.getColumnIndex(Note.LOCALTION_NAME);
			int indexCreatetime = cursor.getColumnIndex(Note.CREATETIME);
			do {
				Note note = new Note(cursor.getInt(indexId),
						cursor.getInt(indexCategoryId),
						cursor.getString(indexTitle),
						cursor.getString(indexContent),
						cursor.getLong(indexLastEditTime),
						cursor.getString(indexImagePath),
						cursor.getString(indexAudioPath),
						cursor.getString(indexVedio),
						cursor.getLong(indexLocaltionX),
						cursor.getLong(indexLocaltionY),
						cursor.getString(indexLocaltionName),
						cursor.getLong(indexCreatetime)
				);

				notes.add(note);
			} while (cursor.moveToNext());

		}
		cursor.close();
		return notes;
	}

	public ArrayList<Note> getNoteList(int categoryId) {
		ArrayList<Note> notes = new ArrayList<Note>();
		Cursor cursor = mDatabase.query(Note.TABLE_NAME, null, Note.CATEGORY_ID
				+ "= ?", new String[] { categoryId + "" }, null, null,
				Note.LAST_EDIT_TIME + " DESC");
		if (cursor.moveToNext()) {
			int indexId = cursor.getColumnIndex(Note.ID);
			int indexCategoryId = cursor.getColumnIndex(Note.CATEGORY_ID);
			int indexTitle = cursor.getColumnIndex(Note.TITLE);
			int indexContent = cursor.getColumnIndex(Note.CONTENT);
			int indexLastEditTime = cursor.getColumnIndex(Note.LAST_EDIT_TIME);
			int indexImagePath = cursor.getColumnIndex(Note.IMAGE_PATH);
			int indexAudioPath = cursor.getColumnIndex(Note.AUDIO_PATH);
			int indexVedio = cursor.getColumnIndex(Note.VIDEO_PATH);
			int indexLocaltionX = cursor.getColumnIndex(Note.LOCALTION_X);
			int indexLocaltionY = cursor.getColumnIndex(Note.LOCALTION_Y);
			int indexLocaltionName = cursor.getColumnIndex(Note.LOCALTION_NAME);
			int indexCreatetime = cursor.getColumnIndex(Note.CREATETIME);
			do {
				Note note = new Note(cursor.getInt(indexId),
						cursor.getInt(indexCategoryId),
						cursor.getString(indexTitle),
						cursor.getString(indexContent),
						cursor.getLong(indexLastEditTime),
						cursor.getString(indexImagePath),
						cursor.getString(indexAudioPath),
						cursor.getString(indexVedio),
						cursor.getLong(indexLocaltionX),
						cursor.getLong(indexLocaltionY),
						cursor.getString(indexLocaltionName),
						cursor.getLong(indexCreatetime));
				notes.add(note);
			} while (cursor.moveToNext());
		}
		cursor.close();
		return notes;
	}

	public void close() {
		mDatabase.close();
		mDbHelper.close();
	}
	
}
