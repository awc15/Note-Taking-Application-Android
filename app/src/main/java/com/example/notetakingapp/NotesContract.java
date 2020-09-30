package com.example.notetakingapp;

import android.provider.BaseColumns;

public class NotesContract {

    private NotesContract(){}
    public static final class Notes implements BaseColumns{
        public static final String TABLE_NAME = "NotesData";
        public static final String NOTES_TITLE = "title";
        public static final String NOTES_DESCRIPTION = "description";
        public static final String NOTES_DATE = "date";
        public static final String NOTES_TIME = "time";
    }
}
