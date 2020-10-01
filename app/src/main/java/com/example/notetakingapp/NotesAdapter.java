package com.example.notetakingapp;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesViewHolder> {
    private Context context;
    private List<NotesContract> listNotes;
    private NotesDBHelper mDatabase;
    private NotesContract singleNote;
    private OnNoteListener onNoteListener;

    public NotesAdapter(Context context, List<NotesContract> listNotes, OnNoteListener onNoteListener) {
        this.context = context;
        this.listNotes = listNotes;
        this.onNoteListener = onNoteListener;
        mDatabase = new NotesDBHelper(context);
    }


    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes, parent, false);
        return new NotesViewHolder(view,onNoteListener);

    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        singleNote = listNotes.get(position);
        holder.title.setText(singleNote.getTitle());
        String desc = singleNote.getDescription();
        if (desc.length() > 10) {
            desc = desc.substring(0, 8);
        }
        holder.description.setText(desc);

    }

    @Override
    public int getItemCount() {
        return listNotes.size();
    }

    public interface OnNoteListener {
        void onNoteClick(int position);

    }
}