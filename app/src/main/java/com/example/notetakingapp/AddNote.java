package com.example.notetakingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.TimeUtils;
import androidx.fragment.app.DialogFragment;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
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

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;

public class AddNote extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private SQLiteDatabase mDatabase;
    private NotesDBHelper dbHelper;
    private EditText title, detail;
    private FloatingActionButton Fab, backBtn;
    private Button btnDate;
    private Button btnTime;
    private TextView date, time;
    private boolean dateSelected = false;
    private boolean timeSet = false;
    private int mHour, minute;
    private NotesAdapter adapter;
    private long milliseconds=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        createNotificationChannel();
        fvd();

    }

    private void fvd() {
        backBtn = findViewById(R.id.goBack);
        title = findViewById(R.id.editTextTitle);
        detail = findViewById(R.id.editTextDetail);
        btnDate = findViewById(R.id.dateBtn);
        date = findViewById(R.id.dateText);
        time = findViewById(R.id.timeText);
        Fab = findViewById(R.id.addNoteFab);
        btnTime = findViewById(R.id.timeBtn);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Hello";
            String descrip = "This is a channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyNow", name, importance);
            channel.setDescription(descrip);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }

    private void schedulingNotes(int noteID, String noteDate, String noteTime) {
//       Data.Builder noteData =new Data.Builder();
//       noteData.putInt("NoteID",noteID);
//       noteData.putString("Date",noteDate);
//        noteData.putString("Time",noteTime);
//        WorkRequest scheduleNotes =new OneTimeWorkRequest.Builder(NoteScheduler.class)
//                .setInputData(noteData.build())
//                .setInitialDelay().build();
//        WorkManager.getInstance(getApplicationContext()).enqueue(scheduleNotes);

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
        startActivity(new Intent(getApplicationContext(), MainActivity.class));

    }

    public void FabGoOnPreviousActivity(View view) {
        onBackPressed();
    }

    public void FabAddNote(View view) {

        if (title.getText().toString().trim().length() > 0 &&
                detail.getText().toString().trim().length() > 0 &&
                dateSelected && timeSet) {
            String noteTitle = title.getText().toString();
            String noteDescription = detail.getText().toString();
            String noteDate = date.getText().toString();
            String noteTime = time.getText().toString();
            NotesContract note = new NotesContract(noteTitle, noteDescription, noteDate, noteTime);
            if (note == null) {
                Toast.makeText(getApplicationContext(), "Null", Toast.LENGTH_SHORT).show();
            } else {
                dbHelper = new NotesDBHelper(getApplicationContext());
                int noteId = dbHelper.addNote(note);
                Toast.makeText(getApplicationContext(), "Reminder Set ", Toast.LENGTH_SHORT).show();
                convertTimeToMillis(mHour, minute);
                Intent intent = new Intent(AddNote.this, ReminderAlarmBroadcast.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(AddNote.this, 0, intent, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP,milliseconds,pendingIntent);
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }

        } else {

            Snackbar.make(view, "Kindly fill all fields", BaseTransientBottomBar.LENGTH_SHORT).show();
        }
    }

    private void convertTimeToMillis(int mHour, int minute) {
        long hourMilliseconds = mHour * 60 * 60 * 1000;
        long minuteMilliseconds = minute  * 60 * 1000;
        milliseconds=minuteMilliseconds+hourMilliseconds;
    }

    public void setDateBtn(View view) {

        DialogFragment datePicker = new DatePicker();
        datePicker.show(getSupportFragmentManager(), "Set Date");
    }

    public void setTimeBtn(View view) {
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
}