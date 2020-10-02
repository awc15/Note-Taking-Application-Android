package com.example.notetakingapp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import javax.security.auth.login.LoginException;

public class NoteScheduler extends Worker {
    public static final String FILE_NAME = "Notes.txt";
    Date date;
    String month, day, year;

    public NoteScheduler(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Toast.makeText(getApplicationContext(),"Job Done ",Toast.LENGTH_SHORT).show();
        Data noteData = getInputData();
        scheduleNotesQueue(noteData);
        Log.d("TAG", "Doing Task");
        return Result.success();
    }


    private boolean scheduleNotesQueue(Data noteData) {

        int noteId = noteData.getInt("NoteID", 0);
        String noteDate = noteData.getString("Date");
        String noteTime = noteData.getString("Time");
        convertingDate(noteDate);
        month = checkMonth(month);
        String startingDate = year + "-" + month + "-" + day + "T";
        String startingTime = noteTime + "Z";
        //("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String dateTime = startingDate + startingTime;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        try {
            date = format.parse(dateTime);
            Log.e("TAG", "scheduleNotesQueue: ");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return true;
    }

    private void convertingDate(String noteDate) {

        month = noteDate.substring(0, 3);
        day = noteDate.substring(4, noteDate.indexOf(','));
        year = noteDate.substring(noteDate.indexOf(',') + 1, noteDate.length());
    }

    private String checkMonth(String month) {

        if (month.equals("Jan")) {
            return month = "1";
        }
        if (month.equals("Feb")) {
            return month = "2";
        }
        if (month.equals("Mar")) {
            return month = "3";
        }
        if (month.equals("Apr")) {
            return month = "4";
        }
        if (month.equals("May")) {
            return month = "5";
        }
        if (month.equals("Jun")) {
            return month = "6";
        }
        if (month.equals("Jul")) {
            return month = "7";
        }
        if (month.equals("Aug")) {
            return month = "8";
        }
        if (month.equals("Sep")) {
            return month = "9";
        }
        if (month.equals("Oct")) {
            return month = "10";
        }
        if (month.equals("Nov")) {
            return month = "11";
        }

        return "12";
    }

}
