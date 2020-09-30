package com.example.notetakingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class ShowNote extends AppCompatActivity {

    TextView title,detail,date,time;
    SQLiteDatabase mDatabase;
    NotesDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note);
        long ID=getIntent().getIntExtra("ID",0);
        String SEARCH_QUERY="SELECT * FROM "+ NotesContract.Notes.TABLE_NAME +
                " WHERE "+ NotesContract.Notes._ID + " = "+ID;
       // Toast.makeText(getApplicationContext(),"ID : "+ ID,Toast.LENGTH_SHORT).show();
        title=findViewById(R.id.title);
        detail=findViewById(R.id.detail);
        date=findViewById(R.id.date);
        time=findViewById(R.id.time);
        dbHelper=new NotesDBHelper(this);
        mDatabase=dbHelper.getReadableDatabase();
        mDatabase.execSQL(SEARCH_QUERY);
        Toast.makeText(getApplicationContext(),"Hello"+"\n"+mDatabase.execSQL(SEARCH_QUERY),Toast.LENGTH_SHORT).show();
    }
}