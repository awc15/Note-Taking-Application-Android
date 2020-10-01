package com.example.notetakingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class ShowNote extends AppCompatActivity {

    TextView title, detail, date, time;
    SQLiteDatabase mDatabase;
    NotesDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note);
        long ID = getIntent().getIntExtra("ID", 0);
        String SEARCH_QUERY = "SELECT * FROM " + NotesContract.Notes.TABLE_NAME +
                " WHERE " + NotesContract.Notes._ID + " = " + ID;
        // Toast.makeText(getApplicationContext(),"ID : "+ ID,Toast.LENGTH_SHORT).show();
        title = findViewById(R.id.title);
        detail = findViewById(R.id.detail);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        dbHelper = new NotesDBHelper(this);
        mDatabase = dbHelper.getReadableDatabase();

        Cursor cursor = mDatabase.query(NotesContract.Notes.TABLE_NAME,
                new String[]{NotesContract.Notes.NOTES_TITLE, NotesContract.Notes.NOTES_DESCRIPTION,
                        NotesContract.Notes.NOTES_TIME,NotesContract.Notes.NOTES_DATE},
                NotesContract.Notes._ID + "=?",
                new String[]{String.valueOf(ID)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
       // String checkTitle = ;
        title.setText(cursor.getString(cursor.getColumnIndex(NotesContract.Notes.NOTES_TITLE)));
        detail.setText(cursor.getString(cursor.getColumnIndex(NotesContract.Notes.NOTES_DESCRIPTION)));
        date.setText(cursor.getString(cursor.getColumnIndex(NotesContract.Notes.NOTES_DATE)));
        time.setText(cursor.getString(cursor.getColumnIndex(NotesContract.Notes.NOTES_TIME)));
        cursor.close();






//        Cursor cursor = mDatabase.rawQuery(SEARCH_QUERY, null);
//        if (cursor != null) {
//            cursor.moveToNext();
//            title.setText(cursor.getColumnIndex(NotesContract.Notes.NOTES_TITLE));
        }

    }
