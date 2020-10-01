package com.example.notetakingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.util.Calendar;

public class AddNote extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private SQLiteDatabase mDatabase;
    private NotesDBHelper dbHelper;
    private EditText title, detail;
    private FloatingActionButton Fab,backBtn;
    private Button btnDate;
    private Button btnTime;
    private TextView date, time;
    private boolean dateSelected = false;
    private boolean timeSet = false;
    private int mHour, minute;
    private NotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        backBtn=findViewById(R.id.goBack);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        title = findViewById(R.id.editTextTitle);
        detail = findViewById(R.id.editTextDetail);
        btnDate = findViewById(R.id.dateBtn);
        date = findViewById(R.id.dateText);
        time = findViewById(R.id.timeText);
        Fab = findViewById(R.id.addNoteFab);
        btnTime = findViewById(R.id.timeBtn);



        //Set Time
        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        AddNote.this,
                        new OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int mMinute) {
                                mHour = hourOfDay;
                                minute = mMinute;
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(0, 0, 0, mHour, minute);
                                time.setText(hourOfDay + ":" + mMinute);
                                timeSet = true;
                            }
                        }, 12, 0, true
                );
                timePickerDialog.updateTime(mHour, minute);
                timePickerDialog.show();

            }
        });


        //Set Date
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePicker();
                datePicker.show(getSupportFragmentManager(), "Set Date");
            }
        });




        Fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (title.getText().toString().trim().length() > 0 &&
                        detail.getText().toString().trim().length() > 0 &&
                        dateSelected && timeSet) {
                    String noteTitle = title.getText().toString();
                    String noteDescription = detail.getText().toString();
                    String noteDate = date.getText().toString();
                    String noteTime = time.getText().toString();
                    NotesContract note=new NotesContract(noteTitle,noteDescription,noteDate,noteTime);
                    if(note ==null){
                        Toast.makeText(getApplicationContext(),"Null",Toast.LENGTH_SHORT).show();
                    }else{
                        dbHelper=new NotesDBHelper(getApplicationContext());
                        dbHelper.addNote(note);
                        title.getText().clear();
                        detail.getText().clear();
                        date.setText("Enter Date using the button below");
                        time.setText("Enter Time using the button below");
                        finish();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }

                } else {

                    Snackbar.make(view, "Kindly fill all fields", BaseTransientBottomBar.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day) {

        dateSelected = true;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        String CurrentDate = DateFormat.getDateInstance().format(calendar.getTime());
        date.setText(CurrentDate);
    }


    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));

    }
}