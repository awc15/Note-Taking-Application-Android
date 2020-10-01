package com.example.notetakingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
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
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NotesAdapter.OnNoteListener {

    private SQLiteDatabase mDatabase;
    private FloatingActionButton fab, deleteAllFab;
    private NotesAdapter adapter;
    private RecyclerView recyclerView;
    private NotesDBHelper dbHelper;
    private List<NotesContract> allNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = findViewById(R.id.floatingButton);
        deleteAllFab = findViewById(R.id.deleteAll);
        dbHelper = new NotesDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();
        recyclerView = findViewById(R.id.recyclerView);
        fillRecyclerView();
        deleteAllFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (adapter!=null){
                    dbHelper.deleteAllNotes();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
                else{
                    Snackbar.make(view, "There's Nothing to DELETE", BaseTransientBottomBar.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void fillRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

        allNotes = dbHelper.listNotes();
        if (allNotes.size() > 0) {
            adapter = new NotesAdapter(this, allNotes, this);
            recyclerView.setAdapter(adapter);

        }
    }

    public void AddNote(View view) {
        startActivity(new Intent(getApplicationContext(), AddNote.class));

    }

    @Override
    public void onNoteClick(int position) {
        position = (int) allNotes.get(position).getID();
        Intent intent = new Intent(getApplicationContext(), ShowNote.class);
        intent.putExtra("ID", position);
        startActivity(intent);
    }
}