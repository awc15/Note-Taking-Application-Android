package com.example.notetakingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.notetakingapp.NotesContract.*;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class NotesDBHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "NotesData";
    public static final String NOTES_ID = "_ID";
    public static final String NOTES_TITLE = "title";
    public static final String NOTES_DESCRIPTION = "description";
    public static final String NOTES_DATE = "date";
    public static final String NOTES_TIME = "time";
    public static final String DATABASE_NAME = "notes.db";
    public static final int DATABASE_VERSION = 1;

    public NotesDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String CREATE_NOTE_TABLE = "CREATE TABLE " + TABLE_NAME +
                " ( " + NOTES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                NOTES_TITLE + " TEXT NOT NULL, " +
                NOTES_DESCRIPTION + " TEXT NOT NULL, " +
                NOTES_DATE + " TEXT NOT NULL, " +
                NOTES_TIME + " TEXT NOT NULL " +
                ");";
        sqLiteDatabase.execSQL(CREATE_NOTE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public List<NotesContract> listNotes() {
        String sql = "select * from " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        List<NotesContract> storeAllNotes = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String title = cursor.getString(1);
                String description = cursor.getString(2);
                storeAllNotes.add(new NotesContract(id, title, description));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return storeAllNotes;
    }


    public void addNote(NotesContract note) {
        ContentValues values = new ContentValues();
        values.put(NOTES_TITLE, note.getTitle());
        values.put(NOTES_DESCRIPTION, note.getDescription());
        values.put(NOTES_DATE, note.getDate());
        values.put(NOTES_TIME, note.getTime());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
    }

    public NotesContract findNote(int id) {
        String query = "Select * FROM " + TABLE_NAME + " WHERE " + NOTES_ID + " = " + id;
        SQLiteDatabase db = this.getWritableDatabase();
        NotesContract noteData = null;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            int ID = Integer.parseInt(cursor.getString(0));
            String title = cursor.getString(1);
            String description = cursor.getString(2);
            String date = cursor.getString(3);
            String time = cursor.getString(4);
            noteData = new NotesContract(title, description, date, time);
            noteData.setID(ID);
        }
        cursor.close();
        return noteData;
    }

    public void deleteNote(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, NOTES_ID + "    = ?", new String[]{String.valueOf(id)});
    }

    public void deleteAllNotes() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
    }

    public void updateNote(NotesContract note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOTES_TITLE, note.getTitle());
        values.put(NOTES_DESCRIPTION, note.getDescription());
        values.put(NOTES_DATE, note.getDate());
        values.put(NOTES_TIME, note.getTime());
        db.update(TABLE_NAME, values, NOTES_ID + "    = ?", new String[]{String.valueOf(note.getID())});
    }
}

