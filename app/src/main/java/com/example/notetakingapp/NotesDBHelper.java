package com.example.notetakingapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.notetakingapp.NotesContract.*;
import androidx.annotation.Nullable;

public class NotesDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "notes.db";
    public static final int DATABASE_VERSION = 1;
    public NotesDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String CREATE_NOTE_TABLE = "CREATE TABLE "+ Notes.TABLE_NAME +
                " ( "+ Notes._ID +" INTEGER PRIMARY KEY AUTOINCREMENT , "+
                Notes.NOTES_TITLE + " TEXT NOT NULL, " +
                Notes.NOTES_DESCRIPTION + " TEXT NOT NULL, " +
                Notes.NOTES_DATE + " TEXT NOT NULL, "+
                Notes.NOTES_TIME +" TEXT NOT NULL "+
                ");";
        sqLiteDatabase.execSQL(CREATE_NOTE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ Notes.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
