package com.example.notetakingapp;

import android.provider.BaseColumns;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotesContract {

    private int ID;
    private String title;
    private String description;
    private String time;
    private String date;

    public NotesContract(String title, String description, String date, String time) {

        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
    }

    public NotesContract(int id, String title, String description) {

        ID = id;
        this.title = title;
        this.description = description;
    }




    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }



//    public static final class Notes implements BaseColumns {
//        public static final String TABLE_NAME = "NotesData";
//        public static final String NOTES_TITLE = "title";
//        public static final String NOTES_DESCRIPTION = "description";
//        public static final String NOTES_DATE = "date";
//        public static final String NOTES_TIME = "time";
//
//    }

}
