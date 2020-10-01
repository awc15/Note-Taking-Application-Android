package com.example.notetakingapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import static android.os.Build.ID;

public class ShowNote extends AppCompatActivity {

    private TextView title, detail, date, time;
    private SQLiteDatabase mDatabase;
    private NotesDBHelper dbHelper;
    private Button updDelButton;
    private long ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note);
        ID = getIntent().getIntExtra("ID", 0);
        // Toast.makeText(getApplicationContext(),"ID : "+ ID,Toast.LENGTH_SHORT).show();
        getSupportActionBar().hide();
        title = findViewById(R.id.title);
        detail = findViewById(R.id.detail);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        dbHelper = new NotesDBHelper(this);
        mDatabase = dbHelper.getReadableDatabase();
        showData();








        }

    private void showData() {
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
    }

}
