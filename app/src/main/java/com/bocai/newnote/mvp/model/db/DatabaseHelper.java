package com.bocai.newnote.mvp.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.bocai.newnote.mvp.model.bean.Category;
import com.bocai.newnote.mvp.model.bean.Note;


public class DatabaseHelper extends SQLiteOpenHelper{

	private static final String DATABASE_NAME = "Category";
	
	private static final int VERSION = 1;
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, VERSION, null);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS "+
				Category.TABLE_NAME + "(" +
				Category.ID+ " INTEGER PRIMARY KEY AUTOINCREMENT,"+
				Category.CATEGORY_NAME+" TEXT"+
				")");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS "+ Category.TABLE_NAME);
		onCreate(db);
	}

}
