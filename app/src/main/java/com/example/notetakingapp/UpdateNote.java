package com.example.notetakingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class UpdateNote extends AppCompatActivity {

    private int ID = -1;
    private int noteId ;
    private NotesContract note = null;
    private NotesDBHelper dbHelper;
    private EditText title, detail;
    private FloatingActionButton Fab,backBtn;
    private Button btnDate;
    private Button btnTime;
    private TextView date, time;
    private int mHour, minute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);
        ID =  getIntent().getIntExtra("backupID",0);
        int id=ID;
        Toast.makeText(getApplicationContext()," The ID passed is : "+ ID,Toast.LENGTH_SHORT).show();
        fvd();
        fillFields(ID);

        //Set time
        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        UpdateNote.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int mMinute) {
                                mHour = hourOfDay;
                                minute = mMinute;
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(0, 0, 0, mHour, minute);
                                time.setText(hourOfDay + ":" + mMinute);

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
                        detail.getText().toString().trim().length() > 0) {
                    String noteTitle = title.getText().toString();
                    String noteDescription = detail.getText().toString();
                    String noteDate = date.getText().toString();
                    String noteTime = time.getText().toString();
                    NotesContract note=new NotesContract(noteTitle,noteDescription,noteDate,noteTime);
                    note.setID(noteId);
                    if(note ==null){
                        Toast.makeText(getApplicationContext(),"Null",Toast.LENGTH_SHORT).show();
                    }else{
                        dbHelper=new NotesDBHelper(getApplicationContext());
                        dbHelper.updateNote(note);
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

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

    }

    private void fvd() {
        Fab=findViewById(R.id.updateNoteFab);
        backBtn=findViewById(R.id.goBack);
        btnDate=findViewById(R.id.dateBtn);
        btnTime=findViewById(R.id.timeBtn);
        title=findViewById(R.id.editTextTitle);
        detail=findViewById(R.id.editTextDetail);
        date=findViewById(R.id.dateText);
        time=findViewById(R.id.timeText);
    }

    private void fillFields(int id) {
        dbHelper = new NotesDBHelper(this);
        note = dbHelper.findNote(ID);
        noteId=note.getID();
        title.setText(note.getTitle());
        detail.setText(note.getDescription());
        date.setText(note.getDate());
        time.setText(note.getTime());
    }
}