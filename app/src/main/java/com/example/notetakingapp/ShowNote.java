package com.example.notetakingapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import static android.os.Build.ID;

public class ShowNote extends AppCompatActivity {

    private TextView title, detail, date, time;
    private SQLiteDatabase mDatabase;
    private NotesDBHelper dbHelper;
    private Button updDelButton;
    private long ID;
    private NotesContract note = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note);
        dbHelper = new NotesDBHelper(this);
        ID = getIntent().getIntExtra("ID", 0);
        Toast.makeText(getApplicationContext(), "ID : " + ID, Toast.LENGTH_SHORT).show();
        getSupportActionBar().hide();
        fvd();
        showData((int) ID);
        fillLayout(note);

        updDelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialog();
            }
        });
    }

    private void fillLayout(NotesContract note) {
        title.setText(note.getTitle());
        detail.setText(note.getDescription());
        date.setText(note.getDate());
        time.setText(note.getTime());
    }


    private void showData(int ID) {
        dbHelper = new NotesDBHelper(this);
        note = dbHelper.findNote(ID);
    }

    private void fvd() {
        title = findViewById(R.id.title);
        detail = findViewById(R.id.detail);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        mDatabase = dbHelper.getReadableDatabase();
        updDelButton = findViewById(R.id.updateDeleteBtn);
    }

    private void ShowDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Select An Option")
                .setMessage("What you want to do?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dbHelper.deleteNote((int) note.getID());
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


}


