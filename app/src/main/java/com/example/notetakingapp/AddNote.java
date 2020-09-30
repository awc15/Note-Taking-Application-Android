package com.example.notetakingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.util.Calendar;

public class AddNote extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    EditText title, detail;
    FloatingActionButton Fab;
    Button btnDate;
    TextView date;
    boolean dateSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        title = findViewById(R.id.editTextTitle);
        detail = findViewById(R.id.editTextDetail);
        btnDate = findViewById(R.id.dateBtn);
        date = findViewById(R.id.dateText);
        Fab = findViewById(R.id.addNoteFab);
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogFragment dateTimePicker = new DatePicker();
                dateTimePicker.show(getSupportFragmentManager(), "Set Date & Time");
            }
        });
        Fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (title.getText().toString().trim().length() > 0 &&
                        detail.getText().toString().trim().length() > 0 &&
                        dateSelected) {
                    finish();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
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
}