package com.bocai.newnote.mvp.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.bocai.newnote.mvp.model.bean.Category;
import com.bocai.newnote.mvp.model.bean.Note;


public class DatabaseHelper2 extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "Note";

    private static final int VERSION = 1;

    public DatabaseHelper2(Context context) {
        super(context, DATABASE_NAME, null, VERSION, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+
                Note.TABLE_NAME + "(" +
                Note.ID+ " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                Note.CATEGORY_ID+" INTEGER,"+
                Note.TITLE+" TEXT,"+
                Note.CONTENT+" TEXT,"+
                Note.LAST_EDIT_TIME+" INTEGER,"+
                Note.IMAGE_PATH+" TEXT,"+
                Note.AUDIO_PATH+" TEXT,"+
                Note.VIDEO_PATH+" TEXT,"+
                Note.LOCALTION_X+" REAL,"+
                Note.LOCALTION_Y+" REAL,"+
                Note.LOCALTION_NAME+" TEXT"+
                Note.CREATETIME+" INTEGER"+
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ Note.TABLE_NAME);
        onCreate(db);
    }

}
