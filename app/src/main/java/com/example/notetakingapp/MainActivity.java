package com.example.notetakingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase mDatabase;
    private FloatingActionButton fab;
    private NotesAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = findViewById(R.id.floatingButton);
        final NotesDBHelper dbHelper = new NotesDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        adapter = new NotesAdapter(this, getAllItems());
        if(adapter!=null){
            adapter.swapCursor(getAllItems());
        }
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new NotesAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position) {
               // Toast.makeText(getApplicationContext(),"Item : "+position,Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(),ShowNote.class);
                intent.putExtra("ID",position);
                startActivity(intent);
            }
        });
    }


    public void AddNote(View view) {
        startActivity(new Intent(getApplicationContext(), AddNote.class));

    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setTitle("Exit Application")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        finishAffinity();
                        MainActivity.super.onBackPressed();
                    }
                }).create().show();
    }

    private Cursor getAllItems() {
        Cursor cursor = mDatabase.query(
                NotesContract.Notes.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                NotesContract.Notes._ID
        );


        return cursor;
    }
}